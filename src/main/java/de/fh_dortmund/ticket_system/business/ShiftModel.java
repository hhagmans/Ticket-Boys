package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import de.fh_dortmund.ticket_system.entity.Shift;

/**
 * Model for shifts used by views to get shifts.
 * 
 * @author Ticket-Boys
 * 
 */
public class ShiftModel extends ListDataModel<Shift> implements
		SelectableDataModel<Shift>, Serializable {
	private static final long serialVersionUID = 1L;

	public ShiftModel(List<Shift> data) {
		super(data);
	}

	@Override
	public Shift getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<Shift> Shifts = (List<Shift>) getWrappedData();

		for (Shift shift : Shifts) {
			if (shift.getUniqueRowKey().equals(rowKey)) {
				return shift;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(Shift Shift) {
		return Shift.getUniqueRowKey();
	}

}