package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.EventType;

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
			long diffDays = calculateDayCount(event);
			emp.decrementVacationCount((int) diffDays + 1);
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
			long diffDays = calculateDayCount(event);
			Employee employee = event.getEmployee();
			employee.incrementVacationCount((int) diffDays + 1);
			employee.refreshFreeVacationDays();
		}
		tx.commit();
	}

	public void update(Event event, int dayDelta) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		Employee emp = event.getEmployee();
		if (event.getEventType() == EventType.vacation) {
			emp.incrementVacationCount(dayDelta);
		}
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
		return diffDays;
	}

}
