package springsim;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.RadioButton;
import controlP5.Textfield;
import processing.core.PApplet;
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
	Textfield tf1;
	
		
	public HapkitFeedback(Main main, ControlP5 _cp5, int _x, int _y, int _w, int _h, Hapkit _hapkit) {
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		this.cp5 = _cp5;
		this.hapkit = _hapkit;
		parent = main;
		this.hand_img = parent.loadImage("hand.png");
		
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
		         .addItem("OFF",2);
		
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
		         .addItem("3x",2);
		
		  tf1 = cp5.addTextfield("Force")
			      .setPosition(x+110,y+90)
			      .setSize(60,25)
			      .setFocus(true)
			      ;
		
		
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
		int xRect = x+(w/2);
		int yRect = y+(h/2);
		
		parent.fill(255);
		parent.rect(xRect, yRect, w, h);
		parent.fill(0);
		parent.text("Force Feedback", x+10, y+15);
		parent.text("Gain", x+10, y+145);
		parent.image(hand_img, x+50, y+50, hand_img.width / 6, hand_img.height / 6);
		
		parent.text("Spring Force = ", x+10, y+105);
	}

	public void feedbackButton(int buttonValue) {
		//parent.println("Got button event.");
		this.hapkit.setFeedback(false);
	}
	
}
