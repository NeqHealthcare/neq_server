package eu.neq.mais.technicalservice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public abstract class FileHandler {

	
	private static BufferedReader returnBrforFile(String filePath) throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader(filePath));	
		return br;
	}
	
	public static Map<String,Backend> getBackendMap () throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		
		Map<String,Backend> backendMap = new HashMap<String,Backend>();
		
		Type listType = new TypeToken<List<Backend>>(){}.getType();
		List<Backend> backendList = new Gson().fromJson(FileHandler.returnBrforFile(Settings.BACKEND_CONFIG_FILE), listType);

		for(Backend backend : backendList){
			backendMap.put(backend.getUri()+" "+backend.getSid(), backend);
		}
		
		return backendMap;
	}

}
