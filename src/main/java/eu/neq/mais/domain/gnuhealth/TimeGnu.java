package eu.neq.mais.domain.gnuhealth;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeGnu {
	

	private int hour, month, second, year, day, minute;
	
	private String __class__ = "datetime";
	
	public TimeGnu(long time){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(time);
		this.hour = cal.get(Calendar.HOUR_OF_DAY);
		this.month = cal.get(Calendar.MONTH);
		this.second = cal.get(Calendar.SECOND);
		this.year = cal.get(Calendar.YEAR);
		this.day = cal.get(Calendar.DAY_OF_MONTH);
		this.minute = cal.get(Calendar.MINUTE);
	}

}
