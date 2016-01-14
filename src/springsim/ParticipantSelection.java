package springsim;

import processing.core.PApplet;
import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;

public class ParticipantSelection extends Component{
	
	int x;
	int y;
	int w;
	int h;
	
	PApplet parent;
	ControlP5 cp5;
	int participantId;
	
	public ParticipantSelection(Main parent, ControlP5 cp5, int _x, int _y, int _w, int _h, int pID){
		super(_x,_y,_w,_h);
		this.participantId = pID;
		this.parent = parent;
		this.cp5 = cp5;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		//parent.stroke(120);
		parent.fill(255);
		//parent.rect(xRect, yRect, w, h);
		parent.fill(0);
		parent.text("Participant ID: ", x+10, y+15);
		parent.text(participantId, x+140, y+15);
	}

	public void submit(ControlEvent theEvent, int participantId) {
		parent.println("yee");
	}

	@Override
	public void controlEvent(ControlEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
