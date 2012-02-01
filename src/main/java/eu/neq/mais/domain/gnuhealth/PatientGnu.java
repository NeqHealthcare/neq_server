package eu.neq.mais.domain.gnuhealth;

import java.util.List;

import eu.neq.mais.domain.Patient;

/**
 * 
 * @author Jan Gansen
 *
 */
public class PatientGnu extends Patient {
	
	private String rec_name;
	//contains diagnoses id, but has to be named diseases because of automatic json to instance process
	private List<String> diseases;
	private DiagnoseGnu latestDiagnose;
//	private List<DiagnoseGnu> diagnoseList;
	private String age;
	private String sex;
	private String id;
	private String primary_care_doctor_rec_name;
	private String primary_care_doctor_name;
	
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public PatientGnu(){

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public List<DiagnoseGnu> getDiagnoseList() {
//		return diagnoseList;
//	}
//
//	public void setDiagnoseList(List<DiagnoseGnu> diagnoseList) {
//		this.diagnoseList = diagnoseList;
//	}

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

	public DiagnoseGnu getLatestDiagnose() {
		return latestDiagnose;
	}

	public void setLatestDiagnose(DiagnoseGnu latestDiagnose) {
		this.latestDiagnose = latestDiagnose;
	};
}
