package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import de.fh_dortmund.ticket_system.business.VacationEvent;

public class VacationView implements Serializable
{

	private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;

	private ScheduleEvent event = new VacationEvent();

	public VacationView()
	{
		eventModel = new DefaultScheduleModel();
		eventModel.addEvent(new VacationEvent("Default Event", someDate(), anotherDate()));
	}

	private Date someDate()
	{

		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);

		return t.getTime();
	}

	private Date anotherDate()
	{

		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);

		return t.getTime();
	}

	public Date getRandomDate(Date base)
	{
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); //set random day of month  

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
			eventModel.addEvent(event);
		}
		else
		{
			eventModel.updateEvent(event);
		}

		event = new VacationEvent();
	}

	public void onEventSelect(SelectEvent selectEvent)
	{
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent)
	{
		event = new VacationEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
	}

	public void onEventMove(ScheduleEntryMoveEvent event)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ereignis verschoben", "Verschoben um: "
			+ event.getDayDelta());

		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ereignis verändert", "Verändert um: "
			+ event.getDayDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message)
	{
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
