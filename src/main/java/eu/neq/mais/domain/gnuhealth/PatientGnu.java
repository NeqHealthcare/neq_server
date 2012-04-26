package eu.neq.mais.domain.gnuhealth;

import java.util.List;

import com.google.gson.Gson;

import eu.neq.mais.domain.Patient;
import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

/**
 * 
 * @author Jan Gansen
 * 
 */
public class PatientGnu extends Patient {

	private String rec_name;
	// contains diagnoses id, but has to be named diseases because of automatic
	// json to instance process
	private List<String> diseases;
	private String latestDiagnoseRecName;
	// private List<DiagnoseGnu> diagnoseList;
	private Object dob;

	private String sex;
	private String id;

	@MapToGnu("primary_care_doctor.rec_name")
	private String primary_care_doctor_rec_name;

	@MapToGnu("primary_care_doctor.name")
	private String primary_care_doctor_name;

	public void prepareDateFormat() {

		if (!long.class.isInstance(dob)) {
			DateGnu temp = new Gson().fromJson(
					String.valueOf(dob), DateGnu.class);
			dob = temp.getTimeInMillis();
			
			if (dob == null) {
				dob = 0;
			}
			
		}
	}

	public String getRec_name() {
		return rec_name;
	}

	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}

	public List<String> getDiagnoseIds() {
		return diseases;
	}

	public void setDiagnoseIds(List<String> diagnoseIds) {
		this.diseases = diagnoseIds;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public PatientGnu() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// public List<DiagnoseGnu> getDiagnoseList() {
	// return diagnoseList;
	// }
	//
	// public void setDiagnoseList(List<DiagnoseGnu> diagnoseList) {
	// this.diagnoseList = diagnoseList;
	// }

	public String getPrimary_care_doctor_rec_name() {
		return primary_care_doctor_rec_name;
	}

	public void setPrimary_care_doctor_rec_name(
			String primary_care_doctor_rec_name) {
		this.primary_care_doctor_rec_name = primary_care_doctor_rec_name;
	}

	public String getPrimary_care_doctor_id() {
		return primary_care_doctor_name;
	}

	public void setPrimary_care_doctor_id(String primary_care_doctor_id) {
		this.primary_care_doctor_name = primary_care_doctor_id;
	}

	public String getLatestDiagnoseRecName() {
		return latestDiagnoseRecName;
	}

	public void setLatestDiagnoseRecName(String latestDiagnoseRecName) {
		this.latestDiagnoseRecName = latestDiagnoseRecName;
	}

	public String toString() {
		return "rec_name: " + rec_name + ", diseases: "
				+ ((diseases == null) ? "none" : diseases.size())
				+ ", latestDiagnoseRecName: " + latestDiagnoseRecName
				+ ", dob: " + dob + ", sex: " + sex + ", id: " + id
				+ ", pcdrecname: " + primary_care_doctor_rec_name;
	}

}
