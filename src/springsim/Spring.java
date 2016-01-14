package springsim;

import java.awt.Font;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class Spring extends SpringInterface {

	int spring_img_w = 35;
	
	//BOX2D
	DistanceJointDef djd;
	DistanceJoint dj;
	
	Anchor anchor;
	PImage spring_img;

	public Spring(int _x, int _y, int _k, int _length,String label, PApplet p, Box2DProcessing box2d, ResearchData rData){

		super(_x, _y, _k, _length, label, p, box2d, rData);
		
		this.hand = new Hand(this.x, this.y + this.originalLen + 10, true, parent, box2d, rData);
		this.anchor = new Anchor(getX(), getY(), parent, box2d);
		
		// Import photo
		this.spring_img = parent.loadImage("spring.jpg");
		
		// Define the joint
		this.djd = new DistanceJointDef();
		
		djd.bodyA = this.anchor.body;
		// Body 2 is the Hand's object
		djd.bodyB = this.hand.body;
		// Get the mouse location in world coordinates
		djd.collideConnected = false;
		djd.length = box2d.scalarPixelsToWorld(this.originalLen);

		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/this.hand.body.m_mass)));
		djd.dampingRatio = 0.001f;
		
		// Make the joint
		dj = (DistanceJoint) box2d.world.createJoint(djd);
	}
	
	//TODO: update hand drawing as well. 
	public void draw(){

		
		//parent.line(this.anchor.getX(), this.anchor.getY(), this.hand.getX(), this.hand.getY());
		parent.fill(0);
		parent.text(this.label, this.x-40, 40);
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
//			parent.stroke(0);
//			parent.strokeWeight(3);
			//parent.line(v1.x,v1.y,v2.x,v2.y);
			
			parent.pushMatrix();
			parent.imageMode(PConstants.CENTER);
			parent.image(spring_img, this.x, (float) (this.y + (0.5*(v2.y-v1.y))), spring_img_w, v2.y-v1.y-(hand.current_hand_img.height/2));
			parent.popMatrix();
			
			//(float) (this.y + (0.5*(v2.y-v1.y)))
			//v2.y-v1.y-(hand.current_hand_img.height/2)
			
			//System.out.println(this.getLength());
			//System.out.println(this.getForce());
			int dfx = this.x - this.hand.w/2-10;
			int dfy = this.hand.y + this.hand.h + 5;
			
			int dsx1 = this.x;
			int dsy1 = (this.anchor.y + this.hand.y)/2;
				
			Font p1 = parent.getFont();
			PFont p2 = parent.createFont("Verdana",12);
			
			if(display_k){
				parent.fill(120);
				parent.pushMatrix();
				parent.textFont(p2);
				parent.text("K: " + k, dsx1+20, dsy1);
				parent.setFont(p1);
				parent.textSize(18);
				parent.popMatrix();
			}
			
			if (this.display_forces == true) {
				parent.fill(100);
				parent.pushMatrix();
				parent.textFont(p2);
				parent.text("Force: " + String.format("%.2f", this.getForce()), dfx, dfy);
				parent.setFont(p1);
				parent.textSize(18);
				parent.popMatrix();	
			}
		}

		this.anchor.draw();
		this.hand.draw();
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
	
	public void setLength(int len_pixels){
		dj.setLength(box2d.scalarPixelsToWorld(len_pixels));
	}
	
	public float getForce() {
		return (this.k * (this.getLength() - dj.getLength()));
	}
	
	@Override
	public void setX(int x){
		this.x = x;
		this.hand.setX(x);
		this.anchor.setX(x);
	}
}
