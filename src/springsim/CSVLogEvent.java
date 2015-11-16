package springsim;

import java.util.Date;

public class CSVLogEvent {
	private int participantId;
	private Date timestamp;
	private double hapkit_force;
	private double hapkit_x; 
	private String notes; 
	
	public CSVLogEvent(int _id, Date _timestamp, double _force, double _x, String _notes){
		this.participantId = _id;
		this.timestamp = _timestamp;
		this.hapkit_force = _force;
		this.hapkit_x = _x;
		this.notes = _notes;
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

	public double getHapkit_force() {
		return hapkit_force;
	}

	public void setHapkit_force(double hapkit_force) {
		this.hapkit_force = hapkit_force;
	}

	public double getHapkit_x() {
		return hapkit_x;
	}

	public void setHapkit_x(double hapkit_x) {
		this.hapkit_x = hapkit_x;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String toString() {
        return "Event [id=" + participantId + ", timestamp=" + timestamp
                + ", hapkit_force=" + hapkit_force + ", hapkit_x=" + hapkit_x + ", notes="
                + notes + "]";
    }

}
