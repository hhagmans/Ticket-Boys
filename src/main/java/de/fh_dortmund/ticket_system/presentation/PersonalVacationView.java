package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.ScheduleEvent;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.base.BaseView;
import de.fh_dortmund.ticket_system.business.ConflictFinder;
import de.fh_dortmund.ticket_system.business.EventData;
import de.fh_dortmund.ticket_system.business.PersonalEventModel;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.EventType;

@ManagedBean
@ViewScoped
public class PersonalVacationView extends BaseView implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{auth}")
	private Authentication auth;

	private Employee employee;

	@ManagedProperty("#{eventData}")
	private EventData data;

	private PersonalEventModel eventModel;
	private ScheduleEvent event = new Event();

	@ManagedProperty("#{conflictFinder}")
	private ConflictFinder conflictFinder;

	public PersonalVacationView() {
		setEventModel(new PersonalEventModel());
	}

	@PostConstruct
	public void init() {
		getEventModel().setAuth(getAuth());
		getEventModel().setData(getData());
		this.setEmployee(getAuth().getEmployee());
	}

	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random
																	// day of
																	// month

		return date.getTime();
	}

	public Date getInitialDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY,
				calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar.getTime();
	}

	public PersonalEventModel getEventModel() {
		return eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null) {
			Event tempEvent = (Event) event;
			tempEvent.setEmployee(getAuth().getEmployee());
			if (!getConflictFinder().checkVacation(tempEvent)) {
				addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Neuer Konflikt!",
						"Der soeben geplante Urlaub kollidiert mit Ihrer Dispatcher-Schicht."));
			}
			getEventModel().addEvent(tempEvent);
		} else {
			getEventModel().updateEvent(event);
		}

		event = new Event();
	}

	public void deleteEvent(ActionEvent actionEvent) {
		if (getEventModel().getEvent(event.getId()) == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Ereignis nicht vorhanden",
					"Ereignis nicht vorhanden und daher nicht löschbar.");

			addMessage(message);
		} else {
			Event e = (Event) event;
			FacesMessage message = null;
			switch (e.getEventType()) {
			case holiday:
				message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Fehlende Berechtigung",
						"Sie können keine nationalen Feiertage löschen...");
				addMessage(message);
				break;
			case dispatcher:
				message = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Hier nicht möglich",
						"Für Änderungen an Ihrer Dispatcher-Schicht wechseln Sie bitte zur Dispatcher-Ansicht!");
				addMessage(message);
				break;
			default:
				getEventModel().deleteEvent(event);
				break;
			}
		}
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		Date selectedDate = (Date) selectEvent.getObject();
		event = new Event("", selectedDate, selectedDate, EventType.vacation);
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {

		Event tempEvent = (Event) event.getScheduleEvent();
		FacesMessage message = null;
		switch (tempEvent.getEventType()) {
		case holiday:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Fehlende Berechtigung",
					"Sie können keine nationalen Feiertage verschieben...");
			addMessage(message);
			break;
		case dispatcher:
			message = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Hier nicht möglich",
					"Für Änderungen an Ihrer Dispatcher-Schicht wechseln Sie bitte zur Dispatcher-Ansicht!");
			addMessage(message);
			break;
		default:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Ereignis verschoben", "Verschoben um: "
							+ event.getDayDelta() + " Tage.");
			getEventModel().updateEvent(tempEvent);
			addMessage(message);
			break;
		}

	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		Event tempEvent = (Event) event.getScheduleEvent();
		FacesMessage message = null;
		switch (tempEvent.getEventType()) {
		case holiday:
			message = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Netter Versuch..",
					".. aber das Leben ist kein Ponyhof. Feiertage kann man nicht einfach verlängern.");
			addMessage(message);
			break;
		case dispatcher:
			message = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Hier nicht möglich",
					"Für Änderungen an Ihrer Dispatcher-Schicht wechseln Sie bitte zur Dispatcher-Ansicht!");
			addMessage(message);
			break;
		default:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Ereignis verändert", "Verändert um: "
							+ event.getDayDelta() + " Tage.");
			getEventModel().updateEvent(event.getScheduleEvent(),
					event.getDayDelta());
			addMessage(message);
			break;
		}
	}

	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public EventData getData() {
		return data;
	}

	public void setData(EventData data) {
		this.data = data;
	}

	public void setEventModel(PersonalEventModel eventModel) {
		this.eventModel = eventModel;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ConflictFinder getConflictFinder() {
		return conflictFinder;
	}

	public void setConflictFinder(ConflictFinder conflictFinder) {
		this.conflictFinder = conflictFinder;
	}

	public EventType[] getEventTypes() {
		EventType[] e = { EventType.vacation, EventType.other };
		return e;
	}
}
