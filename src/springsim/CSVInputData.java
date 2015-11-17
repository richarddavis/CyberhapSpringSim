package springsim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * With help from: http://examples.javacodegeeks.com/core-java/writeread-csv-files-in-java-example/
 */

public class CSVInputData {
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";

    //Student attributes index
	private static final int PARTICIPANT_ID_IDX = 0;
	private static final int CONDITION_IDX = 2;
	private static final int SPRINGINDEX_IDX = 3;
	private static final int XSPRING_IDX = 10;
	private static final int YSPRING_IDX = 11;
	
	public static String fileName;
	
	public CSVInputData(String name) {
		fileName = name;
	}

	public static void readCSVFile(int[][][][] springData) {

			BufferedReader fileReader = null;
	     
	        try {
	        	
	            String line = "";
	            
	            //Create the file reader
	            fileReader = new BufferedReader(new FileReader(fileName));
	            
	            //Read the CSV file header to skip it
	            fileReader.readLine();
	            
	            //Read the file line by line starting from the second line
	            while ((line = fileReader.readLine()) != null) {
	                //Get all tokens available in line
	                String[] tokens = line.split(COMMA_DELIMITER);
	                if (tokens.length > 0) {
	                	//Create a new student object and fill his  data
	                	String pID = tokens[PARTICIPANT_ID_IDX];
	                	String cID = tokens[CONDITION_IDX];
	                	String sIndex = tokens[SPRINGINDEX_IDX];
	                	String xSpring = tokens[XSPRING_IDX];
	                	String ySpring = tokens[YSPRING_IDX];
	                	
	                	int p = Integer.parseInt(pID);
	                	int c = Integer.parseInt(cID);
	                	int s = Integer.parseInt(sIndex);
	                	int x = Integer.parseInt(xSpring);
	                	int y = Integer.parseInt(ySpring);
	                	
	                	springData[p][c][s][0] = x;
	                	springData[p][c][s][1] = y;
	                	
//						Student student = new Student(Long.parseLong(tokens[STUDENT_ID_IDX]), tokens[STUDENT_FNAME_IDX], tokens[STUDENT_LNAME_IDX], tokens[STUDENT_GENDER], Integer.parseInt(tokens[STUDENT_AGE]));
//						students.add(student);
					}
	            }
	            
	            //Print the new student list
//	            for (Student student : students) {
//					System.out.println(student.toString());
//				}
	        } 
	        catch (Exception e) {
	        	System.out.println("Error in CsvFileReader !!!");
	            e.printStackTrace();
	        } finally {
	            try {
	                fileReader.close();
	            } catch (IOException e) {
	            	System.out.println("Error while closing fileReader !!!");
	                e.printStackTrace();
	            }
	        }
		}
	}

