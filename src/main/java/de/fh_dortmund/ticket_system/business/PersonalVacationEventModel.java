package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

@ManagedBean
@ApplicationScoped
public class PersonalVacationEventModel implements ScheduleModel, Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{auth}")
	private Authentication auth;

	@ManagedProperty("#{vacationData}")
	private VacationData data;

	@ManagedProperty("#{conflict}")
	private ConflictFinder conflictFinder;

	public PersonalVacationEventModel() {

	}

	@Override
	public void addEvent(ScheduleEvent event) {
		VacationEvent vacEvent = (VacationEvent) event;
		vacEvent.setId(UUID.randomUUID().toString());
		vacEvent.setEmployee(auth.getEmployee());
		getData().add(vacEvent);
	}

	public void addEvent(VacationEvent event, boolean isHoliday) {
		event.setId(UUID.randomUUID().toString());
		event.setEmployee(auth.getEmployee());
		event.setPersonalTitle(event.getTitle());
		getData().add(event);
	}

	public boolean deleteEvent(ScheduleEvent event) {
		VacationEvent vacEvent = (VacationEvent) event;
		getData().delete(vacEvent);
		// FIXME Return true...
		return true;
	}

	@Override
	public List<ScheduleEvent> getEvents() {
		ArrayList<VacationEvent> myEvents = new ArrayList<VacationEvent>(
				getData().findByUser(getAuth().getEmployee()));
		myEvents = addHolidays(myEvents);
		ArrayList<ScheduleEvent> arrayList = new ArrayList<ScheduleEvent>();
		ScheduleEvent event;
		for (VacationEvent vacationEvent : myEvents) {
			vacationEvent.setTitle(vacationEvent.getPersonalTitle());
			event = (ScheduleEvent) vacationEvent;
			arrayList.add((ScheduleEvent) vacationEvent);
		}

		if (myEvents != null) {
			arrayList = new ArrayList<ScheduleEvent>(myEvents);
		} else {
			arrayList = new ArrayList<ScheduleEvent>();
		}
		return arrayList;
	}

	public ArrayList<VacationEvent> addHolidays(ArrayList<VacationEvent> vacList) {

		HolidayManager manager = HolidayManager
				.getInstance(HolidayCalendar.GERMANY);
		Set<Holiday> holidays = manager.getHolidays(2013, "nw");
		for (Holiday h : holidays) {
			vacList.add(new VacationEvent(h.getDescription(), h.getDate()
					.toDateTimeAtStartOfDay().toDate(), h.getDate()
					.toDateTimeAtStartOfDay().toDate(), true));
		}
		return vacList;
	}

	@Override
	public ScheduleEvent getEvent(String id) {
		return getData().findByID(id);
	}

	@Override
	public void updateEvent(ScheduleEvent event) {
		VacationEvent vacEvent = (VacationEvent) event;

		getData().update(vacEvent);
	}

	public void updateEvent(ScheduleEvent event, int dayDelta) {
		VacationEvent vacEvent = (VacationEvent) event;

		getData().update(vacEvent, dayDelta);
	}

	@Override
	public int getEventCount() {
		List<VacationEvent> events = getData().findAll();
		return events.size();
	}

	@Override
	public void clear() {
		List<VacationEvent> events = getData().findAll();
		for (VacationEvent vacationEvent : events) {
			getData().delete(vacationEvent);
		}
	}

	public VacationData getData() {
		return data;
	}

	public void setData(VacationData data) {
		this.data = data;
	}

	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public ConflictFinder getConflictFinder() {
		return conflictFinder;
	}

	public void setConflictFinder(ConflictFinder conflictFinder) {
		this.conflictFinder = conflictFinder;
	}

}