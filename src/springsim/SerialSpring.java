package springsim;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import processing.core.PApplet;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class SerialSpring extends SpringInterface {

	//BOX2D
	DistanceJointDef djd1;
	DistanceJointDef djd2;
	DistanceJoint dj1;
	DistanceJoint dj2;
	
	RevoluteJointDef rjd1;
//	RevoluteJointDef rjd2;
	RevoluteJoint rj1;
//	RevoluteJoint rj2;
	
	Anchor anchor;
	CircleConnector conn1;
	CircleConnector conn2;
//	Connector conn3;
//	Connector conn4;
	PImage spring_img;

	public SerialSpring(int _x, int _y, int _k, int _length, PApplet p, Box2DProcessing b2, ResearchData rData){
		super(_x, _y, _k, _length, p, b2, rData);
		
		this.hand = new Hand(this.x, this.y + this.originalLen * 2 + 20, false, parent, box2d, rData);
		this.conn1 = new CircleConnector(this.x, this.y + this.originalLen + 10, true, parent, box2d);
		this.conn2 = new CircleConnector(this.x, this.y + this.originalLen + 15, true, parent, box2d);
		this.anchor = new Anchor(getX(), getY(), parent, box2d);
		
		// Import photo
		this.spring_img = parent.loadImage("spring.jpg");
		
		// Define the first spring distance joint
		this.djd1 = new DistanceJointDef();
		
		djd1.bodyA = this.anchor.body;
		// Body 2 is the Hand's object
		djd1.bodyB = this.conn1.body;
		// Get the mouse location in world coordinates
		djd1.collideConnected = true;
		djd1.length = box2d.scalarPixelsToWorld(this.originalLen);
		
		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd1.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/(this.conn1.body.m_mass + this.hand.body.m_mass))));
		djd1.dampingRatio = 0.01f;
		
		// Make the first spring distance joint
		dj1 = (DistanceJoint) box2d.world.createJoint(djd1);
	
		// Define the second spring distance joint
		this.djd2 = new DistanceJointDef();
		
		djd2.bodyA = this.conn2.body;
		// Body 2 is the Hand's object
		djd2.bodyB = this.hand.body;
		// Get the mouse location in world coordinates
		djd2.collideConnected = true;
		djd2.length = box2d.scalarPixelsToWorld(this.originalLen);
		
		// Some stuff about how strong and bouncy the spring should be
		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
		djd2.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/this.hand.body.m_mass)));
		djd2.dampingRatio = 0.01f;
		
		// Make the second spring distance joint
		dj2 = (DistanceJoint) box2d.world.createJoint(djd2);
		
		// Define the revolute joint that connects the two springs
		rjd1 = new RevoluteJointDef();
		rjd1.initialize(this.conn1.body, this.conn2.body, this.conn1.body.getWorldCenter());
		rjd1.lowerAngle = -0.25f * (float) Math.PI; // -45 degrees
		rjd1.upperAngle = 0.25f * (float) Math.PI; // 45 degrees
		rjd1.enableLimit = true;
		rjd1.maxMotorTorque = 10.0f;
		rjd1.motorSpeed = 0.0f;
		rjd1.enableMotor = true;
		rj1 = (RevoluteJoint) box2d.world.createJoint(rjd1);
		
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
			this.anchor.draw();
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
			this.conn2.draw();
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
