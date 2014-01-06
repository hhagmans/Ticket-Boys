package de.fh_dortmund.ticket_system.business;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.persistence.ConflictDaoSqlite;

@ManagedBean
@ApplicationScoped
public class ConflictData extends BaseData<Conflict, ConflictDaoSqlite>
{
	public ConflictData()
	{
		setDao(new ConflictDaoSqlite());
	}

	public List<Conflict> findByEmployee(Employee currentUser)
	{
		return getDao().findByEmployee(currentUser);
	}
}
