package springsim;

import java.awt.Font;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.RadioButton;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class HapkitFeedbackSettings extends Component {

	Hapkit hapkit;
	PApplet parent;
	ControlP5 cp5;
	RadioButton feedback, gain;
	PImage hand_img;
	SpringCollection springs;
		
	public HapkitFeedbackSettings(Main main, ControlP5 _cp5, int _x, int _y, int _w, int _h, Hapkit _hapkit, SpringCollection _springs) {
		super(_x, _y, _w, _h);
		this.cp5 = _cp5;
		this.hapkit = _hapkit;
		parent = main;
		this.hand_img = parent.loadImage("hapkit.jpg");
		this.springs = _springs;
		
		// if we need to implement listeners, consider constructing radio
		// buttons, etc. in main class so that listener can be handed
		// all necessary instances of classes to handle input events appropriately. 
		feedback = cp5.addRadioButton("feedbackButton")
		         .setPosition(x+100,y+50)
		         .setSize(40,20)
		         .setColorForeground(parent.color(120))
		         .setColorActive(parent.color(200))
		         .setColorLabel(parent.color(0))
		         .setItemsPerRow(1)
		         .setSpacingColumn(50)
		         .addItem("ON",1)
		         .addItem("OFF",0)
		         .activate(0);
		
		gain = cp5.addRadioButton("gainOption")
		         .setPosition(x+70,y+130)
		         .setSize(20,20)
		         .setColorForeground(parent.color(120))
		         .setColorActive(parent.color(200))
		         .setColorLabel(parent.color(0))
		         .setItemsPerRow(3)
		         .setSpacingColumn(25)
		         .addItem("1x",1)
		         .addItem("2x",2)
		         .addItem("3x",3)
		         .activate(0);
		
		cp5.addListener(this);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw() {
		parent.fill(255);
		parent.stroke(0);
		parent.rect(x, y, w, h);
		parent.fill(0);
		parent.text("HapKit Feedback", x+10, y+25);
		
		parent.pushMatrix();
		Font p1 = parent.getFont();
		PFont p2 = parent.createFont("Verdana",12);
		parent.textFont(p2);
		parent.text("AMPLIFY FORCES:", x+10, y+125);
		parent.setFont(p1);
		parent.textSize(18);
		parent.popMatrix();

		parent.image(hand_img, x+45, y+65, hand_img.width / 2, hand_img.height / 2);
	}

//	public void feedbackButton(int buttonValue) {
//		//parent.println("Got button event.");
//		if (buttonValue == 1) {
//			this.hapkit.setFeedback(true);
//		} else if (buttonValue == 0) {
//			this.hapkit.setFeedback(false);
//		} 
//	}
//	
//	public void GainOption(int buttonValue) {
//		this.hapkit.setGain(buttonValue);
//	}

	@Override
	public void controlEvent(ControlEvent event) {
		if(event.isFrom(gain)){
			this.hapkit.setGain((int) gain.getValue());
		}else if(event.isFrom(feedback)){
			if (event.getValue() == 1) {
				this.hapkit.setFeedback(true);
			} else if (event.getValue() == 0) {
				this.hapkit.setFeedback(false);
			} 
		}
	}
	
}
