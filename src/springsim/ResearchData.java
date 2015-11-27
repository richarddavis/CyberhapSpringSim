package springsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

public class ResearchData {
	
	static int MOUSE_MODE = 0;
	static int HAPKIT_MODE = 1;
	
	int participantId;
	int inputMode;
	
	CSVLogOutput log;
	
	public ResearchData(int participantId, int inputMode){
		initiateLog();
		this.participantId = participantId;
		this.inputMode = inputMode;
	}
	
	private void initiateLog() {
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy-HHmmss");
		Date today = Calendar.getInstance().getTime(); 
		String reportDate = df.format(today);
		
		this.log = new CSVLogOutput("participant_"+participantId+"_log_"+reportDate+".csv", participantId);
	}
	
	public void logEvent(int k, double current_pos, String notes){
		CSVLogEvent e = new CSVLogEvent(inputMode,-1,k, current_pos);
		e.setNotes(notes);
		log.addEvent(e);
	}

	public void generateCSVLog() {
		log.generateLog();
	}

	public int getInputMode() {
		return this.inputMode;
	}

	public boolean isHapkitMode() {
		return inputMode == HAPKIT_MODE;
	}

}
