package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityTransaction;

import de.fh_dortmund.ticket_system.base.BaseDAOSqlite;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

public class EmployeeDAOsqlLite extends BaseDAOSqlite<Employee> implements EmployeeDAO, Serializable
{
	private static final long	serialVersionUID	= 1L;

	@Override
	public List<Employee> findAllEmployees()
	{
		List<Employee> resultList = getEm().createNamedQuery("Employee.findAll", Employee.class).getResultList();
		return resultList;
	}

	@Override
	public void update(Employee employee)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().merge(employee);
		List<Shift> shiftList = getEm().createNamedQuery("Shift.findAll", Shift.class).getResultList();
		for (Shift shift : shiftList)
		{
			if (shift.getDispatcher().equals(employee))
			{
				shift.setDispatcher(employee);
				getEm().merge(shift);
			}
			else if (shift.getSubstitutioner().equals(employee))
			{
				shift.setSubstitutioner(employee);
				getEm().merge(shift);
			}
		}
		tx.commit();
	}

	@Override
	public Employee findById(String id)
	{
		Employee emp = getEm().find(Employee.class, id);
		return emp;
	}
}
