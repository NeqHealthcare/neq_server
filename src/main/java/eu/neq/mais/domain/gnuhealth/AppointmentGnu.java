package eu.neq.mais.domain.gnuhealth;

import com.google.gson.Gson;

import eu.neq.mais.domain.Appointment;
import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

public class AppointmentGnu extends Appointment {

	String doctor, appointment_type,urgency,comments,patient;
	
	@MapToGnu("speciality.rec_name")
	String speciality_rec_name;
	
	@MapToGnu("patient.rec_name")
	String patient_rec_name;
	
	@MapToGnu("consultations.description")
	String consultations_description;
		
	Object appointment_date;

	public Object getDate (){
		return appointment_date;
	}
	
	public void prepareDateFormat() {
		if (!long.class.isInstance(appointment_date)) {
			TimeGnu end_tr = new Gson().fromJson(String.valueOf(appointment_date),
					TimeGnu.class);
			
			if (appointment_date == null) appointment_date = 0;
			else appointment_date = end_tr.getTimeInMillis();
		}
	}
	
  @Override
  public int compareTo(Appointment o) {
	  AppointmentGnu g = (AppointmentGnu) o;
      return ((Long)appointment_date).compareTo((Long)g.getAppointmentDate());
  }
	
	
	private Object getAppointmentDate() {
	
	return appointment_date;
}

	public String toString(){
		return "date: "+appointment_date+" patient_rec_name: "+patient_rec_name+" consultations_desc: "+consultations_description+" doctor: "+doctor;
	}
	
	public Integer getDoctorId(){
		return Integer.parseInt(doctor);
	}
	
}
