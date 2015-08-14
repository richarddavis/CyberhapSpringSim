package springsim;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Spring {

	int x;
	int y;
	int currentLen;
	int originalLen;
	int k;
	
	//BOX2D
	MouseJoint mouseJoint;
	Box2DProcessing box2d;
	PApplet parent;
	
	public Spring(int _x, int _y, int _k, int _length, PApplet p, Box2DProcessing b2){
		x = _x;
		y = _y;
		k = _k;
		originalLen = _length;
		parent = p;
	    mouseJoint = null;
	    box2d = b2;
	}
	
	void draw(){
		
		parent.line(this.x, this.y, this.x, (float)(this.y + originalLen));
		
		if (mouseJoint != null) {
			// We can get the two anchor points
			Vec2 v1 = new Vec2(0,0);
			mouseJoint.getAnchorA(v1);
			Vec2 v2 = new Vec2(0,0);
			mouseJoint.getAnchorB(v2);
			
			// Convert them to screen coordinates
			v1 = box2d.coordWorldToPixels(v1);
			v2 = box2d.coordWorldToPixels(v2);
			
			// And just draw a line
			parent.stroke(0);
			parent.strokeWeight(1);
			parent.line(v1.x,v1.y,v2.x,v2.y);
		}
	}
	
	// If it exists we set its target to the mouse location 
	void update(float x, float y) {
		if (mouseJoint != null) {
			// Always convert to world coordinates!
			Vec2 mouseWorld = box2d.coordPixelsToWorld(x,y);
			mouseJoint.setTarget(mouseWorld);
	    }	
	}

	// This is the key function where
	// we attach the spring to an x,y location
	// and the Box object's location
	void bind(float x, float y, Hand hand) {
		// Define the joint
		MouseJointDef md = new MouseJointDef();
		
		// Body A is just a fake ground body for simplicity (there isn't anything at the mouse)
		md.bodyA = box2d.getGroundBody();
		// Body 2 is the Hand's object
		md.bodyB = hand.body;
		// Get the mouse location in world coordinates
		Vec2 mp = box2d.coordPixelsToWorld(x,y);
		// And that's the target
		md.target.set(mp);
		// Some stuff about how strong and bouncy the spring should be
		md.maxForce = (float) (1000.0 * hand.body.m_mass);
		md.frequencyHz = 1000.0f;
		md.dampingRatio = 0.0001f;
		
		// Make the joint
		mouseJoint = (MouseJoint) box2d.world.createJoint(md);
	}
	
	//TODO: Consider moving this with hapkit incorporation
	void destroy() {
	    // We can get rid of the joint when the mouse is released
	    if (mouseJoint != null) {
	    	box2d.world.destroyJoint(mouseJoint);
	    	mouseJoint = null;
	    }
	}
	
}
