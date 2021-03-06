package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.EventType;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.Week;
import de.fh_dortmund.ticket_system.util.HolidayUtil;
import de.jollyday.Holiday;

/**
 * Model for personal events used by views to get personal events.
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@SessionScoped
public class PersonalEventModel implements ScheduleModel, Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{auth}")
	private Authentication auth;

	@ManagedProperty("#{eventData}")
	private EventData data;

	@ManagedProperty("#{shiftData}")
	private ShiftData shiftData;

	public PersonalEventModel() {
	}

	@Override
	public void addEvent(ScheduleEvent event) {
		Event newEvent = (Event) event;
		newEvent.setId(UUID.randomUUID().toString());
		newEvent.setEmployee(auth.getEmployee());
		getData().add(newEvent);
	}

	public void addEvent(Event event) {
		event.setId(UUID.randomUUID().toString());
		event.setEmployee(auth.getEmployee());
		event.setPersonalTitle(event.getTitle());
		event.setStyleClass(event.getEventType().getStyleClass());
		getData().add(event);
	}

	@Override
	public boolean deleteEvent(ScheduleEvent event) {
		Event vacEvent = (Event) event;
		getData().delete(vacEvent);
		// FIXME Return true...
		return true;
	}

	@Override
	public List<ScheduleEvent> getEvents() {
		Employee employee = getAuth().getEmployee();
		ArrayList<Event> myEvents = new ArrayList<Event>(getData().findByUser(
				employee));

		getData().updateVacationCount(employee);
		ArrayList<ScheduleEvent> arrayList = new ArrayList<ScheduleEvent>();

		if (employee.getRole() == Role.dispatcher) {
			myEvents = addDispatcherEvents(myEvents);
		}

		myEvents = addHolidays(myEvents);

		for (Event vacationEvent : myEvents) {
			vacationEvent.setTitle(vacationEvent.getPersonalTitle());
			arrayList.add(vacationEvent);
		}

		return arrayList;
	}

	private ArrayList<Event> addDispatcherEvents(ArrayList<Event> myEvents) {

		Employee employee = getAuth().getEmployee();

		List<Shift> findShiftByEmployee = getShiftData().findShiftByEmployee(
				employee);
		if (findShiftByEmployee == null) {
			return myEvents;
		}

		for (Shift shift : findShiftByEmployee) {

			Week week = shift.getWeek();
			Date startDate = week.getStartDate();
			Date endDate = week.getEndDate();

			Event event = new Event(UUID.randomUUID().toString(),
					"Dispatcher-Schicht", startDate, endDate,
					EventType.dispatcher);
			event.setEditable(false);
			event.setStyleClass(event.getEventType().getStyleClass());
			myEvents.add(event);
		}

		return myEvents;
	}

	public ArrayList<Event> addHolidays(ArrayList<Event> vacList) {

		Set<Holiday> holidays = HolidayUtil.getHolidaysforUser(getAuth()
				.getEmployee());

		if (holidays == null) {
			return vacList;
		}

		Event event;
		for (Holiday h : holidays) {
			event = new Event(UUID.randomUUID().toString(), h.getDescription(),
					h.getDate().toDateTimeAtStartOfDay().toDate(), h.getDate()
							.toDateTimeAtStartOfDay().toDate(),
					EventType.holiday);
			event.setStyleClass(event.getEventType().getStyleClass());
			event.setEditable(false);
			vacList.add(event);
		}
		return vacList;
	}

	@Override
	public ScheduleEvent getEvent(String id) {
		return getData().findByID(id);
	}

	@Override
	public void updateEvent(ScheduleEvent event) {
		Event vacEvent = (Event) event;

		getData().update(vacEvent);
	}

	public void updateEvent(Event event) {
		getData().update(event);
	}

	@Override
	public int getEventCount() {
		List<Event> events = getData().findAll();
		return events.size();
	}

	@Override
	public void clear() {
		List<Event> events = getData().findAll();
		for (Event vacationEvent : events) {
			getData().delete(vacationEvent);
		}
	}

	public EventData getData() {
		return data;
	}

	public void setData(EventData data) {
		this.data = data;
	}

	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public ShiftData getShiftData() {
		return shiftData;
	}

	public void setShiftData(ShiftData shiftData) {
		this.shiftData = shiftData;
	}

}
