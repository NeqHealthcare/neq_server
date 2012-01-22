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
	private List<String> diseases;
	private List<DiagnoseGnu> diagnoseList;
	private String age;
	private String sex;
	private String id;
	private String primary_care_doctor_rec_name;
	
	public String getRec_name() {
		return rec_name;
	}

	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}

	public List<String> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<String> diseases) {
		this.diseases = diseases;
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

	public List<DiagnoseGnu> getDiagnoseList() {
		return diagnoseList;
	}

	public void setDiagnoseList(List<DiagnoseGnu> diagnoseList) {
		this.diagnoseList = diagnoseList;
	}

	public String getPrimary_care_doctor_rec_name() {
		return primary_care_doctor_rec_name;
	}

	public void setPrimary_care_doctor_rec_name(
			String primary_care_doctor_rec_name) {
		this.primary_care_doctor_rec_name = primary_care_doctor_rec_name;
	};
}
