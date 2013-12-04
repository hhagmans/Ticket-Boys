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
import de.fh_dortmund.ticket_system.business.VacationData;
import de.fh_dortmund.ticket_system.business.VacationEventModel;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

@ManagedBean
@ViewScoped
public class TeamVacationView extends BaseView implements Serializable
{

	@ManagedProperty("#{auth}")
	private Authentication		auth;

	@ManagedProperty("#{vacationData}")
	private VacationData		data;

	private static final long	serialVersionUID	= 1L;

	private VacationEventModel	eventModel;

	private ScheduleEvent		event				= new VacationEvent();

	public TeamVacationView()
	{
		setEventModel(new VacationEventModel());
	}

	@PostConstruct
	public void init()
	{
		getEventModel().setAuth(getAuth());
		getEventModel().setData(getData());
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

	public VacationEventModel getEventModel()
	{
		return eventModel;
	}

	public Authentication getAuth()
	{
		return auth;
	}

	public void setAuth(Authentication auth)
	{
		this.auth = auth;
	}

	public VacationData getData()
	{
		return data;
	}

	public void setData(VacationData data)
	{
		this.data = data;
	}

	public void setEventModel(VacationEventModel eventModel)
	{
		this.eventModel = eventModel;
	}

	public ScheduleEvent getEvent()
	{
		return event;
	}

	public void setEvent(ScheduleEvent event)
	{
		this.event = event;
	}

	public void onEventSelect(SelectEvent selectEvent)
	{
		event = (ScheduleEvent) selectEvent.getObject();
	}
}
