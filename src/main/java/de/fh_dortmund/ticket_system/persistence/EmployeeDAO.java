package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.entity.Employee;

public interface EmployeeDAO
{

	public List<Employee> getAllEmployees();

	public void updateEmployee(Employee employee);

	public void deleteEmployee(Employee employee);

	public void addEmployee(Employee employee);

}
