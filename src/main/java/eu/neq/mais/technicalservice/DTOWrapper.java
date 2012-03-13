package eu.neq.mais.technicalservice;

import java.util.List;

import com.google.gson.Gson;

public class DTOWrapper {
	
	public String wrap(List<?> list) {
		CustomJsonDto tmp = new CustomJsonDto(list.toArray());
		return new Gson().toJson(tmp);
	}
	public String wrap(List<?> list, String success) {
		CustomJsonDto tmp = new CustomJsonDto(list.toArray(), success);
		return new Gson().toJson(tmp);
	}
	
	public String wrap(Object o) {
		CustomJsonDto tmp = new CustomJsonDto(o);
		return new Gson().toJson(tmp);
	}
	
	public String wrap(Object o, String success) {
		CustomJsonDto tmp = new CustomJsonDto(o, success);
		return new Gson().toJson(tmp);
	}
	
	public String wrap(String s) {
		CustomJsonString tmp = new CustomJsonString(s);
		return new Gson().toJson(tmp);
	}
	
	public String wrap(String s, String success) {
		CustomJsonString tmp = new CustomJsonString(s, success);
		return new Gson().toJson(tmp);
	}
	
	public String wrapError(String errorMessage) {
		CustomJsonError tmp = new CustomJsonError(errorMessage);
		return new Gson().toJson(tmp);
	}
	

	public class CustomJsonString {
		String success = "true";
		String[] data;
		
		public CustomJsonString(String data) {
			this.data = new String[]{data};
		}
		public CustomJsonString(String data, String success) {
			this.data = new String[]{data};
			this.success = success;
		}
		
	}
	
	public class CustomJsonDto {
		
		String success = "true";
		Object[] data;
		
		public CustomJsonDto(Object[] data) {
			this.data = data;
		}
		public CustomJsonDto(Object[] data, String success) {
			this.data = data;
			this.success = success;
		}
		
		public CustomJsonDto(Object data) {
			this.data = new Object[]{data};
		}
		
		public CustomJsonDto(Object data, String success) {
			this.data = new Object[]{data};
			this.success = success;
		}
		
		public CustomJsonDto(String data) {
			this.data = new String[]{data};
		}
		
		public CustomJsonDto(String data, String success) {
			this.data = new String[]{data};
			this.success = success;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object[] data) {
			this.data = data;
		}
		
	}
	
	public class CustomJsonError {
		
		String success = "false";
		String error;
		Object[] data;
		
		public CustomJsonError(String errorMessage) {
			this.error = errorMessage;
			this.data = new Object[]{};
		}
		
	}


}
