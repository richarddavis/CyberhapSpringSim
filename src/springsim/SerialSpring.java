package springsim;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

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
	RevoluteJoint rj1;
	
	Anchor anchor;
	CircleConnector conn1;
	CircleConnector conn2;
	PImage spring_img;
	
	int fixed_x;
	private WeldJointDef wjd;
	private Joint wj;
	private PrismaticJointDef pjd;
	private PrismaticJoint pj;
	private PrismaticJointDef pjd1;
	private PrismaticJoint pj1;

	public SerialSpring(int _x, int _y, int _k, int _length, String label, PApplet p, Box2DProcessing b2, ResearchData rData){
		super(_x, _y, _k, _length, label, p, b2, rData);
		
		this.hand = new Hand(this.x, this.y + this.originalLen * 2 + 20, false, parent, box2d, rData);
		this.conn1 = new CircleConnector(this.x, this.y + this.originalLen + 10, true, parent, box2d);
		this.conn2 = new CircleConnector(this.x, this.y + this.originalLen + 15, true, parent, box2d);
		this.anchor = new Anchor(getX(), getY(), parent, box2d);
		this.fixed_x = _x;
		
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
		
//		// Make the first spring distance joint
//		dj1 = (DistanceJoint) box2d.world.createJoint(djd1);
//	
//		// Define the second spring distance joint
//		this.djd2 = new DistanceJointDef();
//		
//		djd2.bodyA = this.conn2.body;
//		// Body 2 is the Hand's object
//		djd2.bodyB = this.hand.body;
//		// Get the mouse location in world coordinates
//		djd2.collideConnected = true;
//		djd2.length = box2d.scalarPixelsToWorld(this.originalLen);
//		
//		// Some stuff about how strong and bouncy the spring should be
//		//djd.maxForce = (float) (1000.0 * hand.body.m_mass);
//		djd2.frequencyHz = (float) ((1 / (2 * Math.PI)) * (Math.sqrt(this.k/this.hand.body.m_mass)));
//		djd2.dampingRatio = 0.01f;
//		
//		// Make the second spring distance joint
//		dj2 = (DistanceJoint) box2d.world.createJoint(djd2);
		
		pjd1 = new PrismaticJointDef();
		pjd1.bodyA = conn2.body;
		pjd1.bodyB = hand.body;
		pjd1.collideConnected = true;
		pj1 = (PrismaticJoint) box2d.world.createJoint(pjd1);
		
		float pjt = pj1.getJointTranslation();
		float pjs = pj1.getJointSpeed();
		
		pj1.setMaxMotorForce(Math.abs((pjt * 100) + (pjs * 10))); // 100 is the spring constant, 10 is the damping constant.
		pj1.setMotorSpeed(pjt > 0 ? -10000 : +10000); // Arbitrary humongous number.
		
//		// Define the revolute joint that connects the two springs
//		rjd1 = new RevoluteJointDef();
//		rjd1.initialize(this.conn1.body, this.conn2.body, this.conn1.body.getWorldCenter());
//		rjd1.lowerAngle = -0.25f * (float) Math.PI; // -45 degrees
//		rjd1.upperAngle = 0.25f * (float) Math.PI; // 45 degrees
//		rjd1.enableLimit = true;
//		rjd1.maxMotorTorque = 10.0f;
//		rjd1.motorSpeed = 0.0f;
//		rjd1.enableMotor = false;
//		rj1 = (RevoluteJoint) box2d.world.createJoint(rjd1);
		
		pjd = new PrismaticJointDef();
		pjd.bodyA = conn1.body;
		pjd.bodyB = conn2.body;
		pjd.lowerTranslation = 0;
		pjd.upperTranslation = 0;
		pjd.enableLimit = true;
		pjd.enableMotor = false;
//		wjd.type = JointType.WeldJoint;
		pjd.collideConnected = true;
		pj = (PrismaticJoint) box2d.world.createJoint(pjd);
		
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
