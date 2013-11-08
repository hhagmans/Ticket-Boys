package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

@ManagedBean
@SessionScoped
public class ShiftModel extends ListDataModel<Shift> implements SelectableDataModel<Shift>, Serializable
{

	private static final long serialVersionUID = 1L;

	public ShiftModel()
	{

	}

	public ShiftModel(List<Shift> data)
	{
		super(data);
	}

	@Override
	public Shift getRowData(String rowKey)
	{
		//In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  

		List<Shift> Shifts = (List<Shift>) getWrappedData();

		for (Shift Shift : Shifts)
		{
			if (Shift.getUniqueRowKey().equals(rowKey))
			{
				return Shift;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(Shift Shift)
	{
		return Shift.getUniqueRowKey();
	}

}