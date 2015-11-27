package springsim;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.RadioButton;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class HapkitFeedback implements Component {

	int x;
	int y;
	int w;
	int h;
	Hapkit hapkit;
	PApplet parent;
	ControlP5 cp5;
	RadioButton r, r2;
	PImage hand_img;
	SpringCollection springs;
		
	public HapkitFeedback(Main main, ControlP5 _cp5, int _x, int _y, int _w, int _h, Hapkit _hapkit, SpringCollection _springs) {
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		this.cp5 = _cp5;
		this.hapkit = _hapkit;
		parent = main;
		this.hand_img = parent.loadImage("hand.png");
		this.springs = _springs;
		
		// if we need to implement listeners, consider constructing radio
		// buttons, etc. in main class so that listener can be handed
		// all necessary instances of classes to handle input events appropriately. 
		r = cp5.addRadioButton("feedbackButton")
		         .setPosition(x+90,y+30)
		         .setSize(40,20)
		         .setColorForeground(parent.color(120))
		         .setColorActive(parent.color(200))
		         .setColorLabel(parent.color(0))
		         .setItemsPerRow(1)
		         .setSpacingColumn(50)
		         .addItem("ON",1)
		         .addItem("OFF",0);
		
		r2 = cp5.addRadioButton("GainOption")
		         .setPosition(x+50,y+130)
		         .setSize(20,20)
		         .setColorForeground(parent.color(120))
		         .setColorActive(parent.color(200))
		         .setColorLabel(parent.color(0))
		         .setItemsPerRow(3)
		         .setSpacingColumn(20)
		         .addItem("1x",1)
		         .addItem("2x",2)
		         .addItem("3x",3);
		
		// This line tells the radio button where to find the callback function: in this object.
		r.plugTo(this);
		r2.plugTo(this);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {

		parent.fill(255);
		parent.rect(x, y, w, h);
		parent.fill(0);
		parent.text("Force Feedback", x+10, y+15);
		parent.text("Gain", x+10, y+145);
		parent.image(hand_img, x+20, y+20, hand_img.width / 6, hand_img.height / 6);
		
		parent.text("Spring Force = " + String.format("%.2f", this.springs.getActiveForce()), x+10, y+105);
	}

	public void feedbackButton(int buttonValue) {
		//parent.println("Got button event.");
		if (buttonValue == 1) {
			this.hapkit.setFeedback(true);
		} else if (buttonValue == 0) {
			this.hapkit.setFeedback(false);
		} 
	}
	
	public void GainOption(int buttonValue) {
		this.hapkit.setGain(buttonValue);
	}
	
}
