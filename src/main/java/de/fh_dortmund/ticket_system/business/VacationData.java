package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.fh_dortmund.ticket_system.persistence.VacationEventDAO;
import de.fh_dortmund.ticket_system.persistence.VacationEventDAOsqlLite;

@ManagedBean
@ApplicationScoped
public class VacationData extends BaseData<VacationEvent, VacationEventDAO> implements Serializable
{
	private static final long	serialVersionUID	= 1386350160944016195L;

	public VacationData()
	{
		dao = new VacationEventDAOsqlLite();
	}

	public List<VacationEvent> findAllVacationEvents()
	{
		return dao.findAllVacationEvents();
	}
}
