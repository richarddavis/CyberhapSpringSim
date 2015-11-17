package springsim;

import processing.core.PApplet;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Textfield;

public class ParticipantSelection implements Component{
	
	int x;
	int y;
	int w;
	int h;
	
	PApplet parent;
	ControlP5 cp5;
	
	Textfield myTextfield;
	
	public ParticipantSelection(Main parent, ControlP5 cp5, int _x, int _y, int _w, int _h, int participantId){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		
		this.parent = parent;
		this.cp5 = cp5;
		
		myTextfield = cp5.addTextfield("participantId")
                .setPosition(this.x+60, this.y+30)
                .setSize(80,20)
                .setFocus(true)
                ;

		myTextfield.plugTo(this);
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		//adjust for the fact rect() uses center, not top-left, xy coordinates
		int xRect = x+(w/2);
		int yRect = y+(h/2);
		
		//parent.stroke(120);
		parent.fill(255);
		parent.rect(xRect, yRect, w, h);
		parent.fill(0);
		parent.text("Participant ID", x+10, y+15);
	}
	
	void participantId(String theValue) {
	    parent.println(theValue);
	}

}
