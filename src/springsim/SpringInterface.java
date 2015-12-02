package springsim;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import processing.core.PApplet;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public abstract class SpringInterface {
	
	int x;
	int y;
	int currentLen;
	int originalLen;
	int k;
	String label;
	boolean display_forces;
	
	PApplet parent;
	Hand hand;
	Anchor anchor;
	PImage spring_img;
	Box2DProcessing box2d;

	public SpringInterface(int _x, int _y, int _k, int _length, String label, PApplet p, Box2DProcessing b2, ResearchData rData){
		this.x = _x;
		this.y = _y;
		this.k = _k;
		this.originalLen = _length;
		this.parent = p;
		this.box2d = b2;
		this.hand = new Hand(this.x, this.y + this.originalLen + 10, true, parent, box2d, rData);
		this.anchor = new Anchor(getX(), getY(), parent, box2d);
		this.label = label;
		this.display_forces = true;
	}
	
	public void mouseUpdate(int mx, int my, boolean pressed) {
		this.hand.mouseUpdate(mx, my, pressed);
	}

	public void hapkitUpdate(int my) {
		this.hand.hapkitUpdate(my);
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getK() {
		return this.k;
	}
	
	public void setK(int k) {
		this.k = k;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void displayForce(boolean on) {
		if (on == true) {
			this.display_forces = true;
			System.out.println("Turning on force display under spring.");
		} else if (on == false) {
			this.display_forces = false;
			System.out.println("Turning off force display under spring.");
		}
	}
	
	public abstract float getLength();
	public abstract float getForce();
	public abstract void draw();


}






















