package eu.neq.mais.domain;

import java.util.Random;

import com.google.gson.Gson;

public class Patient {
	
	private String name;
	private String birthday;
	private String gender;
	private Integer id;
	
	
	public Patient(String name, String birthday, String gender){
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.id = new Random().nextInt();
	}
	
	public Patient(Integer id,String name, String birthday, String gender){
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.id = id;
	}
	
	
	public Integer getId(){
		return id;
	}
	
	
	
	public String toString(){
		return "id: "+id+" ; name: "+name+" ; gender: "+gender+" ; birthday: "+birthday;
	}
	
	public String toJson (){
		return new Gson().toJson(this);
	}

}
