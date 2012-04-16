package eu.neq.mais.technicalservice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public abstract class FileHandler {

	private static Map<String,Backend> backendMap;
	
	private static Map<Integer,NewsFeed> newsFeeds;
	
	public static Map<String,Backend> getBackendMap () throws IOException{
		
		if(backendMap == null){
			reloadBackendMap();
		}
		return backendMap;
	}
	
	public static Map<Integer,NewsFeed> getNewsFeeds() throws IOException{
		if(newsFeeds == null){
			reloadNewsFeeds();
		}
		return newsFeeds;
	}
	
	private static void reloadNewsFeeds() throws IOException {
		
		Type listType = new TypeToken<List<NewsFeed>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader(Settings.NEWS_FEEDS_FILE));	
		List<NewsFeed> list = new Gson().fromJson(br, listType);
		newsFeeds = new HashMap<Integer,NewsFeed>();
		for(NewsFeed feed : list){
			newsFeeds.put(feed.getId(),feed);
		}	
		br.close();
	}

	public static void reloadBackendMap() throws IOException{
		backendMap = new HashMap<String,Backend>();
		
		Type listType = new TypeToken<List<Backend>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader(Settings.BACKEND_CONFIG_FILE));	
		List<Backend> backendList = new Gson().fromJson(br, listType);
		br.close();
		for(Backend backend : backendList){
			backendMap.put(backend.getSid(), backend);
		}	
	}
	
	public static java.util.logging.FileHandler getLogFileHandler(String logFile) {
		try {
			return new java.util.logging.FileHandler(logFile);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}
