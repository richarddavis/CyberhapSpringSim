package springsim;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class ParallelSpring extends SpringInterface {

	//BOX2D
	DistanceJointDef djd1;
	DistanceJointDef djd2;
	DistanceJoint dj1;
	DistanceJoint dj2;

	Anchor anchor1;
	Anchor anchor2;

	public ParallelSpring(int _x, int _y, int _k, int _length, PApplet p, Box2DProcessing b2, ResearchData rData){
		
		super(_x, _y, _k, _length, p, b2, rData);
		
		this.x = _x;
		this.y = _y;
		this.k = _k;
		this.originalLen = _length;
		this.parent = p;
		this.box2d = b2;
		
		this.hand = new Hand(this.x, this.y + this.originalLen * 2 + 20, false, parent, box2d, rData);
		this.anchor1 = new Anchor(getX() - 40, getY(), parent, box2d);
		this.anchor2 = new Anchor(getX() + 40, getY(), parent, box2d);

		// Define the first spring distance joint
		this.djd1 = new DistanceJointDef();

		djd1.bodyA = this.anchor1.body;
		djd1.bodyB = this.hand.body;
		djd1.collideConnected = true;
		djd1.length = box2d.scalarPixelsToWorld(this.originalLen);

		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd1.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/(this.hand.body.m_mass))));
		djd1.dampingRatio = 0.01f;

		// Make the first spring distance joint
		dj1 = (DistanceJoint) box2d.world.createJoint(djd1);

		// Second spring distance joint definition

		this.djd2 = new DistanceJointDef();
		djd2.bodyA = this.anchor2.body;
		// Body 2 is the Hand's object
		djd2.bodyB = this.hand.body;
		// Get the mouse location in world coordinates
		djd2.collideConnected = true;
		djd2.length = box2d.scalarPixelsToWorld(this.originalLen);

		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd2.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/(this.hand.body.m_mass))));
		djd2.dampingRatio = 0.01f;

		// Make the second spring distance joint
		dj2 = (DistanceJoint) box2d.world.createJoint(djd2);

	}

	//TODO: update hand drawing as well. 
	public void draw(){

		//parent.line(this.anchor.getX(), this.anchor.getY(), this.hand.getX(), this.hand.getY());

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
			parent.stroke(0);
			parent.strokeWeight(3);
			parent.line(v1.x,v1.y,v2.x,v2.y);
			
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
			parent.stroke(0);
			parent.strokeWeight(3);
			parent.line(v1.x,v1.y,v2.x,v2.y);
			
			//System.out.println(this.getLength());
			//System.out.println(this.getForce());
			this.anchor2.draw();
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
	
	public float getForce() {
		return (this.k * (this.getLength() - dj1.getLength()));
	}

}
