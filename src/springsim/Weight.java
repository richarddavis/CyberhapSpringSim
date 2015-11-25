package springsim;

import org.jbox2d.collision.shapes.CircleShape;
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
import processing.core.PConstants;
import processing.core.PImage;
import processing.event.MouseEvent;
import shiffman.box2d.Box2DProcessing;

public class Weight {

	PApplet parent;
	Box2DProcessing box2d;
	Body body;
	MouseJoint mj;

	int x;
	int y;
	int w;
	int h;

	public Weight(int _x, int _y, PApplet p, Box2DProcessing b2){

		//constructor
		this.parent = p;
		this.box2d = b2;
		this.x = _x;
		this.y = _y;

		// Even though the shape is a circle, keeping the width and height.
		// The plan is eventually to create an abstract interface for all objects in the world.
		// Maybe a bad plan but let's see if it will work.
		this.w = 20;
		this.h = 20;
		
		this.parent.registerMethod("mouseEvent", this);

		//BodyDef bd = this.createBody();
		BodyDef bd = new BodyDef();
		bd.position.set(box2d.coordPixelsToWorld(new Vec2(this.x, this.y)));
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = false;
		bd.angularDamping = (float) 0.1;
		bd.linearDamping = (float) 0.1;
		this.body = box2d.createBody(bd);
		//this.body.setBullet(true);
		//body.setGravityScale(0);

		CircleShape cs = new CircleShape();
		cs.m_radius = box2d.scalarPixelsToWorld(this.w/2);

		// Define a fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;

		// Parameters that affect physics
		fd.density = 20f; 
		fd.friction = 1f;
		fd.restitution = 0.3f;

		this.body.createFixture(fd);
		this.body.resetMassData();
	}

	public void draw() {
		//parent.image(hand_img, this.x, this.y);
		Vec2 windVec = new Vec2(.001f, 0);
		this.body.applyForce(windVec, this.body.getWorldCenter());
		Vec2 pos = this.box2d.getBodyPixelCoord(this.body);
		this.x = (int)pos.x;
		this.y = (int)pos.y;
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(this.x, this.y, this.w, this.h);
	}

//	public void mouseEvent(MouseEvent event) {
//		int x = event.getX();
//		int y = event.getY();
//
//		switch (event.getAction()) {
//		case MouseEvent.PRESS:
//			// do something for the mouse being pressed
//			System.out.println("PRESS");
//			System.out.print(x);
//			System.out.print(", ");
//			System.out.println(y);
//			break;
//		case MouseEvent.RELEASE:
//			// do something for mouse released
//			System.out.println("RELEASE");
//			break;
//		case MouseEvent.CLICK:
//			// do something for mouse clicked
//			System.out.println("CLICK");
//			break;
//		case MouseEvent.DRAG:
//			// do something for mouse dragged
//			System.out.println("DRAG");
//			break;
//		case MouseEvent.MOVE:
//			// umm... forgot
//			System.out.println("MOVE");
//			break;
//		}
//	}

	//	public BodyDef createBody() {
	//		BodyDef bd = this.createBody();
	//		bd.position.set(box2d.coordPixelsToWorld(new Vec2(this.x, this.y)));
	//		bd.type = BodyType.DYNAMIC;
	//		bd.fixedRotation = false;
	//		bd.angularDamping = (float) 0.1;
	//		bd.linearDamping = (float) 0.3;
	//		return bd;
	//	}

}
