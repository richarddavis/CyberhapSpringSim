package springsim;

import java.awt.Font;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class ParallelSpring extends SpringInterface {

	//BOX2D
	DistanceJointDef djd1;
	DistanceJointDef djd2;
	DistanceJoint dj1;
	DistanceJoint dj2;

	int spring_img_w = 35;
	
	Anchor anchor1;
	Anchor anchor2;
	
	PImage spring_img;
	private int k1;
	private int k2;

	public ParallelSpring(int _x, int _y, int _k1,int _k2, int _length, String label, PApplet p, Box2DProcessing b2, ResearchData rData){
		
		super(_x, _y, (_k1+_k2), _length,label, p, b2, rData);
		
		this.x = _x;
		this.y = _y;
		this.k = (_k1 + _k2);
		this.k1 = _k1;
		this.k2 = _k2;
		this.parent = p;
		this.box2d = b2;
		
		this.hand = new Hand(this.x, this.y + this.originalLen * 2 + 20, false, parent, box2d, rData);
		this.anchor1 = new Anchor(getX() - 40, getY(), parent, box2d);
		this.anchor2 = new Anchor(getX() + 40, getY(), parent, box2d);
		
		this.spring_img = parent.loadImage("spring.jpg");

		// Define the first spring distance joint
		this.djd1 = new DistanceJointDef();

		djd1.bodyA = this.anchor1.body;
		djd1.bodyB = this.hand.body;
		djd1.collideConnected = true;
		djd1.length = box2d.scalarPixelsToWorld(this.originalLen);

		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd1.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k1/(this.hand.body.m_mass))));
		djd1.dampingRatio = 0.001f;

		// Make the first spring distance joint
		dj1 = (DistanceJoint) box2d.world.createJoint(djd1);

		// Second spring distance joint definition

		this.djd2 = new DistanceJointDef();
		djd2.bodyA = this.anchor2.body;
		// Body 2 is the Hand's object
		djd2.bodyB = this.hand.body;
		// Get the mouse location in world coordinates
		djd2.collideConnected = false;
		djd2.length = box2d.scalarPixelsToWorld(this.originalLen);

		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd2.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k2/(this.hand.body.m_mass))));
		djd2.dampingRatio = 0.001f;

		// Make the second spring distance joint
		dj2 = (DistanceJoint) box2d.world.createJoint(djd2);

	}

	//TODO: update hand drawing as well. 
	public void draw(){

		//parent.line(this.anchor.getX(), this.anchor.getY(), this.hand.getX(), this.hand.getY());
		parent.pushMatrix();
		parent.fill(0);
		parent.text(getLabel(), this.x-40, 40);
		parent.popMatrix();
		
		if (dj1 != null) {
			// We can get the two anchor points
			Vec2 v1 = new Vec2(0,0);
			dj1.getAnchorA(v1);
			Vec2 v2 = new Vec2(0,0);
			dj1.getAnchorB(v2);

			// Convert them to screen coordinates
			v1 = box2d.coordWorldToPixels(v1);
			v2 = box2d.coordWorldToPixels(v2);
			
			// And just draw a line to represent the spring
//			parent.stroke(0);
//			parent.strokeWeight(3);
//			parent.line(v1.x,v1.y,v2.x,v2.y);
			
			double angle = Math.atan2(v2.y-v1.y, v2.x-v1.x);
			parent.pushMatrix();
			parent.translate(((hand.x+anchor.x)/2)-20, (hand.y+anchor.y)/2);
			parent.rotate((float) (angle+(Math.PI/2)));
			System.out.println(angle);
//			parent.image(spring_img, v2.x, (float) (this.y + (0.5*(v2.y-v1.y))), spring_img_w, v2.y-v1.y-(hand.current_hand_img.height/2));
			parent.image(spring_img, 0, 0, spring_img_w, v2.y-v1.y-(hand.current_hand_img.height/2));
			parent.popMatrix();
			
			//System.out.println(this.getLength());
			//System.out.println(this.getForce());
			this.anchor1.draw();
		}
		if (dj2 != null) {
			// We can get the two anchor points
			Vec2 v1 = new Vec2(0,0);
			dj2.getAnchorA(v1);
			Vec2 v2 = new Vec2(0,0);
			dj2.getAnchorB(v2);

			// Convert them to screen coordinates
			v1 = box2d.coordWorldToPixels(v1);
			v2 = box2d.coordWorldToPixels(v2);
			
			// And just draw a line to represent the spring
//			parent.stroke(0);
//			parent.strokeWeight(3);
//			parent.line(v1.x,v1.y,v2.x,v2.y);
			
			double angle = Math.atan2(v1.y-v2.y, v1.x-v2.x);
			parent.pushMatrix();
			parent.translate(((hand.x+anchor.x)/2)+20, (hand.y+anchor.y)/2);
			parent.rotate((float) (angle+(Math.PI/2)));
			System.out.println(angle);
//			parent.image(spring_img, v2.x, (float) (this.y + (0.5*(v2.y-v1.y))), spring_img_w, v2.y-v1.y-(hand.current_hand_img.height/2));
			parent.image(spring_img, 0, 0, spring_img_w, v2.y-v1.y-(hand.current_hand_img.height/2));
			parent.popMatrix();
			
			
			//System.out.println(this.getLength());
			//System.out.println(this.getForce());
			this.anchor2.draw();
		}
		
		int dfx = this.x - this.hand.w/2-10;
		int dfy = this.hand.y + this.hand.h + 5;
		
		int dsx1 = (this.anchor1.x + this.hand.x)/2;
		int dsy1 = (this.anchor1.y + this.hand.y)/2;
		
		int dsx2 = (this.anchor2.x + this.hand.x)/2;
		int dsy2 = (this.anchor2.y + this.hand.y)/2;
			
		Font p1 = parent.getFont();
		PFont p2 = parent.createFont("Verdana",12);
		
		if(display_k){
			parent.fill(120);
			parent.pushMatrix();
			parent.textFont(p2);
			parent.text("K: " + k1, dsx1-55, dsy1);
			parent.text("K: " + k2, dsx2+25, dsy2);
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
		
		this.hand.draw();
	}
	
	public float getLength() {
		Vec2 v1 = new Vec2(0,0);
		dj1.getAnchorA(v1);
		Vec2 v2 = new Vec2(0,0);
		dj1.getAnchorB(v2);

		// Convert them to screen coordinates
		//v1 = box2d.coordWorldToPixels(v1);
		//v2 = box2d.coordWorldToPixels(v2);
		
		return (v2.sub(v1)).length();
	}
	
	public void setLength(int len_pixels){
		dj1.setLength(box2d.scalarPixelsToWorld(len_pixels));
		dj2.setLength(box2d.scalarPixelsToWorld(len_pixels));
	}
	
	public float getForce() {
		return (this.k * (this.getLength() - dj1.getLength()));
	}
	
	
	@Override
	public void setK(int val) {
		this.k1 = val;
	}
	
	@Override
	public void setK2(int val) {
		this.k2 = val;
	}

}
