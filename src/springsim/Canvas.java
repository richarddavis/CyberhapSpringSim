package springsim;

import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class Canvas extends Component {

	static Box2DProcessing box2d;
	PApplet parent;
	Hapkit hapkit;
	
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
	
	int springx_img_x;
	int springx_img_y;
	
	int springy_img_x;
	int springy_img_y;	
	
	int spring_img_w;
	int spring_img_h;
	
	int numSprings;
	private Ruler ruler;
	ResearchData rData;
	
	SpringInterface s1, s2, s3, s4;
	
	SpringCollection sc;
	WeightCollection wc;
	Button delete1;
    Button delete2;
	Button delete3;

	static int X1, X2, X3, Y_ALL; 
	
	
	public Canvas(Main main, ControlP5 cp5, int _x, int _y, int _w, int _h, Hapkit _hapkit, ResearchData rData) {
		
		super(_x,_y,_w,_h);
		
		X1 = x+100;
		X2 = x+250;
		X3 = x+380;
		Y_ALL = this.y+100;
		
		this.hapkit = _hapkit;
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
		
		// This prevents dynamic bodies from sticking to static ones
		org.jbox2d.common.Settings.velocityThreshold = 0.2f;
		
		//s1 = new SerialSpring(this.x+50, this.y+100, 30, 200, "Spring A", this.parent, box2d, rData);
		//s2 = new ParallelSpring(this.x+300, this.y+100, 30, 200, "Spring B",this.parent, box2d, rData);
		s3 = new Spring(X3, this.y+100, 15, 200, "Spring C",this.parent, box2d,rData);
		s2 = new ParallelSpring(X2, this.y+100, 55, 35, 200, "Spring B",this.parent, box2d,rData);
		s1 = new Spring(X1, this.y+100, 35, 200, "Spring A",this.parent, box2d,rData);
		//s4 = new ComboSpring(this.x+150, this.y+100, 30, 100, this.parent, box2d, rData);
		
		sc = new SpringCollection(rData, hapkit);
		sc.add(s1);
		sc.add(s2);
		sc.add(s3);
		sc.setActive(s1);
		
		if(rData.isHapkitMode()){
			rData.logEvent(-1, -1, "Initial K value sent to hapkit");
			//hapkit.setKConstant(sc.activeSpring.getK());
		}
		
		floor = new Boundary(this.x + this.w/2, this.h - 20, this.w - 20, 20, parent, box2d);
		ceiling = new Boundary(this.x+10, this.y+30, this.w - 20, 30, parent, box2d);
		ruler = new Ruler(parent, cp5, this.x+20, this.y+100,40, 300, 7);
		
		delete1 = cp5.addButton("Delete1")
			     .setValue(0)
			     .setPosition(x+77,y+25)
			     .setSize(55,20)
			     .setCaptionLabel("Delete")
			     .setId(1);
		
		delete2 = cp5.addButton("Delete2")
			     .setValue(1)
			     .setPosition(x+220,y+25)
			     .setSize(55,20)
			     .setCaptionLabel("Delete")
			     .setId(1);
		
		delete3 = cp5.addButton("Delete3")
			     .setValue(2)
			     .setPosition(x+370,y+25)
			     .setSize(55,20)
			     .setCaptionLabel("Delete")
			     .setId(1);
		
		cp5.addListener(this);
		
	}
	
	public void step(){
		this.box2d.step();
		
		// Why is this here?
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
		
		parent.pushMatrix();
		parent.imageMode(PConstants.CORNER);
		parent.image(wood_plank_img, this.x+10, this.y+50, this.w-20, 30);
		parent.popMatrix();
		
		sc.draw();
		floor.draw();
		ruler.draw();
	}
	
	private void updateSpringPosition() {
		sc.updateActiveSpringYPosition(hapkitPos);
	}
	
	public void readHapkitPos() {
		hapkitPos = this.hapkit.getPos();
	}
	
	public void displayForces(boolean on) {
		this.sc.displayForces(on);
	}
	
	public void mousePressed() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, true, hapkit);
	}
	
	public void mouseReleased() {
		sc.updateActiveSpring(parent.mouseX, parent.mouseY, false, hapkit);
	}
	
	public SpringCollection getSpringCollection() {
		return this.sc;
	}

	@Override
	public void controlEvent(ControlEvent event) {
		System.out.println(event.getValue());
		if(event.isFrom(delete1)){
			sc.delete((int)event.getValue());
		}else if(event.isFrom(delete2)){
			sc.delete((int)event.getValue());
		}else if(event.isFrom(delete3)){
			sc.delete((int)event.getValue());
		}
	}

	public void displayStiffness(boolean b) {
		this.sc.displayStiffness(b);
	}

}
