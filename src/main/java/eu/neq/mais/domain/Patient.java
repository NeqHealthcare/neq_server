package eu.neq.mais.domain;

import java.util.Date;

import com.google.gson.Gson;

/**
 * 
 * @author Jan Gansen
 *
 */
public abstract class Patient {
	
	public String toJson (){
		return new Gson().toJson(this);
	}
	
	
}
