package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.persistence.EventDao;
import de.fh_dortmund.ticket_system.persistence.EventDaoSqlite;

@ManagedBean
@ApplicationScoped
public class EventData extends BaseData<Event, EventDao>
		implements Serializable {
	private static final long serialVersionUID = 1386350160944016195L;

	public EventData() {
		setDao(new EventDaoSqlite());
	}

	public List<Event> findByUser(Employee emp) {
		return getDao().findByUser(emp);
	}

	public void update(Event vacationEvent, int dayDelta) {
		getDao().update(vacationEvent, dayDelta);
	}
}