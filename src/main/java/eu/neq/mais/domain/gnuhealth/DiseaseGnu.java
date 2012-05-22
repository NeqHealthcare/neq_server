package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.Disease;
import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

public class DiseaseGnu extends Disease {

	String id,code, name;
	
	@MapToGnu("category.rec_name")
	String category_rec_name;
	
	
	public String toString(){
		return "id: "+id+"code: "+code+" name: "+name+" category.rec_name: "+category_rec_name;
	}
}
