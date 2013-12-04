package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

@ManagedBean
@ApplicationScoped
public class PersonalVacationEventModel implements ScheduleModel, Serializable
{

	private static final long	serialVersionUID	= 1L;

	@ManagedProperty("#{auth}")
	private Authentication		auth;

	@ManagedProperty("#{vacationData}")
	private VacationData		data;

	@ManagedProperty("#{conflict}")
	private ConflictFinder		conflictFinder;

	public PersonalVacationEventModel()
	{

	}

	@Override
	public void addEvent(ScheduleEvent event)
	{
		VacationEvent vacEvent = (VacationEvent) event;
		vacEvent.setId(UUID.randomUUID().toString());
		vacEvent.setEmployee(auth.getEmployee());
		getData().add(vacEvent);
	}

	public void addEvent(VacationEvent event)
	{
		event.setId(UUID.randomUUID().toString());
		event.setEmployee(auth.getEmployee());
		if (event.getIsVacation())
		{
			event.setPersonalTitle(event.getTitle());
		}
		else
		{
			event.setPersonalTitle(event.getTitle());
		}

		if (conflictFinder.checkVacation(event))
			getData().add(event);
		else
		{

		}

	}

	public boolean deleteEvent(ScheduleEvent event)
	{
		VacationEvent vacEvent = (VacationEvent) event;
		getData().delete(vacEvent);
		// FIXME Return true...
		return true;
	}

	@Override
	public List<ScheduleEvent> getEvents()
	{
		System.out.println(getAuth().getEmployee().getMyEvents());
		ArrayList<VacationEvent> myEvents = new ArrayList<VacationEvent>(getData().findByUser(getAuth().getEmployee()));
		ArrayList<ScheduleEvent> arrayList = new ArrayList<ScheduleEvent>();
		ScheduleEvent event;
		for (VacationEvent vacationEvent : myEvents)
		{
			vacationEvent.setTitle(vacationEvent.getPersonalTitle());
			event = (ScheduleEvent) vacationEvent;
			arrayList.add((ScheduleEvent) vacationEvent);
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
		VacationEvent vacEvent = (VacationEvent) event;

		getData().update(vacEvent);
	}

	public void updateEvent(ScheduleEvent event, int dayDelta)
	{
		VacationEvent vacEvent = (VacationEvent) event;

		getData().update(vacEvent, dayDelta);
	}

	@Override
	public int getEventCount()
	{
		List<VacationEvent> events = getData().findAll();
		return events.size();
	}

	@Override
	public void clear()
	{
		List<VacationEvent> events = getData().findAll();
		for (VacationEvent vacationEvent : events)
		{
			getData().delete(vacationEvent);
		}
	}

	public VacationData getData()
	{
		return data;
	}

	public void setData(VacationData data)
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

	public ConflictFinder getConflictFinder()
	{
		return conflictFinder;
	}

	public void setConflictFinder(ConflictFinder conflictFinder)
	{
		this.conflictFinder = conflictFinder;
	}

}