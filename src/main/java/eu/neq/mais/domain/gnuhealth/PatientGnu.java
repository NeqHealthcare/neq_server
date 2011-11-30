package eu.neq.mais.domain.gnuhealth;

import java.util.Date;
import eu.neq.mais.domain.Gender;
import eu.neq.mais.domain.Patient;

public class PatientGnu extends Patient {
	
	
	public PatientGnu(String id, String surname,String forename, Date birthday, Gender gender, String[] diseases){
		this.setSurname(surname);
		this.setForename(forename);
		this.setId(id);
		this.setBirthday(birthday);
		this.setDiseases(diseases);
		this.setGender(gender);
	}

}
