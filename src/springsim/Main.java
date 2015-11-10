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
	
	List<Component> components = new ArrayList<Component>();
	
	ControlP5 cp5;
	
	public void setup() {
		size(width, height);
		background(255);
		
		cp5 = new ControlP5(this);
		//TODO consider changing colors
		//cp5.setColorForeground(50);
		//cp5.setColorBackground(150);
		//cp5.setColorActive(200);
		
		hapkit = new Hapkit(this, Serial.list(), 2);
		designPalette = new Canvas(this, dPX, dPY, dPW, dPH, hapkit);
		forceFeedbackOption = new ForceFeedbackOption(this, cp5, fFOX, fFOY, fFOW, fFOH,  designPalette);
		expSettings = new ExperimentSettings(this, cp5, eSX, eSY, eSW, eSH);
		forceDisplayOutput = new ForceDisplayOutput(this, cp5, fDOX, fDOY, fDOW, fDOH);
		physicsPlayground = new PhysicsPlayground(this, cp5, designPalette, pPX, pPY, pPW, pPH);
		hapkitFeedbackPanel = new HapkitFeedback(this, cp5, hfx, hfy, hfw, hfh, hapkit, designPalette.getSpringCollection());
		
		components.add(designPalette);
		components.add(forceFeedbackOption);
		components.add(expSettings);
		components.add(forceDisplayOutput);
		components.add(physicsPlayground);
		components.add(hapkitFeedbackPanel);

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
	
}
