package springsim;

import controlP5.Button;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PConstants;
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
	
	public Canvas(Main main, ControlP5 cp5, int _x, int _y, int _w, int _h, Hapkit _hapkit, ResearchData rData) {
		
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		this.serialData = _hapkit;
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
		
		X = cp5.addButton("Spring X")
		         .setValue(4)
		         .setPosition(this.x+70,this.y+220)
		         .setSize(130,50)
		         .setVisible(false)
		         .setId(2);
		
		Y = cp5.addButton("Spring Y")
		         .setValue(4)
		         .setPosition(this.x+250,this.y+220)
		         .setSize(130,50)
		         .setVisible(false)
		         .setId(2);
			  
		
		// This prevents dynamic bodies from sticking to static ones
		org.jbox2d.common.Settings.velocityThreshold = 0.2f;
		
		s1 = new Spring(this.x+1*(this.w/3), 100, rData.getCurrentXSpring(), 200, parent, box2d, "X");
		s2 = new Spring(this.x+2*(this.w/3), 100, rData.getCurrentYSpring(), 200, parent, box2d, "Y");
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
		parent.rect(x, y, w, h);
		parent.textSize(18); 
		parent.fill(0);
		parent.text("Spring Pair", this.x+170, this.y+400);
		parent.text(rData.getSpringIndex(), this.x+275, this.y+400);
		
		if(rData.getCondition() == rData.CONDITION_GRAPHICS_HAPTICS){
			X.setVisible(false);
			Y.setVisible(false);
			sc.draw();
			parent.fill(0);
			parent.text("Spring X", ceiling.x+100, ceiling.y-10);
			parent.text("Spring Y", ceiling.x+260, ceiling.y-10);
			parent.image(wood_plank_img, ceiling.x+(ceiling.w/2), ceiling.y+((ceiling.h/2)), ceiling.w, ceiling.h);
		}else{
			//X.setVisible(true);
			//Y.setVisible(true);
			parent.pushMatrix();
			parent.imageMode(PConstants.CORNER);
			if(sc.activeSpring.getName().equals("X")){
				parent.image(spring_x_active, springx_img_x, springx_img_y, spring_img_w, spring_img_h);
				parent.image(spring_y, springy_img_x, springy_img_y, spring_img_w, spring_img_h);
			}else if(sc.activeSpring.getName().equals("Y")){
				parent.image(spring_x, springx_img_x, springx_img_y, spring_img_w, spring_img_h);
				parent.image(spring_y_active, springy_img_x, springy_img_y, spring_img_w, spring_img_h);
			}
			parent.popMatrix();
			
		}

		
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
		if(rData.getCondition() == rData.CONDITION_GRAPHICS_HAPTICS){
			sc.updateActiveSpring(parent.mouseX, parent.mouseY, true, false, serialData);
		}else if(clickedSpringX()){
			sc.setXSpringActive();
			serialData.setKConstant(rData.getCurrentXSpring());
		}else if(clickedSpringY()){
			sc.setYSpringActive();
			serialData.setKConstant(rData.getCurrentYSpring());
		}
	}
	
	public boolean clickedSpringX(){
		return (parent.mouseX > springx_img_x
				&& parent.mouseX < springx_img_x+spring_img_w
				&& parent.mouseY > springx_img_y
				&& parent.mouseY < springx_img_y+spring_img_h);
	}
	
	public boolean clickedSpringY(){
		return (parent.mouseX > springy_img_x
				&& parent.mouseX < springy_img_x+spring_img_w
				&& parent.mouseY > springy_img_y
				&& parent.mouseY < springy_img_y+spring_img_h);
	}
	
	public void buttonPressed(){
			rData.logEvent(-1, -1, "next spring pair requested");
			rData.nextSpringPair();

			sc.setSpringX(rData.getCurrentXSpring());
			sc.setSpringY(rData.getCurrentYSpring());
			serialData.setKConstant(sc.activeSpring.k);
	}
	
	public void mouseReleased() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, false, false, serialData);
	}
	
	public SpringCollection getSpringCollection() {
		return this.sc;
	}

}
