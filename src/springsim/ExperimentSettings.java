package springsim;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.core.PImage;

public class ExperimentSettings extends Component {

	static int MOON = 2;
	static int EARTH = 1;
	
	DropdownList d1;
	Textfield tf1;
	PApplet parent;
	int gravity_constant;
	PImage earth_img;
	PImage moon_img;

	public ExperimentSettings(Main main, ControlP5 cp5, int _x, int _y, int _w, int _h) {
		super(_x,_y,_w,_h);
		parent = main;
		
		  d1 = cp5.addDropdownList("Experiment Planet")
		          .setPosition(this.x+34, this.y+50)
		          .setSize(150,100)
		          .setItemHeight(20)
		  		  .setBarHeight(20)
		          ;
		  
		  d1.addItem("Earth", EARTH);
		  d1.addItem("Moon", MOON);
		 
		  d1.setValue(1);
		  
		  d1.addListener(this);
		  
		  this.earth_img = parent.loadImage("earth.jpeg");
		  this.moon_img = parent.loadImage("moon.jpeg");
		  
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
		
		if(d1.getValue() == EARTH){
			parent.image(earth_img, this.x+100, this.y+80, 60,60);
		}else if(d1.getValue() == MOON){
			parent.image(moon_img, this.x+100, this.y+80, 60, 60);
		}
		
	}

	@Override
	public void controlEvent(ControlEvent arg0) {
		parent.println("handled dropdown locally");
	}

}
