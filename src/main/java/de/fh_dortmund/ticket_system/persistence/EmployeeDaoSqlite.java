package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityTransaction;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

public class EmployeeDaoSqlite extends BaseDaoSqlite<Employee> implements EmployeeDao, Serializable
{
	private static final long	serialVersionUID	= 1L;

}
