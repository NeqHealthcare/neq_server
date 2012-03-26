package eu.neq.mais.domain.gnuhealth;

public class LabTestRequestCreationMessage {

	public LabTestRequestCreationMessage(String id) {
		this.id = id;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
