package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.persistence.ConflictDao;
import de.fh_dortmund.ticket_system.persistence.ConflictDaoSqlite;
import de.fh_dortmund.ticket_system.util.TestdataProvider;

@ManagedBean
@ApplicationScoped
public class ConflictData extends BaseData<Conflict, ConflictDao> implements
		Serializable {
	private static final long serialVersionUID = -836521223253264448L;

	@PostConstruct
	private void init() {
		TestdataProvider.fillConflict(getDao());
	}

	public ConflictData() {
		setDao(new ConflictDaoSqlite());
	}

}
