package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.Vaccination;

/**
 * 
 * 
 * @author Jan Gansen
 *
 */
public class VaccinationGnu extends Vaccination {

	private String dose,vaccine_rec_name,observations,vaccine_lot,institution_rec_name;
	
	public String getVaccine_lot() {
		return vaccine_lot;
	}

	public void setVaccine_lot(String vaccine_lot) {
		this.vaccine_lot = vaccine_lot;
	}

	public String getInstitution() {
		return institution_rec_name;
	}

	public void setInstitution(String institution) {
		this.institution_rec_name = institution;
	}

	private DateGnu date, next_dose_date;
	

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getVaccine_rec_name() {
		return vaccine_rec_name;
	}

	public void setVaccine_rec_name(String vaccine_rec_name) {
		this.vaccine_rec_name = vaccine_rec_name;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public DateGnu getDate() {
		return date;
	}

	public void setDate(DateGnu date) {
		this.date = date;
	}

	public DateGnu getNext_dose_date() {
		return next_dose_date;
	}

	public void setNext_dose_date(DateGnu next_dose_date) {
		this.next_dose_date = next_dose_date;
	}

}
