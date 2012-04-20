package eu.neq.mais.domain.gnuhealth;

public class DiagnoseCreationMessageGnu {

	public DiagnoseCreationMessageGnu(String id) {
		this.id = id;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String toString(){
		return "id: "+id;
	}
}
