package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import de.fh_dortmund.ticket_system.entity.Conflict;

/**
 * Model for conflicts used by views to get conflicts.
 * 
 * @author Ticket-Boys
 * 
 */
public class ConflictModel extends ListDataModel<Conflict> implements
		SelectableDataModel<Conflict>, Serializable {
	private static final long serialVersionUID = 1L;

	public ConflictModel(List<Conflict> data) {
		super(data);
	}

	@Override
	public Conflict getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<Conflict> Conflicts = (List<Conflict>) getWrappedData();

		for (Conflict Conflict : Conflicts) {
			if (Conflict.getId().equals(rowKey)) {
				return Conflict;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(Conflict Conflict) {
		return Conflict.getId();
	}

}