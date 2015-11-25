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
	
	int springIndex; // 1-15
	int condition; // 0 or 1
	int participantId; // 1-15
	
	int completedConditions;
	
	CSVLogOutput log;
	
	int[][][][] springData; //[participant][condition][springIndex][left/right spring]
	int[] initialCondition;
	
	public ResearchData(int participantId){
		
		initiateLog();
		parseSpringData();
		
		this.participantId = participantId;
		this.condition = initialCondition[participantId];
		this.springIndex = 0;
		this.completedConditions = 0;
		
		CSVLogEvent e = new CSVLogEvent(initialCondition[participantId], -1, -1, -1, springData[participantId][condition][springIndex]);
		e.setNotes("Intitial Condition: "+initialCondition+" (1=haptics+graphics 0=graphics)");
		log.addEvent(e);
	}
	
	private void parseSpringData() {
		springData = new int[19][2][16][2]; 
		initialCondition = new int[19];
		CSVInputData springDataParser = new CSVInputData("spring_pairs.csv");
		springDataParser.readCSVFile(springData, initialCondition);
	}
	
	public int getCurrentXSpring(){
		return springData[participantId][condition][springIndex][0];
	}
	
	public int getCurrentYSpring(){
		return springData[participantId][condition][springIndex][1];
	}
	
	public void nextSpringPair(){
		if(springIndex < 15){
			springIndex++;
		}else{
			switchCondition();
		}
	}

	private void switchCondition() {
		if(completedConditions == 0){
			JOptionPane.showMessageDialog(null, "Ready to start Part 2?");
			logEvent(-1,-1,"About to switch conditions");
		}else{
			JOptionPane.showMessageDialog(null, "Thank You! The study is now complete.");
			logEvent(-1,-1,"Study is Over.");
		}
		condition = 1 - condition;
		springIndex = 0;
		logEvent(-1,-1,"Conditions have been switched");
		completedConditions++;
	}

	private void initiateLog() {
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy-HHmmss");
		Date today = Calendar.getInstance().getTime(); 
		String reportDate = df.format(today);
		
		this.log = new CSVLogOutput("participant_"+participantId+"_log_"+reportDate+".csv", participantId);
	}
	
	public void logEvent(int k, double current_pos, String notes){
		CSVLogEvent e = new CSVLogEvent(condition,springIndex,k, current_pos, springData[participantId][condition][springIndex]);
		e.setNotes(notes);
		log.addEvent(e);
	}

	public void generateCSVLog() {
		log.generateLog();
	}

	public int getSpringIndex() {
		return springIndex;
	}

	public int getCondition() {
		return condition;
	}

}
