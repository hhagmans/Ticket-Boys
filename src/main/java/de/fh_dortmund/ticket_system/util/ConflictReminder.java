package de.fh_dortmund.ticket_system.util;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.fh_dortmund.ticket_system.persistence.ConflictDao;
import de.fh_dortmund.ticket_system.persistence.ConflictDaoSqlite;

/**
 * Class for a daily reminder for existing conflicts.
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@ApplicationScoped
public class ConflictReminder implements Job, Serializable {

	private static final long serialVersionUID = 1L;

	private ConflictDao conflictDao = new ConflictDaoSqlite();

	/**
	 * Triggers the conflict checker.
	 * 
	 * @author Ticket-Boys
	 * 
	 */
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		DailyChecker.conflictTrigger(conflictDao);
	}

}
