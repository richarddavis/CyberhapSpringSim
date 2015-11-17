package springsim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controlP5.ControlP5;
//import jssc.SerialPort;
//import jssc.SerialPortEvent;
//import jssc.SerialPortEventListener;
//import jssc.SerialPortException;
import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.Serial;
import shiffman.box2d.Box2DProcessing;

public class Main extends PApplet {

	//Container properties, dynamic generated from overall width, height
	int width = 1000;
	int height = 600;
	int spacing = (int) (width*0.02);
	
	//component widths
	int leftColWidth = (int) (width*0.22);
	int centerColWidth = (int) (width*0.45);
	int rightColWidth = (int) (width*0.22);
	
	//designPalette coordinates
	int dPX = leftColWidth+(2*spacing);
	int dPY = spacing;
	int dPW = centerColWidth;
	int dPH = height-(spacing*2);
	
	//forceFeedbackOption coordinates
	int fFOX = spacing;
	int fFOY = spacing;
	int fFOW = leftColWidth;
	int fFOH = 80;
	
	//participantSelection coordinates
	int pSX = spacing;
	int pSY = spacing;
	int pSW = leftColWidth;
	int pSH = 80;
	
	//forceDisplayOutput coord
	int fDOX = (spacing*3)+leftColWidth+centerColWidth;
	int fDOY = spacing;
	int fDOW = rightColWidth;
	int fDOH = 500;
	
	//hapkitFeedbackPanel coord
	int hfx = spacing;
	int hfy = (spacing*2)+fFOH;
	int hfw = leftColWidth;
	int hfh = 160;
	
	//physicsPlayground coord
	int pPX = spacing;
	int pPY = (spacing*3)+fFOH+hfh;
	int pPW = leftColWidth;
	int pPH = 100;
	
	//expSettings coord
	int eSX = spacing;
	int eSY = (spacing*4)+fFOH+hfh+pPH;
	int eSW = leftColWidth;
	int eSH = 160;
	
	//Components
	Hapkit hapkit;
	Canvas designPalette;
	ForceFeedbackOption forceFeedbackOption;
	HapkitFeedback hapkitFeedbackPanel;
	ExperimentSettings expSettings;
	ForceDisplayOutput forceDisplayOutput;
	PhysicsPlayground physicsPlayground;
	ParticipantSelection participantSelection;
	
	List<Component> components = new ArrayList<Component>();
	
	ControlP5 cp5;
	
	int participantId;
	int[ ][ ][ ][ ] springData; //[participantId][condition][springIndex][springx/springy]
	CSVLogOutput log;
	CSVInputData springDataParser;
	
	public void setup() {
		size(width, height);
		background(255);
		
		log = new CSVLogOutput("participant_log.csv");
		
		springData = new int[19][2][16][2];
		springDataParser = new CSVInputData("spring_pairs.csv");
		springDataParser.readCSVFile(springData);
		
		cp5 = new ControlP5(this);
		//TODO consider changing colors
		//cp5.setColorForeground(50);
		//cp5.setColorBackground(150);
		//cp5.setColorActive(200);
		
		  // change the default font to Verdana
		  PFont p = createFont("Verdana",9); 
		  cp5.setControlFont(p);
		  
		  // change the original colors
		  cp5.setColorForeground(0xffaa0000);
		  cp5.setColorBackground(0xff660000);
		  cp5.setColorLabel(0xffdddddd);
		  cp5.setColorValue(0xffff88ff);
		  cp5.setColorActive(0xffff0000);
		
		participantSelection = new ParticipantSelection(this, cp5, pSX, pSY, pSW, pSH, participantId);
		hapkit = new Hapkit(this, Serial.list(), 7);
		designPalette = new Canvas(this, dPX, dPY, dPW, dPH, hapkit, springData[participantId]);
		
		//forceFeedbackOption = new ForceFeedbackOption(this, cp5, fFOX, fFOY, fFOW, fFOH,  designPalette);
		//expSettings = new ExperimentSettings(this, cp5, eSX, eSY, eSW, eSH);
		//forceDisplayOutput = new ForceDisplayOutput(this, cp5, fDOX, fDOY, fDOW, fDOH);
		//physicsPlayground = new PhysicsPlayground(this, cp5, designPalette, pPX, pPY, pPW, pPH);
		//hapkitFeedbackPanel = new HapkitFeedback(this, cp5, hfx, hfy, hfw, hfh, hapkit, designPalette.getSpringCollection());
		
		components.add(designPalette);
		components.add(participantSelection);
//		components.add(forceFeedbackOption);
//		components.add(expSettings);
//		components.add(forceDisplayOutput);
//		components.add(physicsPlayground);
//		components.add(hapkitFeedbackPanel);
		
	}

	public void draw() {
		background(255);
		stroke(255);
		
		for(int i=0; i<components.size(); i++){
			Component c = components.get(i);
			c.draw();
			c.step();	
		}
	}	

	public void mousePressed() {
		designPalette.mousePressed();
		
	}
	
	public void mouseReleased() {
		designPalette.mouseReleased();
	}

	public void serialEvent(Serial p){
		hapkit.serialEvent(p);
    }
	
	/**
	 * Generate CSV Log when program closes
	 * 
	 */
	public void stop() {
		log.generateLog();
	} 
	
}


