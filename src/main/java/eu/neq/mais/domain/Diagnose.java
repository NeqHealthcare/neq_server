/**
 * 
 */
package eu.neq.mais.domain;

import java.util.Date;
import java.util.List;

/**
 * @author user
 *
 */
public abstract class Diagnose {
	
	private Date diagnoseDate, healed_date;
	private Doctor doctor;
	private List<Disease> disease;
	

	/**
	 * @return the healed_date
	 */
	public Date getHealed_date() {
		return healed_date;
	}
	/**
	 * @param healed_date the healed_date to set
	 */
	
	public void setHealed_date(Date healed_date) {
		this.healed_date = healed_date;
	}
	public Date getDiagnoseDate() {
		return diagnoseDate;
	}
	public void setDiagnoseDate(Date diagnoseDate) {
		this.diagnoseDate = diagnoseDate;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public List<Disease> getDisease() {
		return disease;
	}
	public void setDisease(List<Disease> disease) {
		this.disease = disease;
	}

}
