package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.ScheduleEvent;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.base.BaseView;
import de.fh_dortmund.ticket_system.business.EventData;
import de.fh_dortmund.ticket_system.business.EventModel;
import de.fh_dortmund.ticket_system.entity.Event;

/**
 * View for team vacation calendar.
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@ViewScoped
public class TeamVacationView extends BaseView implements Serializable {

	@ManagedProperty("#{auth}")
	private Authentication auth;

	@ManagedProperty("#{eventData}")
	private EventData data;

	private static final long serialVersionUID = 1L;
	private EventModel eventModel;

	private ScheduleEvent event = new Event();

	public TeamVacationView() {
		setEventModel(new EventModel());
	}

	@PostConstruct
	public void init() {
		getEventModel().setAuth(getAuth());
		getEventModel().setData(getData());
	}

	public Date getInitialDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY,
				calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar.getTime();
	}

	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setData(EventData data) {
		this.data = data;
	}

	public void setEventModel(EventModel eventModel) {
		this.eventModel = eventModel;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public EventModel getEventModel() {
		return eventModel;
	}

	public EventData getData() {
		return data;
	}
}
