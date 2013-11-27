package de.fh_dortmund.ticket_system.business;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.ScheduleEvent;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.fh_dortmund.ticket_system.persistence.VacationEventDao;
import de.fh_dortmund.ticket_system.persistence.VacationEventDaoSqlite;

public class VacationEventModel extends PersonalVacationEventModel 
{

	private static final long	serialVersionUID	= 1L;
	private VacationEventDao	vacationEventDAO;

	@ManagedProperty("auth")
	private Authentication		auth;

	public VacationEventModel()
	{
		vacationEventDAO = new VacationEventDaoSqlite();
	}

	@Override
	public List<ScheduleEvent> getEvents() {
		return new ArrayList<ScheduleEvent>(super.getDao().findAll());
	}	public void addEvent(ScheduleEvent event)
	{
		VacationEvent vacEvent = (VacationEvent) event;
		vacEvent.setId(UUID.randomUUID().toString());

		vacationEventDAO.add(vacEvent);
	}
	

	public void addEvent(VacationEvent event)
	{
		event.setId(UUID.randomUUID().toString());

		vacationEventDAO.add(event);
	}

	@Override
	public boolean deleteEvent(ScheduleEvent event)
	{
		VacationEvent vacEvent = (VacationEvent) event;
		vacationEventDAO.delete(vacEvent);
		return true;
	}

	@Override
	public ScheduleEvent getEvent(String id)
	{
		return vacationEventDAO.findById(id);
	}

	@Override
	public void updateEvent(ScheduleEvent event)
	{
		VacationEvent vacEvent = (VacationEvent) event;

		vacationEventDAO.update(vacEvent);
	}

	@Override
	public int getEventCount()
	{
		List<VacationEvent> events = vacationEventDAO.findAll();
		return events.size();
	}

	@Override
	public void clear()
	{
		List<VacationEvent> events = vacationEventDAO.findAll();
		for (VacationEvent vacationEvent : events)
		{
			vacationEventDAO.delete(vacationEvent);
		}
	}

	public List<ScheduleEvent> getEventsForCurrentUser()
	{
		return new ArrayList<ScheduleEvent>(auth.getEmployee().getMyEvents());
	}
}
