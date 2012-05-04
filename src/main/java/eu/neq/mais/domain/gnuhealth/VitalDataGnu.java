package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.VitalData;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 29.04.12
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class VitalDataGnu implements VitalData {

    Integer id;
    String patient_id, state;
    Calendar date;
    float bmi, temprature, blood_pressure, fluid_balace;

    public VitalDataGnu(String patient_id, double bmi, double temprature, double blood_pressure, double fluid_balace, long date) {
        this.bmi = (float) bmi;
        this.temprature = (float) temprature;
        this.blood_pressure = (float) blood_pressure;
        this.fluid_balace = (float) fluid_balace;
        this.patient_id = patient_id;
        //this.date = new Date();

        this.date = Calendar.getInstance();

        this.date.setTimeInMillis(date);
        //this.date = new Date(temp_date.);
        //this.date.setDate(temp_date.getTime().getDay());
        //this.date.setMonth(temp_date.getTime().getMonth());
        //this.date.setYear(temp_date.getTime().getYear());

    }

    public VitalDataGnu(String patient_id) {
        this.patient_id = patient_id;
        this.generateVitalData();
    }

    public VitalData getVitalDataForPatient() {
        return this;
    }

    public void generateVitalData() {
        this.bmi = 18 + (float) Math.random() * 15;
        this.temprature = 36 + (float) Math.random() * 5;
        this.blood_pressure = 90 + (float) Math.random() * 30;
        this.fluid_balace = 5 - (float) Math.random() * 10;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getTemprature() {
        return temprature;
    }

    public void setTemprature(float temprature) {
        this.temprature = temprature;
    }

    public float getBlood_pressure() {
        return blood_pressure;
    }

    public void setBlood_pressure(float blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public float getFluid_balace() {
        return fluid_balace;
    }

    public void setFluid_balace(float fluid_balace) {
        this.fluid_balace = fluid_balace;
    }
}
