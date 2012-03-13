package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

public class LabTestCriteriaGnu {

	String name, result_text, remarks, upper_limit, lower_limit, result, excluded, units, warning;
	
	@MapToGnu("units.rec_name")
	String units_rec_name;
}
