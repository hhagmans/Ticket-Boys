package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.EventType;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.Week;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

@ManagedBean
@ApplicationScoped
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
		switch (event.getEventType()) {
		case vacation:
			event.setStyleClass("vacation-event");
			break;
		case other:
			event.setStyleClass("other-event");
			break;
		default:
			break;
		}

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
		ArrayList<ScheduleEvent> arrayList = new ArrayList<ScheduleEvent>();

		if (employee.getRole() == Role.dispatcher)
			myEvents = addDispatcherEvents(myEvents);

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
		if (findShiftByEmployee == null)
			return myEvents;

		for (Shift shift : findShiftByEmployee) {

			Week week = shift.getWeek();
			Date startDate = getStartDateForWeek(week);
			Date endDate = getEndDateForWeek(week);

			Event event = new Event(UUID.randomUUID().toString(),
					"Dispatcher-Schicht", startDate, endDate,
					EventType.dispatcher);
			event.setEditable(false);
			event.setStyleClass("dispatcher-event");
			myEvents.add(event);
		}

		return myEvents;
	}

	private Date getEndDateForWeek(Week week) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, week.getYear());
		cal.set(Calendar.WEEK_OF_YEAR, week.getWeekNumber());
		cal.set(Calendar.DAY_OF_WEEK, 1);
		return cal.getTime();
	}

	private Date getStartDateForWeek(Week week) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.WEEK_OF_YEAR, week.getWeekNumber());
		cal.set(Calendar.YEAR, week.getYear());
		cal.set(Calendar.DAY_OF_WEEK, 2);
		return cal.getTime();
	}

	public ArrayList<Event> addHolidays(ArrayList<Event> vacList) {

		HolidayManager manager;
		Set<Holiday> holidays = null;
		if (String.valueOf(getAuth().getEmployee().getZipcode()).length() == 5) {
			manager = HolidayManager.getInstance(HolidayCalendar.GERMANY);
			if (getAuth().getEmployee().getCity().trim().toLowerCase()
					.startsWith("marl")) {
				holidays = manager.getHolidays(
						Calendar.getInstance().get(Calendar.YEAR), "nw");
			} else if (getAuth().getEmployee().getCity().trim().toLowerCase()
					.startsWith("frankfurt")) {
				holidays = manager.getHolidays(
						Calendar.getInstance().get(Calendar.YEAR), "he");
			} else {
				holidays = manager.getHolidays(
						Calendar.getInstance().get(Calendar.YEAR), "de");
			}
		} else if (String.valueOf(getAuth().getEmployee().getZipcode())
				.length() == 4) {
			manager = HolidayManager.getInstance(HolidayCalendar.BULGARIA);
			holidays = manager.getHolidays(Calendar.getInstance().get(
					Calendar.YEAR));
		}
		Event event;
		for (Holiday h : holidays) {
			event = new Event(UUID.randomUUID().toString(), h.getDescription(),
					h.getDate().toDateTimeAtStartOfDay().toDate(), h.getDate()
							.toDateTimeAtStartOfDay().toDate(),
					EventType.holiday);
			event.setEditable(false);
			event.setStyleClass("holiday-event");
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

	public void updateEvent(ScheduleEvent event, int dayDelta) {
		Event vacEvent = (Event) event;

		getData().update(vacEvent, dayDelta);
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
