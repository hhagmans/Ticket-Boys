package de.fh_dortmund.ticket_system.util;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.ee.servlet.QuartzInitializerServlet;

import de.fh_dortmund.ticket_system.business.ConflictData;

@ManagedBean
@ApplicationScoped
public class ConflictReminder extends QuartzInitializerServlet implements Job,
		Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{conflictData}")
	private ConflictData conflictData;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		DailyChecker.conflictTrigger();
	}

	public ConflictData getConflictData() {
		return conflictData;
	}

	public void setConflictData(ConflictData conflictData) {
		this.conflictData = conflictData;
	}
}
