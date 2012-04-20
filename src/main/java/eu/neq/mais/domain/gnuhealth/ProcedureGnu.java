package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.Procedure;

public class ProcedureGnu extends Procedure {

	String description,name;
	
	
	public String toString(){
		return "description: "+description+" name: "+name;
	}
}
