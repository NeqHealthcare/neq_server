/**
 * 
 */
package eu.neq.mais.domain.gnuhealth;

import java.util.List;

import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.domain.Disease;
import eu.neq.mais.domain.Doctor;

/**
 * @author user
 *
 */
public class DiagnoseGnu extends Diagnose{

	
//	private Date diagnoseDate, healed_date;
	private DoctorGnu doctor;
	private List<Disease> disease;
	
	private boolean pregnancy_warning, is_active, is_infectious, is_allergy, is_on_treatment;
    private String short_comment, disease_severity, id, pathology_rec_name,  doctor_rec_name,
    treatment_description, extra_info, allergy_type, pds_code_rec_name, status;
    int  pcs_code, pathology, age, weeks_of_pregnancy;
    private DateGnu diagnosed_date, healed_date, date_start_treatment, date_stop_treatment;
	
//	public DiagnoseGnu(Date diagnoseDate, Date healed_date, Doctor doctor,
//			List<Disease> disease, boolean status, boolean pregnancy_warning,
//			boolean is_active, boolean is_infectious, boolean is_allergy,
//			String short_comment, String disease_severity) {
//		
//		this.setDiagnoseDate(diagnoseDate);
//		this.setHealed_date(healed_date);
//		this.setDoctor(doctor);
//		this.setDisease(disease);
//		
//		this.status = status;
//		this.pregnancy_warning = pregnancy_warning;
//		this.is_active = is_active;
//		this.is_infectious = is_infectious;
//		this.is_allergy = is_allergy;
//		this.short_comment = short_comment;
//		this.disease_severity = disease_severity;
//		
//	}
//	

//	/**
//	 * @return the healed_date
//	 */
//	public Date getHealed_date() {
//		return healed_date;
//	}
//	/**
//	 * @param healed_date the healed_date to set
//	 */
//	
//	public void setHealed_date(Date healed_date) {
//		this.healed_date = healed_date;
//	}
//	public Date getDiagnoseDate() {
//		return diagnoseDate;
//	}
//	public void setDiagnoseDate(Date diagnoseDate) {
//		this.diagnoseDate = diagnoseDate;
//	}
    
    public DiagnoseGnu returnLatest(DiagnoseGnu dg){
    	
    	if(this.getDiagnosed_date().latest(dg.getDiagnosed_date())){
    		return this;
    	}else{
    		return dg;
    	}
    }
    
//	public Doctor getDoctor() {
//		return doctor;
//	}
//	public void setDoctor(DoctorGnu doctor) {
//		this.doctor = doctor;
//	}
	public List<Disease> getDisease() {
		return disease;
	}
	public void setDisease(List<Disease> disease) {
		this.disease = disease;
	}
	

	/**
	 * @return the status
	 */
//	public boolean isStatus() {
//		return status;
//	}

	/**
	 * @param status the status to set
	 */
//	public void setStatus(boolean status) {
//		this.status = status;
//	}

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
	public DateGnu getDiagnosed_date() {
		return diagnosed_date;
	}
	public void setDiagnosed_date(DateGnu diagnosed_date) {
		this.diagnosed_date = diagnosed_date;
	}
	public String getShort_comment() {
		return short_comment;
	}
	public void setShort_comment(String short_comment) {
		this.short_comment = short_comment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public String getPathology() {
//		return pathology;
//	}
//	public void setPathology(String pathology) {
//		this.pathology = pathology;
//	}
	public String getDisease_severity() {
		return disease_severity;
	}
	public void setDisease_severity(String disease_severity) {
		this.disease_severity = disease_severity;
	}
	public String getPathology_rec_name() {
		return pathology_rec_name;
	}
	public void setPathology_rec_name(String pathology_rec_name) {
		this.pathology_rec_name = pathology_rec_name;
	}
	public DateGnu getHealed_date() {
		return healed_date;
	}
	public void setHealed_date(DateGnu healed_date) {
		this.healed_date = healed_date;
	}

//	/**
//	 * @return the short_comment
//	 */
//	public String getShort_comment() {
//		return short_comment;
//	}
//
//	/**
//	 * @param short_comment the short_comment to set
//	 */
//	public void setShort_comment(String short_comment) {
//		this.short_comment = short_comment;
//	}
//
//	/**
//	 * @return the disease_severity
//	 */
//	public String getDisease_severity() {
//		return disease_severity;
//	}
//
//	/**
//	 * @param disease_severity the disease_severity to set
//	 */
//	public void setDisease_severity(String disease_severity) {
//		this.disease_severity = disease_severity;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getRec_name() {
//		return rec_name;
//	}
//
//	public void setRec_name(String rec_name) {
//		this.rec_name = rec_name;
//	}
//
//	public String getDiagnosed_date() {
//		return diagnosed_date;
//	}
//
//	public void setDiagnosed_date(String diagnosed_date) {
//		this.diagnosed_date = diagnosed_date;
//	}
//
//	public String getPathology() {
//		return pathology;
//	}
//
//	public void setPathology(String pathology) {
//		this.pathology = pathology;
//	}

}
