package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Dieses Objekt repräsentiert eine Schicht Eine Schicht ist die Zuordnung einer Kalenderwoche zu
 * einem Dispatcher und seinem Vertreter
 * 
 * @author Ticket-Boys
 * 
 */

@Entity
@Table(name = "shift")
public class Shift implements Serializable
{

	private static final long serialVersionUID = 1L;

	private int year;
	private int weekNumber;
	private Employee dispatcher;
	private Employee substitutioner;
	private String uniqueRowKey;

	public Shift()
	{
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

	public void setWeekNumber(int weekNumber)
	{
		this.weekNumber = weekNumber;
	}

	public Employee getDispatcher()
	{
		return dispatcher;
	}

	public void setDispatcher(Employee dispatcher)
	{
		this.dispatcher = dispatcher;
	}

	public Employee getSubstitutioner()
	{
		return substitutioner;
	}

	public void setSubstitutioner(Employee substitutioner)
	{
		this.substitutioner = substitutioner;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{

		Shift newShift = new Shift(this.getYear(), this.getWeekNumber(), this.getDispatcher(), this.getSubstitutioner());

		return newShift;
	}

	/**
	 * 
	 * @param weekNumber Kalenderwoche der Schicht
	 * @param dispatcher vollständiger Name der dieser Schicht zugeteilten Dispatchers
	 * @param substitutioner vollständiger Name der dieser Schicht zugeteilten Vertreters des
	 *        Dispatchers
	 */
	public Shift(int year, int weekNumber, Employee dispatcher, Employee substitutioner)
	{
		super();
		this.year = year;
		this.weekNumber = weekNumber;
		this.dispatcher = dispatcher;
		this.substitutioner = substitutioner;
	}

	@Id
	public String getUniqueRowKey()
	{
		// TODO Auto-generated method stub
		return year + "-" + weekNumber;
	}

	public void setUniqueRowKey(String uniqueRowKey)
	{
		this.uniqueRowKey = uniqueRowKey;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((dispatcher == null) ? 0 : dispatcher.hashCode());
		result = (prime * result) + ((substitutioner == null) ? 0 : substitutioner.hashCode());
		result = (prime * result) + ((uniqueRowKey == null) ? 0 : uniqueRowKey.hashCode());
		result = (prime * result) + weekNumber;
		result = (prime * result) + year;
		return result;
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
		Shift other = (Shift) obj;
		if (dispatcher == null)
		{
			if (other.dispatcher != null)
			{
				return false;
			}
		}
		else if (!dispatcher.equals(other.dispatcher))
		{
			return false;
		}
		if (substitutioner == null)
		{
			if (other.substitutioner != null)
			{
				return false;
			}
		}
		else if (!substitutioner.equals(other.substitutioner))
		{
			return false;
		}
		if (uniqueRowKey == null)
		{
			if (other.uniqueRowKey != null)
			{
				return false;
			}
		}
		else if (!uniqueRowKey.equals(other.uniqueRowKey))
		{
			return false;
		}
		if (weekNumber != other.weekNumber)
		{
			return false;
		}
		if (year != other.year)
		{
			return false;
		}
		return true;
	}

	//	@Override
	//	public boolean equals(Object obj)
	//	{
	//		if (obj instanceof Shift)
	//		{
	//			Shift shift = (Shift) obj;
	//
	//			return shift.getUniqueRowKey().equals(this.getUniqueRowKey());
	//
	//		}
	//		return false;
	//	}

}
