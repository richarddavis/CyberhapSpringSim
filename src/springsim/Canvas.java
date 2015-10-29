package springsim;

import processing.core.PApplet;
import processing.serial.Serial;
import shiffman.box2d.Box2DProcessing;

public class Canvas implements Component {

	Box2DProcessing box2d;
	Spring s1;
	Spring s2;
	Spring s3;
	SpringCollection sc;
	
	double hapkitPos;
	
	Hand hand;
	Boundary ceiling;
	Boundary floor;
	
	PApplet parent;
	
	int x;
	int y;
	int w;
	int h;
	
	//Serial Data
	int serialPortIndex = 7;
	Hapkit serialData;
	
	public Canvas(Main main, int _x, int _y, int _w, int _h) {
		
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		
		parent = main; 
		
		box2d = new Box2DProcessing(parent);
		box2d.createWorld();
		box2d.setScaleFactor(500);
		box2d.setGravity(0, -2);
		
		// This prevents dynamic bodies from sticking to static ones
		org.jbox2d.common.Settings.velocityThreshold = 0.2f;
		
		s1 = new Spring(100, 100, 10, 100, parent, box2d);
		s2 = new Spring(200, 100, 40, 100, parent, box2d);
		s3 = new Spring(300, 100, 70, 100, parent, box2d);
		
		sc = new SpringCollection();
		sc.add(s1);
		sc.add(s2);
		sc.add(s3);
		
		//set initial active spring
		sc.setActive(s1);
		
		floor = new Boundary(this.w/2, this.h - 20, this.w - 20, 20, parent, box2d);
		ceiling = new Boundary(10, 10, this.w - 20, 20, parent, box2d);
		
		// Initialize Serial Comms
		serialData = new Hapkit(parent, Serial.list(), serialPortIndex);
	}
	
	public void step(){
		this.box2d.step();
		updateSpringPosition();
		readHapkitPos();
	}
	
	public void draw(){
		int xRect = x+(w/2);
		int yRect = y+(h/2);
		
		parent.fill(255);
		parent.stroke(0);
		parent.rect(xRect, yRect, w, h);
		
		sc.draw();
		floor.draw();
	}
	
	private void updateSpringPosition() {
		
		sc.updateActiveSpringY(hapkitPos);
	}
	
	public void readHapkitPos() {
		double rawValue = serialData.readIn();
		
		if(rawValue != 0.0){
			hapkitPos = rawValue;
		}
	}
	
	public void mousePressed() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, true, false, serialData);
		
	}
	
	public void mouseReleased() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, false, false, serialData);
	}


}
