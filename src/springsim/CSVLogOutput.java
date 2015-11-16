package springsim;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

	//CSV file header
	private static final String FILE_HEADER = "participant_id,timestamp,hapkit_x,hapkit_force,notes";
	
	public CSVLogOutput(String fileName) {

		events = new ArrayList<CSVLogEvent>(); 
		fileWriter = null;
		filename = fileName;
	}

	
	public void addEvent(CSVLogEvent event){
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
				fileWriter.append((CharSequence) event.getTimestamp());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(Float.toString((float) event.getHapkit_force()));
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
