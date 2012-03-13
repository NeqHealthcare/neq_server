package eu.neq.mais.domain.gnuhealth;

import com.google.gson.Gson;

import eu.neq.mais.domain.Vaccination;
import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

/**
 * 
 * 
 * @author Jan Gansen
 *
 */
public class VaccinationGnu extends Vaccination {

	private String dose,observations,vaccine_lot;
	
	private Object date, next_dose_date;
	
	@MapToGnu("vaccine.rec_name")
	private String vaccine_rec_name;
	
	@MapToGnu("institution.rec_name")
	private String institution_rec_name;
	
	public String getVaccine_lot() {
		return vaccine_lot;
	}

	public void prepareDateFormat() {

		if (!long.class.isInstance(date)
				&& !long.class.isInstance(next_dose_date)) {
			DateGnu start_tr = new Gson().fromJson(
					String.valueOf(date), DateGnu.class);
			DateGnu end_tr = new Gson().fromJson(String.valueOf(next_dose_date),
					DateGnu.class);
			date = start_tr.getTimeInMillis();
			next_dose_date = end_tr.getTimeInMillis();
			
			if (date == null) date = 0;
			else date = start_tr.getTimeInMillis();
			
			if (next_dose_date == null) next_dose_date = 0;
			else next_dose_date = end_tr.getTimeInMillis();
		}
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

	public Object getDate() {
		return date;
	}

	public void setDate(DateGnu date) {
		this.date = date;
	}

	public Object getNext_dose_date() {
		return next_dose_date;
	}

	public void setNext_dose_date(DateGnu next_dose_date) {
		this.next_dose_date = next_dose_date;
	}

}
