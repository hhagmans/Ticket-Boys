package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityTransaction;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

public class EmployeeDAOsqlLite extends BaseDAO implements EmployeeDAO, Serializable
{
	private static final long	serialVersionUID	= 1L;

	@Override
	public List<Employee> findAllEmployees()
	{
		List<Employee> resultList = getEm().createNamedQuery("Employee.findAll", Employee.class).getResultList();
		return resultList;
	}

	@Override
	public void updateEmployee(Employee employee)
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
	public void deleteEmployee(Employee employee)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(employee);
		tx.commit();
	}

	@Override
	public void addEmployee(Employee employee)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().persist(employee);
		tx.commit();
	}

	@Override
	public Employee findEmployeeById(String id)
	{
		Employee emp = getEm().find(Employee.class, id);
		return emp;
	}

}
