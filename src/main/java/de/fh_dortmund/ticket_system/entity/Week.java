package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a unique week in a year.
 * 
 * @author Alex Hofmann
 * 
 */
@Entity
@Table(name = "week")
public class Week implements Serializable
{

	private static final long serialVersionUID = 1L;
	private int year;
	private int weekNumber;
	private String uniqueRowKey;

	public Week()
	{

	}

	/**
	 * Creates a Week object with the given parameters.
	 * 
	 * @param year
	 * @param weekNumber
	 */
	public Week(int year, int weekNumber)
	{
		this.setYear(year);
		this.setWeekNumber(weekNumber);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}

		if (getClass() != obj.getClass())
		{
			return false;
		}

		Week kw = (Week) obj;
		if ((this.getYear() == kw.getYear()) && (this.getWeekNumber() == kw.getWeekNumber()))
		{
			return true;
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + year;
		result = (prime * result) + weekNumber;

		return result;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return new Week(year, weekNumber);
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public int getWeekNumber()
	{
		return weekNumber;
	}

	public void setWeekNumber(int kw)
	{
		this.weekNumber = kw;
	}

	@Id
	public String getUniqueRowKey()
	{
		return getYear() + "-" + getWeekNumber();
	}

	public void setUniqueRowKey(String uniqueRowKey)
	{
		this.uniqueRowKey = uniqueRowKey;
	}

	/**
	 * 
	 * @param otherWeek
	 * @return returns true if the week, on which this method is called, is after the given week.
	 *         returns false if it is before or equals.
	 */
	public boolean isAfter(Week otherWeek)
	{

		if (this.year > otherWeek.getYear())
		{
			return true;
		}
		else if (this.year == otherWeek.getYear())
		{
			if (this.weekNumber > otherWeek.getWeekNumber())
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		else
		{
			return false;
		}

	}
}
