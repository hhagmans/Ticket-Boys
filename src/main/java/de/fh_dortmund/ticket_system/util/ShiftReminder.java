package de.fh_dortmund.ticket_system.util;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.fh_dortmund.ticket_system.persistence.ShiftDaoSqlite;

/**
 * Class to remind users of their upcoming chifts.
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@ApplicationScoped
public class ShiftReminder implements Job, Serializable {

	private static final long serialVersionUID = 1L;

	private ShiftDaoSqlite dao = new ShiftDaoSqlite();

	public ShiftReminder() {

	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		DailyChecker.trigger(dao);
	}

	public ShiftDaoSqlite getDao() {
		return dao;
	}

	public void setDao(ShiftDaoSqlite dao) {
		this.dao = dao;
	}

}