package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.Medication;

/**
 * 
 * @author seba
 *
 */
public class MedicationGnu extends Medication {
	
	private String dose,
	route,
	duration_period,
	frequency_unit,
	dose_unit,
	frequency,
	indication,
	notes,
	is_active,
	admin_times,
	common_dosage,
	discontinued_reason,
	duration,
	form_rec_name,// REPLACE
	doctor_rec_name,// REPLACE
	route_rec_name,// REPLACE
	dose_unit_rec_name,// REPLACE
	indication_rec_name,// REPLACE
	common_dosage_rec_name, // REPLACE
	course_completed,
	discontinued,
	medicament_rec_name;
	
	DateGnu start_treatment,
	end_treatment;

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getDuration_period() {
		return duration_period;
	}

	public void setDuration_period(String duration_period) {
		this.duration_period = duration_period;
	}

	public String getFrequency_unit() {
		return frequency_unit;
	}

	public void setFrequency_unit(String frequency_unit) {
		this.frequency_unit = frequency_unit;
	}

	public String getDose_unit() {
		return dose_unit;
	}

	public void setDose_unit(String dose_unit) {
		this.dose_unit = dose_unit;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getAdmin_times() {
		return admin_times;
	}

	public void setAdmin_times(String admin_times) {
		this.admin_times = admin_times;
	}

	public String getCommon_dosage() {
		return common_dosage;
	}

	public void setCommon_dosage(String common_dosage) {
		this.common_dosage = common_dosage;
	}

	public String getDiscontinued_reason() {
		return discontinued_reason;
	}

	public void setDiscontinued_reason(String discontinued_reason) {
		this.discontinued_reason = discontinued_reason;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getForm_rec_name() {
		return form_rec_name;
	}

	public void setForm_rec_name(String form_rec_name) {
		this.form_rec_name = form_rec_name;
	}

	public String getDoctor_rec_name() {
		return doctor_rec_name;
	}

	public void setDoctor_rec_name(String doctor_rec_name) {
		this.doctor_rec_name = doctor_rec_name;
	}

	public String getRoute_rec_name() {
		return route_rec_name;
	}

	public void setRoute_rec_name(String route_rec_name) {
		this.route_rec_name = route_rec_name;
	}

	public String getDose_unit_rec_name() {
		return dose_unit_rec_name;
	}

	public void setDose_unit_rec_name(String dose_unit_rec_name) {
		this.dose_unit_rec_name = dose_unit_rec_name;
	}

	public String getIndication_rec_name() {
		return indication_rec_name;
	}

	public void setIndication_rec_name(String indication_rec_name) {
		this.indication_rec_name = indication_rec_name;
	}

	public String getCommon_dosage_rec_name() {
		return common_dosage_rec_name;
	}

	public void setCommon_dosage_rec_name(String common_dosage_rec_name) {
		this.common_dosage_rec_name = common_dosage_rec_name;
	}

	public String getCourse_completed() {
		return course_completed;
	}

	public void setCourse_completed(String course_completed) {
		this.course_completed = course_completed;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getMedicament_rec_name() {
		return medicament_rec_name;
	}

	public void setMedicament_rec_name(String medicament_rec_name) {
		this.medicament_rec_name = medicament_rec_name;
	}

	public DateGnu getStart_treatment() {
		return start_treatment;
	}

	public void setStart_treatment(DateGnu start_treatment) {
		this.start_treatment = start_treatment;
	}

	public DateGnu getEnd_treatment() {
		return end_treatment;
	}

	public void setEnd_treatment(DateGnu end_treatment) {
		this.end_treatment = end_treatment;
	}



}
