package de.fh_dortmund.ticket_system.business;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.ScheduleEvent;


public class VacationEventModel extends PersonalVacationEventModel 
{

	private static final long serialVersionUID = 1L;
	
	@Override
	public List<ScheduleEvent> getEvents() {
		return new ArrayList<ScheduleEvent>(super.getDao().findAll());
	}
	
}
