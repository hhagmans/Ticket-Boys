package de.fh_dortmund.ticket_system.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.fh_dortmund.ticket_system.persistence.ShiftDAOImpl;


/**
 * Dieses Objekt berechnet und verwaltet den Dispatcher-Schichtplan (Liste von Shift-Objekten) 
 * 
 * @author Ticket-Boys
 *
 */

@ManagedBean
@SessionScoped
public class ShiftsData implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private ShiftModel shiftModel;
	private ShiftDAOImpl shiftDAO;
	
	public ShiftsData() {
		shiftDAO = new ShiftDAOImpl();
		shiftModel = new ShiftModel(shiftDAO.getAllShifts());
		
	}


	public ShiftModel getShiftModel() {
		return shiftModel;
	}
	
}