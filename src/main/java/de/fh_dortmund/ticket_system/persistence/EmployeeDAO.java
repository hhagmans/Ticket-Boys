package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDao;
import de.fh_dortmund.ticket_system.entity.Employee;

public interface EmployeeDAO extends BaseDao<Employee>
{
	public List<Employee> findAllEmployees();
}
