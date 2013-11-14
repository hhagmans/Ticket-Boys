package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@SessionScoped
public class VacationEventModel implements ScheduleModel, Serializable
{

	private static final long serialVersionUID = 1L;
	private List<ScheduleEvent> events;

	public VacationEventModel()
	{
		events = new ArrayList<ScheduleEvent>();
	}

	public VacationEventModel(List<ScheduleEvent> events)
	{
		this.events = events;
	}

	@Override
	public void addEvent(ScheduleEvent event)
	{
		event.setId(UUID.randomUUID().toString());

		events.add(event);
	}

	@Override
	public boolean deleteEvent(ScheduleEvent event)
	{
		return events.remove(event);
	}

	@Override
	public List<ScheduleEvent> getEvents()
	{
		return events;
	}

	@Override
	public ScheduleEvent getEvent(String id)
	{
		for (ScheduleEvent event : events)
		{
			if (event.getId().equals(id))
			{
				return event;
			}
		}

		return null;
	}

	@Override
	public void updateEvent(ScheduleEvent event)
	{
		int index = -1;

		for (int i = 0; i < events.size(); i++)
		{
			if (events.get(i).getId().equals(event.getId()))
			{
				index = i;

				break;
			}
		}

		if (index >= 0)
		{
			events.set(index, event);
		}
	}

	@Override
	public int getEventCount()
	{
		return events.size();
	}

	@Override
	public void clear()
	{
		events = new ArrayList<ScheduleEvent>();
	}

}
