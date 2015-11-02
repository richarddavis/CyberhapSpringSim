package springsim;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.RadioButton;
import processing.core.PApplet;

public class HapkitFeedback implements Component {

	int x;
	int y;
	int w;
	int h;
	Hapkit hapkit;
	PApplet parent;
	ControlP5 cp5;
	RadioButton r;
		
	public HapkitFeedback(Main main, ControlP5 _cp5, int _x, int _y, int _w, int _h, Hapkit _hapkit) {
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		this.cp5 = _cp5;
		this.hapkit = _hapkit;
		parent = main;
		
		// if we need to implement listeners, consider constructing radio
		// buttons, etc. in main class so that listener can be handed
		// all necessary instances of classes to handle input events appropriately. 
		r = cp5.addRadioButton("feedbackButton")
		         .setPosition(x,y)
		         .setSize(40,20)
		         .setColorForeground(parent.color(120))
		         .setColorActive(parent.color(200))
		         .setColorLabel(parent.color(0))
		         .setItemsPerRow(5)
		         .setSpacingColumn(50)
		         .addItem("feedbackButtonON",1)
		         .addItem("feedbackButtonOFF",2);
		
		// This line tells the radio button where to find the callback function: in this object.
		r.plugTo(this);
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
		parent.text("Hapkit Feedback Control", x, y);
	}

	public void feedbackButton(int buttonValue) {
		//parent.println("Got button event.");
		this.hapkit.setFeedback(false);
	}
	
}
