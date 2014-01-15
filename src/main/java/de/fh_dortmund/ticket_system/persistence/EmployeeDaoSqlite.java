package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Employee;

/**
 * Implementation of the EmployeeDao for SQLite. Extends BaseDaoSqlite for basic
 * methods.
 * 
 * @author Ticket-Boys
 * 
 */
public class EmployeeDaoSqlite extends BaseDaoSqlite<Employee> implements
		EmployeeDao, Serializable {
	private static final long serialVersionUID = 1L;

}
