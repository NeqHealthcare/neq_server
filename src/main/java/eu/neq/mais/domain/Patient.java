package eu.neq.mais.domain;

import java.util.Date;

import com.google.gson.Gson;

public abstract class Patient {

	private String id;

	private String surname;
	private String forename;
	private Date birthday;
	private Gender gender;
	private String[] diseases;
	
	public String toString(){
		return "id: "+id+" ; name: "+forename+" "+surname+" ; gender: "+getGender()+" ; birthday: "+birthday;
	}
	
	public String toJson (){
		return new Gson().toJson(this);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String[] getDiseases() {
		return diseases;
	}

	public void setDiseases(String[] diseases) {
		this.diseases = diseases;
	}
	
}
