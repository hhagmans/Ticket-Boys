package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.ScheduleEvent;

public class VacationEvent implements ScheduleEvent, Serializable
{

	private String id;

	private String title;

	private Date startDate;

	private Date endDate;

	private boolean allDay = true;

	private String styleClass;

	private Object data;

	private boolean editable = true;

	public VacationEvent(String title, Date startDate, Date endDate)
	{
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public VacationEvent()
	{

	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Override
	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	@Override
	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	@Override
	public boolean isAllDay()
	{
		return allDay;
	}

	public void setAllDay(boolean allDay)
	{
		this.allDay = allDay;
	}

	@Override
	public String getStyleClass()
	{
		return styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}

	@Override
	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	@Override
	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

}
