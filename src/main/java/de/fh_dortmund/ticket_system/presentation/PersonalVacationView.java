package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.ScheduleEvent;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.PersonalVacationEventModel;
import de.fh_dortmund.ticket_system.business.VacationData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

@ManagedBean
@ViewScoped
public class PersonalVacationView implements Serializable
{

	private static final long	serialVersionUID	= 1L;
	
	@ManagedProperty("#{auth}")
	private
	Authentication auth;
	
	private Employee employee;
	
	@ManagedProperty("#{vacationData}")
	private
	VacationData data;
	
	private PersonalVacationEventModel		eventModel;

	private ScheduleEvent		event				= new VacationEvent();
	
	public PersonalVacationView()
	{
		setEventModel(new PersonalVacationEventModel());
	}
	
	@PostConstruct
	public void init(){
		getEventModel().setAuth(getAuth());
		getEventModel().setData(getData());
		this.setEmployee(getAuth().getEmployee());
	}
	
	public Date getRandomDate(Date base)
	{
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random day of month

		return date.getTime();
	}

	public Date getInitialDate()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar.getTime();
	}

	public PersonalVacationEventModel getEventModel()
	{
		return eventModel;
	}

	private Calendar today()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar;
	}

	public ScheduleEvent getEvent()
	{
		return event;
	}

	public void setEvent(ScheduleEvent event)
	{
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent)
	{
		if (event.getId() == null)
		{
			VacationEvent vacEvent = (VacationEvent) event;
			vacEvent.setEmployee(getAuth().getEmployee());
			getEventModel().addEvent(vacEvent);
		}
		else
		{
			getEventModel().updateEvent(event);
		}

		event = new VacationEvent();
	}
	
	public void deleteEvent(ActionEvent actionEvent)
	{
		if (getEventModel().getEvent(event.getId()) == null)
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ereignis nicht vorhanden", "Ereignis nicht vorhanden und daher nicht löschbar.");

			addMessage(message);
		}
		else
		{
			getEventModel().deleteEvent(event);
		}
	}

	public void onEventSelect(SelectEvent selectEvent)
	{
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent)
	{
		Date selectedDate = (Date) selectEvent.getObject();
		event = new VacationEvent("", selectedDate, selectedDate, true);
	}

	public void onEventMove(ScheduleEntryMoveEvent event)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ereignis verschoben", "Verschoben um: "
				+ event.getDayDelta() + " Tage.");
		getEventModel().updateEvent(event.getScheduleEvent());
		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ereignis verändert", "Verändert um: "
				+ event.getDayDelta() + " Tage.");
		getEventModel().updateEvent(event.getScheduleEvent());
		addMessage(message);
	}

	private void addMessage(FacesMessage message)
	{
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Authentication getAuth() {
		return auth;
	}
	
	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public VacationData getData() {
		return data;
	}

	public void setData(VacationData data) {
		this.data = data;
	}

	public void setEventModel(PersonalVacationEventModel eventModel) {
		this.eventModel = eventModel;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
