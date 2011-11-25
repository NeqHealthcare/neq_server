package eu.neq.mais.technicalservice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class FileHandler {

	
	public static BufferedReader returnBrforFile(String filePath) throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader(filePath));	
		return br;
	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException{
			
	BufferedReader br = new BufferedReader(
			new FileReader(Settings.BACKEND_CONFIG_FILE));
 
	Type listType = new TypeToken<List<Backend>>(){}.getType();
	List<Backend> backendList = new Gson().fromJson(FileHandler.returnBrforFile(Settings.BACKEND_CONFIG_FILE), listType);
	
	for(Backend b : backendList){
		System.out.println(b.toString());
	}
	
	}
//	Gson gson = new Gson();
//	Collection<Integer> ints = new ArrayList<Integer>();
//	ints.add(1);
//	ints.add(2);//.immutableList(1,2,3,4,5);
//
//	String json = gson.toJson(ints);
//	
//	System.out.println(json);
//
//	Type collectionType = new TypeToken<Collection<Integer>>(){}.getType();
//	Collection<Integer> ints2 = gson.fromJson(json, collectionType);
//
//	for(Integer b : ints2){
//		System.out.println(b.toString());
//	}
//		
//	}

}
