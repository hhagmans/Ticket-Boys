package de.fh_dortmund.ticket_system.business;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.ScheduleEvent;

import de.fh_dortmund.ticket_system.entity.VacationEvent;

@ManagedBean
@ApplicationScoped
public class VacationEventModel extends PersonalVacationEventModel {

	private static final long serialVersionUID = 1L;

	public VacationEventModel() {
	}

	@Override
	public List<ScheduleEvent> getEvents() {
		ArrayList<VacationEvent> events = new ArrayList<VacationEvent>(getData().findAll());
		for (VacationEvent scheduleEvent : events) {
			if (scheduleEvent.getIsVacation()){
			scheduleEvent.setTitle("Urlaub: " + scheduleEvent.getEmployee().getFullName());
			}
			else {
				scheduleEvent.setTitle("Sonstiges: " + scheduleEvent.getEmployee().getFullName());
			}
		}
		return new ArrayList<ScheduleEvent>(events);
	}

	public void addEvent(ScheduleEvent event) {
		VacationEvent vacEvent = (VacationEvent) event;
		vacEvent.setId(UUID.randomUUID().toString());

		getData().add(vacEvent);
	}

	public void addEvent(VacationEvent event) {
		event.setId(UUID.randomUUID().toString());

		getData().add(event);
	}

	@Override
	public boolean deleteEvent(ScheduleEvent event) {
		VacationEvent vacEvent = (VacationEvent) event;
		getData().delete(vacEvent);
		return true;
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

	public List<ScheduleEvent> getEventsForCurrentUser() {
		return new ArrayList<ScheduleEvent>(super.getAuth().getEmployee().getMyEvents());
	}
}
