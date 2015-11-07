package springsim;

import processing.core.PApplet;
import processing.core.PImage;
import processing.serial.Serial;
import shiffman.box2d.Box2DProcessing;

public class Canvas implements Component {

	Box2DProcessing box2d;
	Spring s1;
	Spring s2;
	Spring s3;
	SpringCollection sc;
	
	//Serial Data
	//int serialPortIndex = 0;
	Hapkit serialData;
	
	double hapkitPos;
	
	Hand hand;
	Boundary ceiling;
	Boundary floor;
	
	PApplet parent;
	
	PImage wood_plank_img;
	
	int x;
	int y;
	int w;
	int h;
	
	int numSprings;
	
	public Canvas(Main main, int _x, int _y, int _w, int _h, Hapkit _hapkit) {
		
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		this.serialData = _hapkit;
		this.numSprings = 3;
		
		parent = main; 
		
		wood_plank_img = parent.loadImage("wood-plank.jpg");
		
		box2d = new Box2DProcessing(parent);
		box2d.createWorld();
		box2d.setScaleFactor(500);
		box2d.setGravity(0, -2);
		
		// This prevents dynamic bodies from sticking to static ones
		org.jbox2d.common.Settings.velocityThreshold = 0.2f;
		
		s1 = new Spring(this.x+1*(this.w/4), 100, 10, 200, parent, box2d);
		s2 = new Spring(this.x+2*(this.w/4), 100, 40, 200, parent, box2d);
		s3 = new Spring(this.x+3*(this.w/4), 100, 70, 200, parent, box2d);
		
		sc = new SpringCollection();
		sc.add(s1);
		sc.add(s2);
		sc.add(s3);
		
		//set initial active spring
		sc.setActive(s1);
		
		floor = new Boundary(this.x + this.w/2, this.h - 20, this.w - 20, 20, parent, box2d);
		ceiling = new Boundary(this.x+10, this.y+30, this.w - 20, 30, parent, box2d);
		
		// Initialize Serial Comms
		// serialData = new Hapkit(parent, Serial.list(), serialPortIndex);
	}
	
	public void step(){
		this.box2d.step();
		updateSpringPosition();
		readHapkitPos();
		//parent.println(hapkitPos);
	}
	
	public void draw(){
		int xRect = x+(w/2);
		int yRect = y+(h/2);
		
		parent.fill(255);
		parent.stroke(0);
		parent.rect(xRect, yRect, w, h);
		
		sc.draw();
		parent.image(wood_plank_img, ceiling.x+(ceiling.w/2), ceiling.y+((ceiling.h/2)), ceiling.w, ceiling.h);
		
		floor.draw();
	}
	
	private void updateSpringPosition() {
		sc.updateActiveSpringY(hapkitPos);
	}
	
	public void readHapkitPos() {
		double rawValue = serialData.readIn();
		
		if(rawValue != 0.0){
			hapkitPos = rawValue*4;
			//this.parent.println(rawValue);
		}
	}
	
	public void displayForces(boolean on) {
		if (on == true) {
			parent.println("Displaying forces in the canvas.");
		} else {
			parent.println("Not displaying forces in the canvas.");
		}
	}
	
	public void mousePressed() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, true, false, serialData);
		
	}
	
	public void mouseReleased() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, false, false, serialData);
	}
	
	public SpringCollection getSpringCollection() {
		return this.sc;
	}

}
