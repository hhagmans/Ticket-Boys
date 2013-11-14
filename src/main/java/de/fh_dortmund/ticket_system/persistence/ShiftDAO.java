package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.entity.Shift;

public interface ShiftDAO
{

	public List<Shift> findAllShifts();

	public void updateShift(Shift shift);

	public void deleteShift(Shift shift);

	public void addShift(Shift shift);

	public Shift findShiftById(String id);

}
