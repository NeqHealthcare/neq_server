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
	
	private Date diagnoseDate;
	private Doctor doctor;
	private List<Disease> disease;
	
	
	
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
