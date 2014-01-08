package de.fh_dortmund.ticket_system.util;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.fh_dortmund.ticket_system.business.ShiftData;

@ManagedBean
@ApplicationScoped
public class SchedulerJob implements Job, Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{shiftData}")
	private ShiftData shiftData;

	public SchedulerJob() {
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out
				.println("..............---Daily Trigger------.............!!!!");

		DailyChecker.trigger(getShiftData());
	}

	public ShiftData getShiftData() {
		return shiftData;
	}

	public void setShiftData(ShiftData shiftData) {
		this.shiftData = shiftData;
	}

}