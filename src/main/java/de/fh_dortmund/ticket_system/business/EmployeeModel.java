package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import de.fh_dortmund.ticket_system.entity.Employee;

public class EmployeeModel extends ListDataModel<Employee> implements SelectableDataModel<Employee>, Serializable
{
	private static final long	serialVersionUID	= 1L;

	public EmployeeModel(List<Employee> data)
	{
		super(data);
	}

	@Override
	public Employee getRowData(String rowKey)
	{
		// In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data

		List<Employee> Employees = (List<Employee>) getWrappedData();
		System.out.println("Employees isnull?" + Employees != null);
		for (Employee Employee : Employees)
		{
			if (Employee.getKonzernID().equals(rowKey))
			{
				return Employee;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(Employee Employee)
	{
		return Employee.getKonzernID();
	}

}
