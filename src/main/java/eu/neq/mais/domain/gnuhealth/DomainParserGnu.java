package eu.neq.mais.domain.gnuhealth;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;

import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

/**
 * 
 * @author seba
 *
 */
public class DomainParserGnu {
	
	public static <T> T fromJson(String jsonString, Class<T> targetClass) {
		
		jsonString = jsonString.substring(jsonString.indexOf("[") + 1,
				jsonString.lastIndexOf("]"));
		
		Field[] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			for(Annotation annotation : annotations) {
				MapToGnu anno = (MapToGnu) annotation;
				jsonString = jsonString.replace(anno.value(), field.getName());
			}
		}
		
		return new Gson().fromJson(jsonString, targetClass);
	}
	
	public static <T> List<T> fromJson(String jsonString, Type listType, Class<T> targetClass) {
//		System.out.println("jsonstring: "+jsonString);
		jsonString = jsonString.substring(jsonString.indexOf("["),
				jsonString.lastIndexOf("]") + 1);
		
		Field[] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			for(Annotation annotation : annotations) {
				MapToGnu anno = (MapToGnu) annotation;
				jsonString = jsonString.replace(anno.value(), field.getName());
			}
		}
		return new Gson().fromJson(jsonString, listType);
	}

}
