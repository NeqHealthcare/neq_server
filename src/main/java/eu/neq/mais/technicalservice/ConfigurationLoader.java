package eu.neq.mais.technicalservice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

class ConfigurationLoader {

	
	public static void main(String[] args) throws FileNotFoundException{
		
	
	BufferedReader br = new BufferedReader(
			new FileReader(Settings.BACKEND_CONFIG_FILE));
 
		//convert the json string back to object
		Backend backend = new Gson().fromJson(br, Backend.class);
		
		System.out.println(backend.toString());
		
	}

}
