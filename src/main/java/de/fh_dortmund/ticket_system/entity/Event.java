package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.model.ScheduleEvent;

@Entity
@Table(name = "Event")
@NamedQueries({
		@NamedQuery(name = "findAll", query = "SELECT v FROM Event v"),
		@NamedQuery(name = "findByUser", query = "SELECT v FROM Event v WHERE v.employee = :employee") })
public class Event implements ScheduleEvent, Serializable {

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

	private EventType eventType;

	public Event(String title, Date startDate, Date endDate, EventType eventType) {
		this.title = title;
		this.personalTitle = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setEventType(eventType);
	}

	public Event(String id, String title, Date startDate, Date endDate,
			EventType eventType) {
		this.id = id;
		this.title = title;
		this.personalTitle = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setEventType(eventType);
	}

	public Event(String id, String title, Date startDate, Date endDate,
			EventType eventType, Employee emp) {
		this.employee = emp;
		this.id = id;
		this.title = title;
		this.personalTitle = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setEventType(eventType);
	}

	public Event() {
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

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinTable(name = "employeeID")
	// @JoinTable(name = "vacations", joinColumns = { @JoinColumn(name =
	// "vacationID") }, inverseJoinColumns = { @JoinColumn(name = "employeeID")
	// })
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
	public String toString() {
		return "VacationEvent{title=" + title + ",startDate=" + startDate
				+ ",endDate=" + endDate + "}";
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (allDay ? 1231 : 1237);
		result = (prime * result) + (editable ? 1231 : 1237);
		result = (prime * result)
				+ ((employee == null) ? 0 : employee.hashCode());
		result = (prime * result)
				+ ((endDate == null) ? 0 : endDate.hashCode());
		result = (prime * result)
				+ ((eventType == null) ? 0 : eventType.hashCode());
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result)
				+ ((personalTitle == null) ? 0 : personalTitle.hashCode());
		result = (prime * result)
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = (prime * result)
				+ ((styleClass == null) ? 0 : styleClass.hashCode());
		result = (prime * result) + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Event other = (Event) obj;
		if (allDay != other.allDay) {
			return false;
		}
		if (editable != other.editable) {
			return false;
		}
		if (employee == null) {
			if (other.employee != null) {
				return false;
			}
		} else if (!employee.equals(other.employee)) {
			return false;
		}
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (eventType != other.eventType) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (personalTitle == null) {
			if (other.personalTitle != null) {
				return false;
			}
		} else if (!personalTitle.equals(other.personalTitle)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		if (styleClass == null) {
			if (other.styleClass != null) {
				return false;
			}
		} else if (!styleClass.equals(other.styleClass)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

}
