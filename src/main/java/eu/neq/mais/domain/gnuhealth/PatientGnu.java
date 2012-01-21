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

	public String toString(){
		String returnString = "id: "+id+" ; rec_name: "+rec_name+" ; sex: "+sex+" ; age: "+age+" ; diseases: ";
		if(diseases.size()>1){
			for(String disease : diseases){
				returnString+= disease+" ";
			}
		}
		return returnString;
	}

	public List<DiagnoseGnu> getDiagnoseList() {
		return diagnoseList;
	}

	public void setDiagnoseList(List<DiagnoseGnu> diagnoseList) {
		this.diagnoseList = diagnoseList;
	};
}
