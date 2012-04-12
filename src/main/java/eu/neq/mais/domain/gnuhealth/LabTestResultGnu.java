package eu.neq.mais.domain.gnuhealth;

import com.google.gson.Gson;

import eu.neq.mais.domain.LabTestResult;
import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

public class LabTestResultGnu extends LabTestResult {
String id, test, name, result, diagnosis, patient, request_id;
	
	@MapToGnu("test.rec_name")
	String test_rec_name;
	
	@MapToGnu("pathologist.rec_name")
	String pathologist_rec_name;
	
	@MapToGnu("requestor.rec_name")
	String requestor_rec_name;
	
	String requestor;
	
	@MapToGnu("patient.rec_name")
	String patient_rec_name;
	
	Object date_requested, date_analysis;
	
	int[] critearea;
	
	Object[] criteria; 
	
	
	public void prepareDateFormat() {
		if (!long.class.isInstance(date_requested)
				&& !long.class.isInstance(date_analysis)) {
			DateGnu start_tr = new Gson().fromJson(
					String.valueOf(date_requested), DateGnu.class);
			DateGnu end_tr = new Gson().fromJson(String.valueOf(date_analysis),
					DateGnu.class);
			
			if (date_requested == null) date_requested = 0;
			else date_requested = start_tr.getTimeInMillis();
			
			if (date_analysis == null) date_analysis = 0;
			else date_analysis = end_tr.getTimeInMillis();
		}
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTest_rec_name() {
		return test_rec_name;
	}

	public void setTest_rec_name(String test_rec_name) {
		this.test_rec_name = test_rec_name;
	}



	public int[] getCritearea() {
		return critearea;
	}

	public void setCritearea(int[] critearea) {
		this.critearea = critearea;
	}

	public Object[] getCriteria() {
		return criteria;
	}

	public void setCriteria(Object[] criteria) {
		this.criteria = criteria;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getPathologist_rec_name() {
		return pathologist_rec_name;
	}

	public void setPathologist_rec_name(String pathologist_rec_name) {
		this.pathologist_rec_name = pathologist_rec_name;
	}

	public String getRequestor_rec_name() {
		return requestor_rec_name;
	}

	public void setRequestor_rec_name(String requestor_rec_name) {
		this.requestor_rec_name = requestor_rec_name;
	}

	public String getPatient_rec_name() {
		return patient_rec_name;
	}

	public void setPatient_rec_name(String patient_rec_name) {
		this.patient_rec_name = patient_rec_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
}
