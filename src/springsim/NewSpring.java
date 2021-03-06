package springsim;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import processing.core.PApplet;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class NewSpring {

	int x;
	int y;
	int currentLen;
	int originalLen;
	int k;

	//BOX2D
	DistanceJointDef djd;
	DistanceJoint dj;
	Box2DProcessing box2d;
	
	PApplet parent;
	Hand hand;
	Anchor anchor;
	PImage spring_img;

	public NewSpring(int _x, int _y, int _k, int _length, PApplet p, Box2DProcessing b2){
		this.x = _x;
		this.y = _y;
		this.k = _k;
		this.originalLen = _length;
		this.parent = p;
		this.box2d = b2;
		this.hand = new Hand(this.x, this.y + this.originalLen + 10, parent, box2d);
		this.anchor = new Anchor(getX(), getY(), parent, box2d);
		
		// Import photo
		this.spring_img = parent.loadImage("spring.jpg");
		
		// Define the joint
		this.djd = new DistanceJointDef();
		
		djd.bodyA = this.anchor.body;
		// Body 2 is the Hand's object
		djd.bodyB = this.hand.body;
		// Get the mouse location in world coordinates
		djd.collideConnected = true;
		djd.length = box2d.scalarPixelsToWorld(this.originalLen);
		
		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/this.hand.body.m_mass)));
		djd.dampingRatio = 0.01f;
		
		// Make the joint
		dj = (DistanceJoint) box2d.world.createJoint(djd);
	}

	//TODO: update hand drawing as well. 
	void draw(){

		//parent.line(this.anchor.getX(), this.anchor.getY(), this.hand.getX(), this.hand.getY());

		if (dj != null) {
			// We can get the two anchor points
			Vec2 v1 = new Vec2(0,0);
			dj.getAnchorA(v1);
			Vec2 v2 = new Vec2(0,0);
			dj.getAnchorB(v2);

			// Convert them to screen coordinates
			v1 = box2d.coordWorldToPixels(v1);
			v2 = box2d.coordWorldToPixels(v2);

			// And draw the spring
			//int height = (int) (v2.y - v1.y);
			//int width = 30;
			//spring_img.resize(width, height);
			//parent.image(spring_img, v1.x, v1.y);
			
			// And just draw a line
			parent.stroke(0);
			parent.strokeWeight(3);
			parent.line(v1.x,v1.y,v2.x,v2.y);
			
			//System.out.println(this.getLength());
			//System.out.println(this.getForce());
		}

		this.anchor.draw();
		this.hand.draw();
	}
	
	public void mouseUpdate(int mx, int my, boolean pressed) {
		this.hand.mouseUpdate(mx, my, pressed);
	}
	
	public float getLength() {
		Vec2 v1 = new Vec2(0,0);
		dj.getAnchorA(v1);
		Vec2 v2 = new Vec2(0,0);
		dj.getAnchorB(v2);

		// Convert them to screen coordinates
		//v1 = box2d.coordWorldToPixels(v1);
		//v2 = box2d.coordWorldToPixels(v2);
		
		return (v2.sub(v1)).length();
	}
	
	public float getForce() {
		return (this.k * (this.getLength() - dj.getLength()));
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

}
