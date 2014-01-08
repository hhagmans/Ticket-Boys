package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.EventType;
import de.fh_dortmund.ticket_system.util.HolidayUtil;

public class EventDaoSqlite extends BaseDaoSqlite<Event> implements EventDao,
		Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void delete(Event event) {

		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(event);
		if (event.getEventType() == EventType.vacation) {
			Employee emp = event.getEmployee();
			updateVacationCount(emp);
			emp.refreshFreeVacationDays();
			getEm().merge(emp);
		}
		tx.commit();
	}

	@Override
	public void add(Event event) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().persist(event);
		if (event.getEventType() == EventType.vacation) {
			Employee employee = event.getEmployee();
			updateVacationCount(employee);
			employee.refreshFreeVacationDays();
		}
		tx.commit();
	}

	public void update(Event event, int dayDelta) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		Employee emp = event.getEmployee();
		updateVacationCount(emp);
		emp.refreshFreeVacationDays();
		getEm().merge(event);
		getEm().merge(emp);
		tx.commit();
	}

	@Override
	public Event findById(String id) {
		Event emp = getEm().find(Event.class, id);
		return emp;
	}

	@Override
	public List<Event> findAll() {
		return getEm().createNamedQuery("findAll", Event.class).getResultList();
	}

	public List<Event> findByUser(Employee emp) {
		Query setParameter = getEm()
				.createNamedQuery("findByUser", Event.class).setParameter(
						"employee", emp);
		return (List<Event>) setParameter.getResultList();
	}

	public List<Event> findByUserAndYear(Employee emp, int year) {
		Query setParameter = getEm()
				.createNamedQuery("findByUser", Event.class).setParameter(
						"employee", emp);
		List<Event> userList = (List<Event>) setParameter.getResultList();
		List<Event> filteredList = new ArrayList<Event>();
		Calendar c = Calendar.getInstance();
		for (Event event : userList) {
			c.setTime(event.getStartDate());
			if (c.get(Calendar.YEAR) == year) {
				c.setTime(event.getEndDate());
				if (c.get(Calendar.YEAR) == year + 1) {
					c.set(Calendar.YEAR, year);
					c.set(Calendar.MONTH, 11);
					c.set(Calendar.DAY_OF_MONTH, 31);
					System.out.println(c.getTime());
					event.setEndDate(c.getTime());
				}
				filteredList.add(event);
			} else {
				c.setTime(event.getEndDate());
				if (c.get(Calendar.YEAR) == year) {
					c.set(Calendar.YEAR, year);
					c.set(Calendar.MONTH, 0);
					c.set(Calendar.DAY_OF_MONTH, 1);
					System.out.println(c.getTime());
					event.setStartDate(c.getTime());
					filteredList.add(event);
				}
			}

		}
		return filteredList;
	}

	private void updateVacationCount(Employee emp) {
		Calendar c = Calendar.getInstance();
		List<Event> eventList = findByUserAndYear(emp, c.get(Calendar.YEAR));
		long count = 0;
		for (Event event : eventList) {
			if (event.getEventType() == EventType.vacation)
				count = count + (calculateDayCount(event) + 1);
		}
		emp.setVacationCount((int) count);
		getEm().merge(emp);

	}

	private long calculateDayCount(Event event) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Date startDate = event.getStartDate();
		Date endDate = event.getEndDate();
		start.setTime(startDate);
		end.setTime(endDate);
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		long diffTime = endTime - startTime;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		start.add(Calendar.DAY_OF_MONTH, (int) diffDays);
		while (start.before(end)) {
			start.add(Calendar.DAY_OF_MONTH, 1);
			diffDays++;
		}
		while (start.after(end)) {
			start.add(Calendar.DAY_OF_MONTH, -1);
			diffDays--;
		}
		diffDays = diffDays
				- HolidayUtil.getNumberofHolidaysBetweenTwoDates(
						event.getEmployee(), startDate, endDate);
		return diffDays;
	}
}
