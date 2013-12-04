package de.fh_dortmund.ticket_system.business;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.ScheduleEvent;

import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.EventType;

@ManagedBean
@ApplicationScoped
public class EventModel extends PersonalEventModel {

	private static final long serialVersionUID = 1L;

	public EventModel() {
	}

	@Override
	public List<ScheduleEvent> getEvents() {
		ArrayList<Event> events = new ArrayList<Event>(super
				.getData().findAll());
		for (Event vacationEvent : events) {
			if (vacationEvent.getEventType() == EventType.vacation) {
				vacationEvent.setTitle("Urlaub: "
						+ vacationEvent.getEmployee().getFullName());
			} else {
				vacationEvent.setTitle("Sonstiges: "
						+ vacationEvent.getEmployee().getFullName());
			}
		}
		return new ArrayList<ScheduleEvent>(events);
	}

	public void addEvent(ScheduleEvent event) {
		Event vacEvent = (Event) event;
		vacEvent.setId(UUID.randomUUID().toString());

		getData().add(vacEvent);
	}

	public void addEvent(Event event) {
		event.setId(UUID.randomUUID().toString());

		getData().add(event);
	}

	@Override
	public boolean deleteEvent(ScheduleEvent event) {
		Event vacEvent = (Event) event;
		getData().delete(vacEvent);
		return true;
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

	public List<ScheduleEvent> getEventsForCurrentUser() {
		return new ArrayList<ScheduleEvent>(super.getAuth().getEmployee()
				.getMyEvents());
	}
}
