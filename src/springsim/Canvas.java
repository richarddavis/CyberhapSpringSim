package springsim;

import controlP5.Button;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class Canvas implements Component {

	Box2DProcessing box2d;
	PApplet parent;
	Hapkit hapkitData;
	
	double hapkitPos;
	
	Hand hand;
	Boundary ceiling;
	Boundary floor;
	Weight weight;
	
	PImage wood_plank_img;
	PImage next_img;
	
	PImage spring_x;
	PImage spring_y;
	PImage spring_x_active;
	PImage spring_y_active;
	
	Button next, X, Y;
	
	int x;
	int y;
	int w;
	int h;
	
	int springx_img_x;
	int springx_img_y;
	
	int springy_img_x;
	int springy_img_y;	
	
	int spring_img_w;
	int spring_img_h;
	
	int numSprings;
	private Ruler ruler;
	ResearchData rData;
	
	SerialSpring s1;
	ComboSpring s2;
	ParallelSpring s3;
	Spring s4;
	
	SpringCollection sc;
	WeightCollection wc;


	
	public Canvas(Main main, ControlP5 cp5, int _x, int _y, int _w, int _h, Hapkit _hapkit, ResearchData rData) {
		
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		this.hapkitData = _hapkit;
		this.numSprings = 3;
		this.rData = rData;
		
		spring_img_w = 100;
		spring_img_h = 100;
		
		springx_img_x = this.x+(this.w/4)-(spring_img_w/2);
		springx_img_y = this.y+150;
		
		springy_img_x = this.x+(3*(this.w/4))-(spring_img_w/2);
		springy_img_y = this.y+150;	
		
		parent = main; 
		
		wood_plank_img = parent.loadImage("wood-plank.jpg");
		next_img = parent.loadImage("arrow-next.png");
		spring_x = parent.loadImage("springx.jpg");
		spring_y = parent.loadImage("springy.jpg");
		spring_x_active = parent.loadImage("springx-active.jpg");
		spring_y_active = parent.loadImage("springy-active.jpg");
		
		box2d = new Box2DProcessing(parent);
		box2d.createWorld();
		box2d.setScaleFactor(500);
		box2d.setGravity(0, -2);
		
		next = cp5.addButton("Next Springs")
			         .setValue(4)
			         .setPosition(this.x+300,this.y+470)
			         .setSize(130,50)
			         .setId(2);
		
		
		// This prevents dynamic bodies from sticking to static ones
		org.jbox2d.common.Settings.velocityThreshold = 0.2f;
		
		s1 = new SerialSpring(this.x+50, this.y+100, 30, 100, this.parent, box2d, rData);
		s2 = new ComboSpring(this.x+150, this.y+100, 30, 100, this.parent, box2d, rData);
		s3 = new ParallelSpring(this.x+300, this.y+100, 30, 100, this.parent, box2d, rData);
		s4 = new Spring(this.x+400, this.y+100, 30, 100, this.parent, box2d,rData);
		
		sc = new SpringCollection(rData);
		sc.add(s1);
		sc.add(s2);
		sc.add(s3);
		sc.add(s4);
		sc.setActive(s1);
		
		if(rData.isHapkitMode()){
			rData.logEvent(-1, -1, "Initial K value sent to hapkit");
			hapkitData.setKConstant(sc.activeSpring.getK());
		}
		
		floor = new Boundary(this.x + this.w/2, this.h - 20, this.w - 20, 20, parent, box2d);
		ceiling = new Boundary(this.x+10, this.y+30, this.w - 20, 30, parent, box2d);
		ruler = new Ruler(parent, this.x+20, this.y+80, 30, 400, 12);
		
	}
	
	public void step(){
		this.box2d.step();
		
		if(rData.isHapkitMode()){
		  updateSpringPosition();
		  readHapkitPos();
		}
	}
	
	public void draw(){

		parent.fill(255);
		parent.stroke(0);
		parent.rect(x, y, w, h);
		parent.textSize(18); 
		parent.fill(0);
		
		for(SpringInterface s : sc.springs){
			s.draw();
		}
		
		floor.draw();
	}
	
	private void updateSpringPosition() {
		sc.updateActiveSpringYPosition(hapkitPos);
	}
	
	public void readHapkitPos() {
		hapkitPos = this.hapkitData.getPos();
	}
	
	public void displayForces(boolean on) {
		this.sc.displayForces(on);
	}
	
	public void mousePressed() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, true, hapkitData);
	}
	
	public void mouseReleased() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, false, hapkitData);
	}
	
	public SpringCollection getSpringCollection() {
		return this.sc;
	}

}
