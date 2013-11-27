package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.fh_dortmund.ticket_system.persistence.VacationEventDaoSqlite;

public class PersonalVacationEventModel implements ScheduleModel, Serializable
{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("auth")
	private Authentication auth;
	private VacationEventDaoSqlite dao = new VacationEventDaoSqlite();
	
	public PersonalVacationEventModel()
	{

	}

	@Override
	public void addEvent(ScheduleEvent event)
	{
		VacationEvent vacEvent = (VacationEvent) event;
		vacEvent.setId(UUID.randomUUID().toString());
		
		getDao().add(vacEvent);
	}
	
	public void addEvent(VacationEvent event)
	{
		event.setId(UUID.randomUUID().toString());
		
		getDao().add(event);
	}

	public boolean deleteEvent(ScheduleEvent event)
	{
		VacationEvent vacEvent = (VacationEvent) event;
		getDao().delete(vacEvent);
		//FIXME Return true... 
		return true;
	}

	@Override
	public List<ScheduleEvent> getEvents()
	{
		return new ArrayList<ScheduleEvent>(auth.getEmployee().getMyEvents());
	}

	@Override
	public ScheduleEvent getEvent(String id)
	{
		return getDao().findById(id);
	}

	@Override
	public void updateEvent(ScheduleEvent event)
	{
		VacationEvent vacEvent = (VacationEvent) event;
		
		getDao().update(vacEvent);
	}

	@Override
	public int getEventCount()
	{
		List<VacationEvent> events = getDao().findAll();
		return events.size();
	}

	@Override
	public void clear()
	{
		List<VacationEvent> events = getDao().findAll();
		for (VacationEvent vacationEvent : events) {
			getDao().delete(vacationEvent);
		}
	}

	public VacationEventDaoSqlite getDao() {
		return dao;
	}

	public void setDao(VacationEventDaoSqlite dao) {
		this.dao = dao;
	}
	
}
