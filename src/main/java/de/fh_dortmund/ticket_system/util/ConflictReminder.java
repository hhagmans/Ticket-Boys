package de.fh_dortmund.ticket_system.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ConflictReminder implements Job
{
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		//DailyChecker.conflictTrigger();
	}
}
