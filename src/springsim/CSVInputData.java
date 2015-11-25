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
	private static final int CONDITION_NAME = 2;
	private static final int CONDITION_IDX = 4;
	private static final int SPRINGINDEX_IDX = 5;
	private static final int XSPRING_IDX = 11;
	private static final int YSPRING_IDX = 12;
	
	public static String fileName;
	
	public CSVInputData(String name) {
		fileName = name;
	}

	public static void readCSVFile(int[][][][] springData, int[] initialCondition) {

			BufferedReader fileReader = null;
	     
	        try {
	        	
	            String line = "";
	            
	            //Create the file reader
	            fileReader = new BufferedReader(new FileReader("bin/"+fileName));
	            
	            //Read the CSV file header to skip it
	            fileReader.readLine();
	            
	            //Read the file line by line starting from the second line
	            while ((line = fileReader.readLine()) != null) {
	                //Get all tokens available in line
	                String[] tokens = line.split(COMMA_DELIMITER);
	                if (tokens.length > 0) {
	                	//Create a new student object and fill his  data
	                	String pID = tokens[PARTICIPANT_ID_IDX];
	                	String initCondition = tokens[CONDITION_NAME];
	                	String conditionID = tokens[CONDITION_IDX];
	                	
	                	int p = Integer.parseInt(pID);
	                	int c = Integer.parseInt(conditionID);
	                	
	                	if(initCondition.equals("COUNTERBALANCE")){
	                		initialCondition[p] = c;
	                	}else{
		                	String sIndex = tokens[SPRINGINDEX_IDX];
		                	String xSpring = tokens[XSPRING_IDX];
		                	String ySpring = tokens[YSPRING_IDX];
		                	
		                	int s = Integer.parseInt(sIndex);
		                	int x = Integer.parseInt(xSpring);
		                	int y = Integer.parseInt(ySpring);
		                	
		                	springData[p][c][s][0] = x;
		                	springData[p][c][s][1] = y;
	                	}
					}
	            }
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

