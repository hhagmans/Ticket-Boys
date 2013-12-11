package de.fh_dortmund.ticket_system.business;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.persistence.ConflictDaoSqlite;

@ManagedBean
@ApplicationScoped
public class ConflictData extends BaseData<Conflict, ConflictDaoSqlite>
{
	public ConflictData() {
		setDao(new ConflictDaoSqlite());
	}
}
