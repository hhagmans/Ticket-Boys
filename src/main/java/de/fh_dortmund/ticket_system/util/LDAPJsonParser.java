package de.fh_dortmund.ticket_system.util;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;

/**
 * Class to Parse Json given by the Evonik LDAP system.
 * 
 * @author Ticket-Boys
 * 
 */

public class LDAPJsonParser {

	/**
	 * Parses a given jsonString to an employee
	 * 
	 * @author Ticket-Boys
	 * 
	 */
	
	private static Employee employee;
	
	public static Employee parseEmployee(String jsonLine) {
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();

		if (jobject.get("error").toString().trim().length() < 3) {
			String konzernID = removeQuotes(jobject.get("kid").toString());
			String firstName = removeQuotes(jobject.get("vorname").toString());
			String lastName = removeQuotes(jobject.get("nachname").toString());
			String city = removeQuotes(jobject.get("standort").toString());
			HolidayCalendarType holidayCalendarType = HolidayCalendarType.germanyNRW;
			if (city.toLowerCase().contains("frankfurt")) {
				holidayCalendarType = HolidayCalendarType.germanyHessen;
			} else if (jobject.get("land").toString().toLowerCase()
					.contains("bulgari")) {
				holidayCalendarType = HolidayCalendarType.bulgariaAll;
			}

			String email = removeQuotes(jobject.get("email").toString());


			if (removeQuotes(jobject.get("orgeinheit").toString()).equals("BS-AS-TE-WAM1") || removeQuotes(jobject.get("orgeinheit").toString()).equals("BS-AS-TE-WAM2")) {
				employee = new Employee(konzernID, firstName, lastName, city, Role.dispatcher, 0, 0, holidayCalendarType, email);	
	
			} else if (removeQuotes(jobject.get("kid").toString().toLowerCase()).equals("s20376")) {
				employee = new Employee(konzernID, firstName, lastName, city, Role.admin, 0, 0, holidayCalendarType, email);	
		
			} else {
				employee = new Employee(konzernID, firstName, lastName, city, Role.guest, 0, 0, holidayCalendarType, email);	
		
			} return employee;
		} else {
			return null;
		}	
	}
	
//	method checks if user in given string is authenticated in Evonik LDAP system
	public static boolean checkEmployee(String jsonLine) {
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		if (jobject.get("rc").toString().equals("0")) {
			return true;
		}
		return false;
	}

	private static String removeQuotes(String word) {
		return word.replace("\"", "");
	}
	
	public static Employee getActEmployee() {
		return employee;
	}
	
}
