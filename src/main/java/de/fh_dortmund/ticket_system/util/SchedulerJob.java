package de.fh_dortmund.ticket_system.util;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.fh_dortmund.ticket_system.persistence.ShiftDaoSqlite;

@ManagedBean
@ApplicationScoped
public class SchedulerJob implements Job, Serializable
{

	private static final long serialVersionUID = 1L;

	private ShiftDaoSqlite dao = new ShiftDaoSqlite();

	public SchedulerJob()
	{

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		System.out.println("!!!!..............---Daily Trigger------.............!!!!");

		DailyChecker.trigger(dao);
	}

	public ShiftDaoSqlite getDao()
	{
		return dao;
	}

	public void setDao(ShiftDaoSqlite dao)
	{
		this.dao = dao;
	}

}