package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

/**
 * Implementation of the ShiftDao for SQLite. Extends BaseDaoSqlite for basic
 * methods and implements individual methods.
 * 
 * @author Ticket-Boys
 * 
 */
public class ShiftDaoSqlite extends BaseDaoSqlite<Shift> implements ShiftDao,
		Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void update(Shift shift) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().merge(shift);
		tx.commit();
	}

	@Override
	public void delete(Shift shift) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(shift);
		Employee emp = shift.getDispatcher();
		emp.decrementScore();
		getEm().merge(emp);
		tx.commit();
	}

	@Override
	public void add(Shift newShift) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().persist(newShift);
		Employee emp = newShift.getDispatcher();
		emp.incrementScore();
		getEm().merge(emp);
		tx.commit();
	}

	@Override
	public Shift findById(String id) {
		Shift shift = getEm().find(Shift.class, id);
		return shift;
	}

	@Override
	public List<Shift> findByEmployee(Employee employee) {
		// Query setParameter = getEm().createNamedQuery("findByDispatcher",
		// Shift.class).setParameter("dispatcher", employee);
		// return (List<Shift>) setParameter.getResultList();
		List<Shift> allShifts = findAll();
		List<Shift> empShifts = new ArrayList<Shift>();
		for (Shift shift : allShifts) {
			if (shift.getDispatcher().equals(employee)) {
				empShifts.add(shift);
			}
		}
		return empShifts;
	}

	/**
	 * Deletes an Employee from all shifts and sets a new dispatcher. Used when
	 * an employee gets deleted.
	 * 
	 * @author Ticket-Boys
	 * 
	 */
	@Override
	public void deleteEmployeeFromShifts(Employee employee) {
		ArrayList<Shift> shifts = new ArrayList<Shift>(findByEmployee(employee));
		System.out.println(findByEmployee(employee));
		Shift tempShift = null;
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		for (Shift shift : shifts) {
			if (shift.getWeek().getWeekNumber() != 52) {
				tempShift = findById(shift.getWeek().getYear() + "-"
						+ (shift.getWeek().getWeekNumber() + 1));
			} else {
				tempShift = findById((shift.getWeek().getYear() + 1) + "-"
						+ (shift.getWeek().getWeekNumber() - 51));
			}
			shift.setDispatcher(shift.getSubstitutioner());
			if (tempShift != null) {
				shift.setSubstitutioner(tempShift.getDispatcher());
			}
			getEm().merge(shift);
		}
		tx.commit();
	}
}
