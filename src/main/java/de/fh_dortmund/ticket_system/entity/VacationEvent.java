package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.model.ScheduleEvent;

@Entity
@Table(name = "vacationEvent")
@NamedQueries({
		@NamedQuery(name = "findAll", query = "SELECT v FROM VacationEvent v"),
		@NamedQuery(name = "findByUser", query = "SELECT v FROM VacationEvent v WHERE v.employee = :employee") })
public class VacationEvent implements ScheduleEvent, Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String title;

	private Date startDate;

	private Date endDate;

	private String personalTitle;

	private boolean allDay = true;

	private String styleClass;

	private boolean editable = true;

	private Employee employee;

	private boolean isVacation = true;

	public VacationEvent(String title, Date startDate, Date endDate,
			boolean vacation) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setIsVacation(vacation);
	}

	private String generateID(String title, Date startDate, Date endDate) {
		return startDate.toString() + "-" + endDate.toString() + "-" + title;
	}

	public VacationEvent() {
		this.id = null;
	}

	@Id
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	@Override
	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	@Transient
	@Override
	public Object getData() {
		return null;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "vacations", joinColumns = { @JoinColumn(name = "vacationID") }, inverseJoinColumns = { @JoinColumn(name = "employeeID") })
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getPersonalTitle() {
		return personalTitle;
	}

	public void setPersonalTitle(String teamTitle) {
		this.personalTitle = teamTitle;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final VacationEvent other = (VacationEvent) obj;
		if ((this.title == null) ? (other.getTitle() != null) : !this.title
				.equals(other.getTitle())) {
			return false;
		}
		if ((this.startDate != other.getStartDate())
				&& ((this.startDate == null) || !this.startDate.equals(other
						.getStartDate()))) {
			return false;
		}
		if ((this.endDate != other.getEndDate())
				&& ((this.endDate == null) || !this.endDate.equals(other
						.getEndDate()))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = (61 * hash) + (this.title != null ? this.title.hashCode() : 0);
		hash = (61 * hash)
				+ (this.startDate != null ? this.startDate.hashCode() : 0);
		hash = (61 * hash)
				+ (this.endDate != null ? this.endDate.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "VacationEvent{title=" + title + ",startDate=" + startDate
				+ ",endDate=" + endDate + "}";
	}

	// needs that retarded name. Otherwise the property wont be found
	public boolean getIsVacation() {
		return isVacation;
	}

	public void setIsVacation(boolean isVacation) {
		this.isVacation = isVacation;
	}

}
