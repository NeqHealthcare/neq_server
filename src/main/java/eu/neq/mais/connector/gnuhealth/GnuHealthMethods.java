package eu.neq.mais.connector.gnuhealth;

public class GnuHealthMethods {
	
	 public static String getDocumentReadMethod() {
	        return "model.ir.attachment.read";
	    }

	    public static String getDocumentListMethod() {
	        return "model.ir.attachment.search";
	    }


	    public static String getLoginMethod() {
	        return "common.db.login";
	    }

	    public static String getLogoutMethod() {
	        return "common.db.logout";
	    }

	    public static String getPatientSearchMethod() {
	        return "model.gnuhealth.patient.search";
	    }

	    public static String getPatientReadMethod() {
	        return "model.gnuhealth.patient.read";
	    }

	    public static String getPatientWriteMethod(){
	    	return "model.gnuhealth.patient.write";
	    }
	    
	    public static String getVaccinationReadMethod() {
	        return "model.gnuhealth.vaccination.read";
	    }

	    public static String getMedicationSearchMethod() {
	        return "model.gnuhealth.patient.medication.search";
	    }

	    public static String getMedicationReadMethod() {
	        return "model.gnuhealth.patient.medication.read";
	    }

	    public static String getPreferencesMethod() {
	        return "model.res.user.get_preferences";
	    }

	    public static String getDiagnoseReadMethod() {
	        return "model.gnuhealth.patient.disease.read";
	    }

	    public static String getUserSearchMethod() {
	        return "model.res.user.search";
	    }

	    public static String getUserReadMethod() {
	        return "model.res.user.read";
	    }

	    public static String getPhysicianSearchMethod() {
	        return "model.gnuhealth.physician.search";
	    }

	    public static String getLabTestRequestSearchMethod() {
	        return "model.gnuhealth.patient.lab.test.search";
	    }

	    public static String getLabTestRequestReadMethod() {
	        return "model.gnuhealth.patient.lab.test.read";
	    }

	    public static String getLabTestSearchMethod() {
	        return "model.gnuhealth.lab.search";
	    }

	    public static String getLabTestReadMethod() {
	        return "model.gnuhealth.lab.read";
	    }

	    public static String getLabTestCriteriaReadMethod() {
	        return "model.gnuhealth.lab.test.critearea.read";
	    }

	    public static String getLabTestTypeSearchMethod() {
	        return "model.gnuhealth.lab.test_type.search";
	    }

	    public static String getLabTestTypeReadMethod() {
	        return "model.gnuhealth.lab.test_type.read";
	    }

	    public static String getLabTestRequestCreateMethod() {
	        return "model.gnuhealth.patient.lab.test.create";
	    }


	    public static String getProcedureReadMethod(){
	    	return "model.gnuhealth.procedure.read";
	    }
	    
	    public static String getProcedureSearchMethod(){
	    	return "model.gnuhealth.procedure.search";
	    }

	    public static String getPathologyReadMethod(){
	    	return "model.gnuhealth.pathology.read";
	    }
	    
	    public static String getPathologySearchMethod(){
	    	return "model.gnuhealth.pathology.search";
	    }
	    
	    public static String getAppointmentReadMethod(){
	    	return "model.gnuhealth.appointment.read";
	    }
	    
	    public static String getAppointmentSearchMethod(){
	    	return "model.gnuhealth.appointment.search";
	    }

}
