package eu.neq.mais.domain.gnuhealth;

import com.google.gson.Gson;

import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

public class TimestampGnu {

	
	@MapToGnu("write_date")
	Object date;
	
	public void prepareDateFormat() {

		if (!long.class.isInstance(date) && date != null) {
			System.out.println("date: "+date);
			DateGnu temp = new Gson().fromJson(
					String.valueOf(date), DateGnu.class);
			date = temp.getTimeInMillis();
			
			if (date != null) {
				String dateS = String.valueOf(date);
				date = dateS.substring(0, 10)+"."+dateS.substring(10);
			}else{
				date = 0000000000.00000;
			}
			
		}else{
			date = 0000000000.00000;
		}
	}

	public Object getTime() {
		return date;
	}
	
}
