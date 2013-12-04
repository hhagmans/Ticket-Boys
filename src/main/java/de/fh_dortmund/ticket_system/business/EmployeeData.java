package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.persistence.EmployeeDao;
import de.fh_dortmund.ticket_system.persistence.EmployeeDaoSqlite;
import de.fh_dortmund.ticket_system.util.TestdataProvider;

/**
 * Diese Klasse speichert und verwaltet alle bekannten Benutzer
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean
@ApplicationScoped
public class EmployeeData extends BaseData<Employee, EmployeeDao> implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	public EmployeeData()
	{
		setDao(new EmployeeDaoSqlite());
	}

	@PostConstruct
	private void fill()
	{
		TestdataProvider.fillEmployees(getDao());
	}
}
