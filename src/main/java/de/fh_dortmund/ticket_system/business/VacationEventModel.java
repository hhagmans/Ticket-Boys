package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.fh_dortmund.ticket_system.persistence.VacationEventDAO;
import de.fh_dortmund.ticket_system.persistence.VacationEventDAOsqlLite;

public class VacationEventModel implements ScheduleModel, Serializable
{

	private static final long	serialVersionUID	= 1L;
	private VacationEventDAO	vacationEventDAO;

	public VacationEventModel()
	{
		vacationEventDAO = new VacationEventDAOsqlLite();
	}

	@Override
	public void addEvent(ScheduleEvent event)
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
	public List<ScheduleEvent> getEvents()
	{
		List<VacationEvent> vacEvents = vacationEventDAO.findAllVacationEvents();
		List<ScheduleEvent> events = new ArrayList<ScheduleEvent>();

		for (VacationEvent vacationEvent : vacEvents)
		{
			events.add(vacationEvent);
		}
		return events;
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
		List<VacationEvent> events = vacationEventDAO.findAllVacationEvents();
		return events.size();
	}

	@Override
	public void clear()
	{
		List<VacationEvent> events = vacationEventDAO.findAllVacationEvents();
		for (VacationEvent vacationEvent : events)
		{
			vacationEventDAO.delete(vacationEvent);
		}
	}

}
