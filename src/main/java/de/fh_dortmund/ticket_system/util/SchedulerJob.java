package de.fh_dortmund.ticket_system.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.fh_dortmund.ticket_system.business.ShiftData;

public class SchedulerJob implements Job {
 
	ShiftData shiftData = new ShiftData();
	
	public SchedulerJob(){
		shiftData.fill();
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DailyChecker.trigger(shiftData);
	}
 
}