package springsim;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import processing.core.PApplet;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class ComboSpring extends SpringInterface {

	//BOX2D
	DistanceJointDef djd1;
	DistanceJointDef djd2;
	DistanceJointDef djd3;
	DistanceJoint dj1;
	DistanceJoint dj2;
	DistanceJoint dj3;

	//RevoluteJointDef rjd1;
	//RevoluteJoint rj1;

	Anchor anchor1;
	Anchor anchor2;
	RectangleConnector conn1;
	RectangleConnector conn2;
	PImage spring_img;

	public ComboSpring(int _x, int _y, int _k, int _length, PApplet p, Box2DProcessing b2){
		super(_x, _y, _k, _length, p, b2);
		
		this.hand = new Hand(this.x, this.y + this.originalLen * 2 + 20, false, parent, box2d);
		this.conn1 = new RectangleConnector(this.x-20, this.y + this.originalLen + 10, true, parent, box2d);
		this.conn2 = new RectangleConnector(this.x+20, this.y + this.originalLen + 10, true, parent, box2d);
		this.anchor1 = new Anchor(getX() - 40, getY(), parent, box2d);
		this.anchor2 = new Anchor(getX() + 40, getY(), parent, box2d);

		// Define the first spring distance joint
		this.djd1 = new DistanceJointDef();

		djd1.bodyA = this.anchor1.body;
		djd1.bodyB = this.conn1.body;
		djd1.collideConnected = true;
		djd1.length = box2d.scalarPixelsToWorld(this.originalLen);

		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd1.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/(this.conn1.body.m_mass + this.hand.body.m_mass))));
		djd1.dampingRatio = 0.01f;

		// Make the first spring distance joint
		dj1 = (DistanceJoint) box2d.world.createJoint(djd1);

		// Second spring distance joint definition

		this.djd2 = new DistanceJointDef();
		djd2.bodyA = this.anchor2.body;
		// Body 2 is the Hand's object
		djd2.bodyB = this.conn1.body;
		// Get the mouse location in world coordinates
		djd2.collideConnected = true;
		djd2.length = box2d.scalarPixelsToWorld(this.originalLen);

		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd2.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/(this.conn1.body.m_mass + this.hand.body.m_mass))));
		djd2.dampingRatio = 0.01f;

		// Make the second spring distance joint
		dj2 = (DistanceJoint) box2d.world.createJoint(djd2);

		// Third spring distance joint definition
		this.djd3 = new DistanceJointDef();
		
		djd3.bodyA = this.conn1.body;
		// Body 2 is the Hand's object
		djd3.bodyB = this.hand.body;
		// Get the mouse location in world coordinates
		djd3.collideConnected = true;
		djd3.length = box2d.scalarPixelsToWorld(this.originalLen);

		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd3.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/(this.conn1.body.m_mass + this.hand.body.m_mass))));
		djd3.dampingRatio = 0.01f;

		// Make the second spring distance joint
		dj3 = (DistanceJoint) box2d.world.createJoint(djd3);
		
//		// Messing around
//		this.conn3 = new Connector(200, 200, true, parent, box2d);
//		this.conn4 = new Connector(220, 220, false, parent, box2d);
//		rjd2 = new RevoluteJointDef();
//		rjd2.initialize(this.conn3.body, this.conn4.body, this.conn3.body.getWorldCenter());
//		rjd2.motorSpeed = 10;
//		rjd2.maxMotorTorque = 10000;
//		rjd2.enableMotor = true;
//		rj2 = (RevoluteJoint) box2d.world.createJoint(rjd2);	
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
			this.conn1.draw();
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
			//this.hand.draw();
		}
		if (dj3 != null) {
			// We can get the two anchor points
			Vec2 v1 = new Vec2(0,0);
			dj3.getAnchorA(v1);
			Vec2 v2 = new Vec2(0,0);
			dj3.getAnchorB(v2);

			// Convert them to screen coordinates
			v1 = box2d.coordWorldToPixels(v1);
			v2 = box2d.coordWorldToPixels(v2);
			
			// And just draw a line to represent the spring
			parent.stroke(0);
			parent.strokeWeight(3);
			parent.line(v1.x,v1.y,v2.x,v2.y);
			
			//System.out.println(this.getLength());
			//System.out.println(this.getForce());
			this.hand.draw();
		}
		//this.conn3.draw();
		//this.conn4.draw();
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
