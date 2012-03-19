package eu.neq.mais.domain.gnuhealth;

import com.google.gson.Gson;

import eu.neq.mais.domain.LabTestRequest;
import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;


public class LabTestRequestGnu extends LabTestRequest {
	
	
	String patient_id, state;
	
	
	@MapToGnu("name.rec_name")
	String rec_name;
	
	@MapToGnu("doctor_id.rec_name")
	String doctor_rec_name;
	
	Object date;	
	
	public void prepareDateFormat() {
		if (!long.class.isInstance(date)) {
			DateGnu end_tr = new Gson().fromJson(String.valueOf(date),
					DateGnu.class);
			
			if (date == null) date = 0;
			else date = end_tr.getTimeInMillis();
		}
	}
	
	public String getPatientId(){
		return patient_id;
	}


	
}
