package springsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

public class ResearchData {
	
	static int CONDITION_GRAPHICS_HAPTICS = 1;
	static int CONDITION_GRAPHICS_ONLY = 0;
	
	int condition; // 0 or 1
	int participantId; // 1-15
	
	CSVLogOutput log;
	
	public ResearchData(int participantId){
		
		initiateLog();
		this.participantId = participantId;
		
		//TODO: make this dynamic/random
		this.condition = 0;
		
		CSVLogEvent e = new CSVLogEvent(condition, -1, -1, -1);
		e.setNotes("Intitial Condition: "+condition+" (1=haptics+graphics 0=graphics)");
		log.addEvent(e);
	}
	
	private void initiateLog() {
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy-HHmmss");
		Date today = Calendar.getInstance().getTime(); 
		String reportDate = df.format(today);
		
		this.log = new CSVLogOutput("participant_"+participantId+"_log_"+reportDate+".csv", participantId);
	}
	
	public void logEvent(int k, double current_pos, String notes){
		CSVLogEvent e = new CSVLogEvent(condition,-1,k, current_pos);
		e.setNotes(notes);
		log.addEvent(e);
	}

	public void generateCSVLog() {
		log.generateLog();
	}

	public int getCondition() {
		return condition;
	}

}
