package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.Event;

@ManagedBean
@ApplicationScoped
public class PersonalEventModel implements ScheduleModel, Serializable
{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{auth}")
	private Authentication auth;

	@ManagedProperty("#{eventData}")
	private EventData data;

	public PersonalEventModel()
	{

	}

	@Override
	public void addEvent(ScheduleEvent event)
	{
		Event newEvent = (Event) event;
		newEvent.setId(UUID.randomUUID().toString());
		newEvent.setEmployee(auth.getEmployee());
		getData().add(newEvent);
	}

	public void addEvent(Event event)
	{
		event.setId(UUID.randomUUID().toString());
		event.setEmployee(auth.getEmployee());
		event.setPersonalTitle(event.getTitle());
		getData().add(event);
	}

	@Override
	public boolean deleteEvent(ScheduleEvent event)
	{
		Event vacEvent = (Event) event;
		getData().delete(vacEvent);
		// FIXME Return true...
		return true;
	}

	@Override
	public List<ScheduleEvent> getEvents()
	{
		System.out.println(getAuth().getEmployee().getMyEvents());
		ArrayList<Event> myEvents = new ArrayList<Event>(getData().findByUser(getAuth().getEmployee()));
		ArrayList<ScheduleEvent> arrayList = new ArrayList<ScheduleEvent>();
		ScheduleEvent event;
		for (Event vacationEvent : myEvents)
		{
			vacationEvent.setTitle(vacationEvent.getPersonalTitle());
			event = vacationEvent;
			arrayList.add(vacationEvent);
		}
		if (myEvents != null)
		{
			arrayList = new ArrayList<ScheduleEvent>(myEvents);
		}
		else
		{
			arrayList = new ArrayList<ScheduleEvent>();
		}
		return arrayList;
	}

	@Override
	public ScheduleEvent getEvent(String id)
	{
		return getData().findByID(id);
	}

	@Override
	public void updateEvent(ScheduleEvent event)
	{
		Event vacEvent = (Event) event;

		getData().update(vacEvent);
	}

	public void updateEvent(ScheduleEvent event, int dayDelta)
	{
		Event vacEvent = (Event) event;

		getData().update(vacEvent, dayDelta);
	}

	@Override
	public int getEventCount()
	{
		List<Event> events = getData().findAll();
		return events.size();
	}

	@Override
	public void clear()
	{
		List<Event> events = getData().findAll();
		for (Event vacationEvent : events)
		{
			getData().delete(vacationEvent);
		}
	}

	public EventData getData()
	{
		return data;
	}

	public void setData(EventData data)
	{
		this.data = data;
	}

	public Authentication getAuth()
	{
		return auth;
	}

	public void setAuth(Authentication auth)
	{
		this.auth = auth;
	}

}