package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.persistence.ConflictDao;
import de.fh_dortmund.ticket_system.persistence.ConflictDaoSqlite;

/**
 * This class saves and manages all conflicts.
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean()
@ApplicationScoped
public class ConflictData extends BaseData<Conflict, ConflictDao> implements
		Serializable {
	private static final long serialVersionUID = -836521223253264448L;

	public ConflictData() {
		setDao(new ConflictDaoSqlite());
	}

	public List<Conflict> findByUser(Employee emp) {
		return getDao().findByUser(emp);
	}

}
