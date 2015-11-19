package springsim;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * With help from: http://examples.javacodegeeks.com/core-java/writeread-csv-files-in-java-example/
 * 
 */
public class CSVLogOutput {

    //Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	static FileWriter fileWriter;
	static List<CSVLogEvent> events;
	static String filename;
	static int participantId;

	//CSV file header
	private static final String FILE_HEADER = "participant_id,timestamp,condition_id, springIndex, hapkit_k,hapkit_x,notes";
	
	public CSVLogOutput(String fileName, int participantId) {

		CSVLogOutput.participantId = participantId;
		events = new ArrayList<CSVLogEvent>(); 
		fileWriter = null;
		filename = fileName;
	}

	
	public void addEvent(CSVLogEvent event){

		Date today = Calendar.getInstance().getTime(); 
		event.setTimestamp(today);
		
		event.setParticipantId(participantId);
		events.add(event);
	}
	
	public void generateLog(){
		
		
		try {
			fileWriter = new FileWriter(filename);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write a new student object list to the CSV file
			for (CSVLogEvent event : events) {
				fileWriter.append(String.valueOf(event.getParticipantId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(event.getTimestamp().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(Integer.toString(event.getConditionId())); // condition id
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(Integer.toString(event.getSpringpairIndex())); // springIndex
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(Float.toString((float) event.getHapkit_k()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(Float.toString((float) event.getHapkit_x()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(event.getNotes()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			
			
			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
			
		}
	}
}
