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
	PImage next_img;
	
	int x;
	int y;
	int w;
	int h;
	
	int numSprings;
	private Ruler ruler;
	private int[][][] springData;
	CSVLogOutput log;
	int condition;
	boolean showSprings;
	int springIndex;
	
	public Canvas(Main main, int _x, int _y, int _w, int _h, Hapkit _hapkit, int[][][] springData, CSVLogOutput log, int initCondition) {
		
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		this.serialData = _hapkit;
		this.log = log;
		this.numSprings = 3;
		this.condition = initCondition;
		
		parent = main; 
		this.springData = springData;

		
		wood_plank_img = parent.loadImage("wood-plank.jpg");
		next_img = parent.loadImage("arrow-next.png");
		
		box2d = new Box2DProcessing(parent);
		box2d.createWorld();
		box2d.setScaleFactor(500);
		box2d.setGravity(0, -2);
		
		// This prevents dynamic bodies from sticking to static ones
		org.jbox2d.common.Settings.velocityThreshold = 0.2f;
		
		springIndex = 0;
		showSprings = true;
		
		s1 = new Spring(this.x+1*(this.w/3), 100, springData[0][1][0], 200, parent, box2d);
		s2 = new Spring(this.x+2*(this.w/3), 100, springData[0][1][1], 200, parent, box2d);
		//s3 = new Spring(this.x+3*(this.w/4), 100, 70, 200, parent, box2d);
		
		sc = new SpringCollection();
		sc.add(s1);
		sc.add(s2);
		//sc.add(s3);
		
		//set initial active spring
		sc.setActive(s1);
		
		floor = new Boundary(this.x + this.w/2, this.h - 20, this.w - 20, 20, parent, box2d);
		ceiling = new Boundary(this.x+10, this.y+30, this.w - 20, 30, parent, box2d);
		//ruler = new Ruler(parent, this.x+20, this.y+80, 30, 400, 12);
		
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
		parent.fill(0);
		parent.text("Spring X", ceiling.x+100, ceiling.y-10);
		parent.text("Spring Y", ceiling.x+260, ceiling.y-10);
		parent.text("Spring Pair", ceiling.x+170, ceiling.y+400);
		parent.text(springIndex+1, ceiling.x+250, ceiling.y+400);
		parent.image(wood_plank_img, ceiling.x+(ceiling.w/2), ceiling.y+((ceiling.h/2)), ceiling.w, ceiling.h);
		parent.image(next_img, ceiling.x+400, ceiling.y+450, 80, 80);
		
		floor.draw();
	}
	
	private void updateSpringPosition() {
		sc.updateActiveSpringY(hapkitPos);
	}
	
	public void readHapkitPos() {
		hapkitPos = this.serialData.getPos();
	}
	
	public void displayForces(boolean on) {
		this.sc.displayForces(on);
	}
	
	public void mousePressed() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, true, false, serialData);
		if(parent.mouseX > ceiling.x+360 && parent.mouseX < ceiling.x+440
				&& parent.mouseY > ceiling.y+410 && parent.mouseY < ceiling.y+490){
			
			CSVLogEvent e = new CSVLogEvent(condition, springIndex, -1, -1);
			e.setNotes("next spring pair requested");
			log.addEvent(e);
			
			this.springIndex++;
			if(springIndex > 14){
				showSprings = false;
			}
			sc.setSpringX(springData[0][springIndex][0]);
			sc.setSpringY(springData[0][springIndex][1]);
			serialData.setKConstant(sc.activeSpring.k);
		}
		
	}
	
	public void mouseReleased() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, false, false, serialData);
	}
	
	public SpringCollection getSpringCollection() {
		return this.sc;
	}

}
