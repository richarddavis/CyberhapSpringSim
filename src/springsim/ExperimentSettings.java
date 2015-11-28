package springsim;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.Textfield;
import processing.core.PApplet;

public class ExperimentSettings extends Component {

	DropdownList d1;
	Textfield tf1;
	PApplet parent;

	public ExperimentSettings(Main main, ControlP5 cp5, int _x, int _y, int _w, int _h) {
		super(_x,_y,_w,_h);
		parent = main;
		
		  d1 = cp5.addDropdownList("myList-d1")
		          .setPosition(this.x+60, this.y+50)
		          .setSize(80,40)
		          .setItemHeight(20)
		  		  .setBarHeight(20)
		          ;
		  
		  d1.addItem("Earth", 1);
		  
		  d1.addListener(this);
		  
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
		parent.text("Experiment Settings", x+10, y+15);
		
	}

	@Override
	public void controlEvent(ControlEvent arg0) {
		parent.println("handled dropdown locally");
	}

}
