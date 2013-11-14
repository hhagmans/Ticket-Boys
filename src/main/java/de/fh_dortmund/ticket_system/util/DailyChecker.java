package de.fh_dortmund.ticket_system.util;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class DailyChecker
{

	public static void check()
	{

		try
		{
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();

			/**
			 * @TODO check if Dispatcher KW - current KW <= 2 then execute EmailUtil
			 */
			try
			{
				Thread.sleep(1000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			scheduler.shutdown();

		}
		catch (SchedulerException se)
		{
			se.printStackTrace();
		}
	}
}
