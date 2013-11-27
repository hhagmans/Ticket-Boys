package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.business.VacationEventModel;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.fh_dortmund.ticket_system.util.RightsManager;

@ManagedBean
@ViewScoped
public class PersonalVacationView implements Serializable
{

	private static final long	serialVersionUID	= 1L;

	private VacationEventModel		eventModel;

	private ScheduleEvent		event				= new VacationEvent();
	
	@ManagedProperty("#{rightsManager}")
	private RightsManager				rightsManager;

	public PersonalVacationView()
	{
		eventModel = new VacationEventModel();
	}

	private Date someDate()
	{
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);

		return t.getTime();
	}

	private Date anotherDate()
	{

		Calendar t = (Calendar) today().clone();
		t.set(Calendar.DATE, t.get(Calendar.DATE));

		return t.getTime();
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

	public ScheduleModel getEventModel()
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
			vacEvent.setEmployee(rightsManager.getCurrentUser());
			eventModel.addEvent(vacEvent);
		}
		else
		{
			eventModel.updateEvent(event);
		}

		event = new VacationEvent();
	}
	
	public void deleteEvent(ActionEvent actionEvent)
	{
		if (eventModel.getEvent(event.getId()) == null)
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ereignis nicht vorhanden", "Ereignis nicht vorhanden und daher nicht löschbar.");

			addMessage(message);
		}
		else
		{
			eventModel.deleteEvent(event);
		}
	}

	public RightsManager getRightsManager()
	{
		return rightsManager;
	}

	public void setRightsManager(RightsManager rightsManager)
	{
		this.rightsManager = rightsManager;
	}
	
	public void onEventSelect(SelectEvent selectEvent)
	{
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent)
	{
		Date selectedDate = (Date) selectEvent.getObject();
		event = new VacationEvent("", selectedDate, selectedDate);
	}

	public void onEventMove(ScheduleEntryMoveEvent event)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ereignis verschoben", "Verschoben um: "
				+ event.getDayDelta() + " Tage.");

		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ereignis verändert", "Verändert um: "
				+ event.getDayDelta() + " Tage.");

		addMessage(message);
	}

	private void addMessage(FacesMessage message)
	{
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
