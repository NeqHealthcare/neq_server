package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.Doctor;

public class DoctorGnu implements Doctor {
	private int doctor;
	
	DoctorGnu ()
	{
		
	}
//	
//	@
	//Override
//	public String toString()
//	{
//	   return String.format("doctor: %s", doctor);
//	}

	/**
	 * @return the doctor
	 */
	public int getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor the doctor to set
	 */
	public void setDoctor(int doctor) {
		this.doctor = doctor;
	}

}
