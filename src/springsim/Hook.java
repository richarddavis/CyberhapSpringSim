package springsim;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

import processing.core.PApplet;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class Hook {

	PApplet parent;
	Box2DProcessing box2d;
	Body body;
	MouseJoint mj;

	int x;
	int y;
	int w;
	int h;
	float mass;

	public Hook(int _x, int _y, float mass, PApplet p, Box2DProcessing b2){

		//constructor
		this.parent = p;
		this.box2d = b2;
		this.x = _x;
		this.y = _y;
		this.mass = mass;

		this.w = 25;
		this.h = 25;

		BodyDef bd = new BodyDef();
		bd.position.set(box2d.coordPixelsToWorld(new Vec2((int) x,(int) y)));
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = true;

		//bd.angularDamping = 0;
		bd.linearDamping = (float) 0.3;

		this.body = box2d.createBody(bd);
		//body.setGravityScale(0);

		PolygonShape sd = new PolygonShape();

		float box2dW = box2d.scalarPixelsToWorld(this.w/2);
		float box2dH = box2d.scalarPixelsToWorld(this.h/2);

		sd.setAsBox(box2dW, box2dH);

		// Define a fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = sd;

		// Parameters that affect physics
		fd.density = 0.7f; 
		fd.friction = 0.01f;
		fd.restitution = 0.9f;
		//fd.setSensor(true);

		this.body.createFixture(fd);
		this.body.resetMassData();
	}

	public void draw() {
		if (mj != null) {

			this.mousePosUpdate(parent.mouseX, parent.mouseY);
			
			// We can get the two anchor points
			Vec2 v1 = new Vec2(0,0);
			mj.getAnchorA(v1);
			Vec2 v2 = new Vec2(0,0);
			mj.getAnchorB(v2);
			// Convert them to screen coordinates
			v1 = box2d.coordWorldToPixels(v1);
			v2 = box2d.coordWorldToPixels(v2);
			// And just draw a line
			parent.stroke(0);
			parent.strokeWeight(1);
			//parent.line(v1.x,v1.y,v2.x,v2.y);
		}

		//parent.image(hand_img, this.x, this.y);
		Vec2 pos = this.box2d.getBodyPixelCoord(this.body);
		this.x = (int)pos.x;
		this.y = (int)pos.y;
		parent.rect(this.x,  this.y,  this.w,  this.h);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void bind(float mx, float my) {
		MouseJointDef mjd = new MouseJointDef();
		mjd.bodyA = box2d.getGroundBody();
		mjd.bodyB = this.body;
		Vec2 mp = this.box2d.coordPixelsToWorld(mx, my);
		mjd.target.set(mp);

		mjd.frequencyHz = 3000;
		mjd.dampingRatio = (float) 0.1;
	    mjd.maxForce = (float) (10000.0 * this.body.m_mass);

		this.mj = (MouseJoint) box2d.world.createJoint(mjd);
	}

	public void mouseUpdate(int mx, int my, boolean pressed) {
		if (pressed == false) {
			this.destroy();
		} else if (this.contains(mx, my)) {
			this.bind(mx, my);
		}
	}
	
	public void mousePosUpdate(int mx, int my) {
		// Update the position
		Vec2 mouseWorld = box2d.coordPixelsToWorld(mx,my);
		this.mj.setTarget(mouseWorld);
	}

	public void destroy() {
		// We can get rid of the joint when the mouse is released
		if (this.mj != null) {
			box2d.world.destroyJoint(this.mj);
			this.mj = null;
			System.out.println("Mouse Joint Destroyed");
		}
	}

	public boolean contains(int x, int y) {
		Vec2 worldPoint = this.box2d.coordPixelsToWorld(x, y);
		Fixture f = this.body.getFixtureList();
		boolean inside = f.testPoint(worldPoint);
		return inside;
	}

}
