package springsim;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.Serial;

public class Main extends PApplet {
	
	static int MOUSE_MODE = 0;
	static int HAPKIT_MODE = 1;
	int inputMode;

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
	int fFOH = 90;
	
	//forceDisplayOutput coord
	int fDOX = (spacing*3)+leftColWidth+centerColWidth;
	int fDOY = spacing;
	int fDOW = rightColWidth;
	int fDOH = 500;
	
	//participantSelection coordinates
	int pSX = (spacing*3)+leftColWidth+centerColWidth;
	int pSY = fDOH+spacing;
	int pSW = rightColWidth;
	int pSH = 80;
	
	//hapkitFeedbackPanel coord
	int hfx = spacing;
	int hfy = (spacing*2)+fFOH;
	int hfw = leftColWidth;
	int hfh = 140;
	
	//physicsPlayground coord
	int pPX = spacing;
	int pPY = (spacing*3)+fFOH+hfh;
	int pPW = leftColWidth;
	int pPH = 130;
	
	//expSettings coord
	int eSX = spacing;
	int eSY = (spacing*4)+fFOH+hfh+pPH;
	int eSW = leftColWidth;
	int eSH = 140;
	
	//Components
	Hapkit hapkit;
	Canvas designCanvas;
	ForceDisplaySettings forceFeedbackOption;
	HapkitFeedbackSettings hapkitFeedbackPanel;
	ExperimentSettings expSettings;
	SpringFactory forceDisplayOutput;
	PhysicsPlayground physicsPlayground;
	ParticipantSelection participantSelection;
	
	List<Component> components = new ArrayList<Component>();
	
	ControlP5 cp5;
	
	int participantId;
	static ResearchData researchData;
	
	static public void main(String args[]) {
		   PApplet.main(new String[] { "springsim.Main" });
			
			Runtime.getRuntime().addShutdownHook(new Thread()
			{
			    @Override
			    public void run()
			    {
			        endProcedure();
			    }
			});
	}
	
	public void setup() {
		size(width, height);
		background(255);
		
		String pID = JOptionPane.showInputDialog(null,
				  "Enter Participant ID",
				  "Participant ID",
				  JOptionPane.QUESTION_MESSAGE);
		
		participantId = Integer.parseInt(pID);
		
		inputMode = HAPKIT_MODE;
		//inputMode = MOUSE_MODE;
		
		researchData = new ResearchData(participantId, inputMode);
		
		cp5 = new ControlP5(this);
		
		// change the default font to Verdana
		PFont p = createFont("Verdana",12); 
		cp5.setControlFont(p);
		  
		// change the original colors
		cp5.setColorForeground(0xffaa0000);
		cp5.setColorBackground(0xff660000);
		cp5.setColorLabel(0xffdddddd);
		cp5.setColorValue(0xffff88ff);
		cp5.setColorActive(0xffff0000);
		  
		if(inputMode == HAPKIT_MODE){
			hapkit = new Hapkit(this, Serial.list(), 7, researchData);
		}
		
		//participantSelection = new ParticipantSelection(this, cp5, pSX, pSY, pSW, pSH, participantId);
		designCanvas = new Canvas(this, cp5, dPX, dPY, dPW, dPH, hapkit, researchData);
		forceFeedbackOption = new ForceDisplaySettings(this, cp5, fFOX, fFOY, fFOW, fFOH,  designCanvas);
		expSettings = new ExperimentSettings(this, cp5, eSX, eSY, eSW, eSH);
		forceDisplayOutput = new SpringFactory(this, cp5, fDOX, fDOY, fDOW, fDOH);
		physicsPlayground = new PhysicsPlayground(this, cp5, designCanvas, pPX, pPY, pPW, pPH);
		hapkitFeedbackPanel = new HapkitFeedbackSettings(this, cp5, hfx, hfy, hfw, hfh, hapkit, designCanvas.getSpringCollection());
		
		//components.add(participantSelection);
		components.add(designCanvas);
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
		designCanvas.mousePressed();	
	}
	
	public void mouseReleased() {
		designCanvas.mouseReleased();
	}

	public void serialEvent(Serial p){
		hapkit.serialEvent(p);
    }
	
	/**
	 * Generate CSV Log when program closes
	 * 
	 */
	public void stop() {
		System.out.println("GENERATING LOG");
		researchData.generateCSVLog();
	} 
	
	public static void endProcedure(){
		System.out.println("GENERATING LOG");
		researchData.generateCSVLog();
	}
	
}



