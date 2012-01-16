/**
 * 
 */
package eu.neq.mais.domain.gnuhealth;

import java.util.Date;
import java.util.List;

import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.domain.Disease;
import eu.neq.mais.domain.Doctor;

/**
 * @author user
 *
 */
public class DiagnoseGnu extends Diagnose{
	


	private boolean status, pregnancy_warning, is_active, is_infectious, is_allergy;
    private String short_comment, disease_severity;
	
	public DiagnoseGnu(Date diagnoseDate, Date healed_date, Doctor doctor,
			List<Disease> disease, boolean status, boolean pregnancy_warning,
			boolean is_active, boolean is_infectious, boolean is_allergy,
			String short_comment, String disease_severity) {
		
		this.setDiagnoseDate(diagnoseDate);
		this.setHealed_date(healed_date);
		this.setDoctor(doctor);
		this.setDisease(disease);
		
		this.status = status;
		this.pregnancy_warning = pregnancy_warning;
		this.is_active = is_active;
		this.is_infectious = is_infectious;
		this.is_allergy = is_allergy;
		this.short_comment = short_comment;
		this.disease_severity = disease_severity;
		
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the pregnancy_warning
	 */
	public boolean isPregnancy_warning() {
		return pregnancy_warning;
	}

	/**
	 * @param pregnancy_warning the pregnancy_warning to set
	 */
	public void setPregnancy_warning(boolean pregnancy_warning) {
		this.pregnancy_warning = pregnancy_warning;
	}

	/**
	 * @return the is_active
	 */
	public boolean isIs_active() {
		return is_active;
	}

	/**
	 * @param is_active the is_active to set
	 */
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	/**
	 * @return the is_infectious
	 */
	public boolean isIs_infectious() {
		return is_infectious;
	}

	/**
	 * @param is_infectious the is_infectious to set
	 */
	public void setIs_infectious(boolean is_infectious) {
		this.is_infectious = is_infectious;
	}

	/**
	 * @return the is_allergy
	 */
	public boolean isIs_allergy() {
		return is_allergy;
	}

	/**
	 * @param is_allergy the is_allergy to set
	 */
	public void setIs_allergy(boolean is_allergy) {
		this.is_allergy = is_allergy;
	}

	/**
	 * @return the short_comment
	 */
	public String getShort_comment() {
		return short_comment;
	}

	/**
	 * @param short_comment the short_comment to set
	 */
	public void setShort_comment(String short_comment) {
		this.short_comment = short_comment;
	}

	/**
	 * @return the disease_severity
	 */
	public String getDisease_severity() {
		return disease_severity;
	}

	/**
	 * @param disease_severity the disease_severity to set
	 */
	public void setDisease_severity(String disease_severity) {
		this.disease_severity = disease_severity;
	}

	
	
	
	
 
    
 

}
