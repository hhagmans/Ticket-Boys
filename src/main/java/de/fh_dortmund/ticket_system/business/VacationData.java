package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.fh_dortmund.ticket_system.persistence.VacationEventDao;
import de.fh_dortmund.ticket_system.persistence.VacationEventDaoSqlite;

@ManagedBean
@ApplicationScoped
public class VacationData extends BaseData<VacationEvent, VacationEventDao> implements Serializable
{
	private static final long	serialVersionUID	= 1386350160944016195L;

	public VacationData()
	{
		dao = new VacationEventDaoSqlite();
	}
	
	public List<VacationEvent> findByUser(Employee emp) {
		return dao.findByUser(emp);
	}
}
