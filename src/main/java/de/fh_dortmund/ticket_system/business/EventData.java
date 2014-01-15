package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.persistence.EventDao;
import de.fh_dortmund.ticket_system.persistence.EventDaoSqlite;
import de.fh_dortmund.ticket_system.util.TestdataProvider;

/**
 * This class saves and manages all events.
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean()
@ApplicationScoped
public class EventData extends BaseData<Event, EventDao> implements
		Serializable {
	private static final long serialVersionUID = 1386350160944016195L;

	public EventData() {
		setDao(new EventDaoSqlite());
	}

	@PostConstruct
	public void init() {
		TestdataProvider.fillEvents(getDao());
	}

	public List<Event> findByUser(Employee emp) {
		return getDao().findByUser(emp);
	}

	public void updateVacationCount(Employee emp) {
		getDao().updateVacationCount(emp);
	}

	@Override
	public void update(Event vacationEvent) {
		getDao().update(vacationEvent);
	}
}
