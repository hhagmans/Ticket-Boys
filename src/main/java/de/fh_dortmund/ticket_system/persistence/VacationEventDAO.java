package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.entity.VacationEvent;

public interface VacationEventDAO
{

	public List<VacationEvent> findAllVacationEvents();
	
	public VacationEvent findVacationEventById(String id);

	public void updateVacationEvent(VacationEvent vacationEvent);

	public void deleteVacationEvent(VacationEvent vacationEvent);

	public void addVacationEvent(VacationEvent vacationEvent);

}
