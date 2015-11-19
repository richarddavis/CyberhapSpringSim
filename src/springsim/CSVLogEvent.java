package springsim;

import java.util.Date;

public class CSVLogEvent {
	private int participantId;
	private Date timestamp;
	private double hapkit_k;
	private double hapkit_x; 
	private int conditionId;
	private int springpairIndex;
	private String notes; 
	
	public CSVLogEvent(int condition, int springIndex, double _k, double _x){
		this.hapkit_k = _k;
		this.hapkit_x = _x;
		this.conditionId = condition;
		this.springpairIndex = springIndex;
	}

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participant_id) {
		this.participantId = participant_id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getHapkit_x() {
		return hapkit_x;
	}

	public void setHapkit_x(double hapkit_x) {
		this.hapkit_x = hapkit_x;
	}
	
	public double getHapkit_k() {
		return hapkit_k;
	}

	public void setHapkit_k(double hapkit_k) {
		this.hapkit_k = hapkit_k;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String toString() {
        return "Event [id=" + participantId + ", timestamp=" + timestamp
                + ", hapkit_k=" + hapkit_k + ", hapkit_x=" + hapkit_x + ", notes="
                + notes + "]";
    }

	public int getSpringpairIndex() {
		return springpairIndex;
	}

	public void setSpringpairIndex(int springpairIndex) {
		this.springpairIndex = springpairIndex;
	}

	public int getConditionId() {
		return conditionId;
	}

	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}

}
