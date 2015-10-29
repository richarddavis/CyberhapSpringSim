package springsim;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import processing.core.PApplet;
import processing.core.PConstants;
import shiffman.box2d.Box2DProcessing;

public class Boundary implements PConstants {

	// A boundary is a simple rectangle with x,y,width,and height
	int x;
	int y;
	int w;
	int h;
	PApplet parent;
	Box2DProcessing box2d;

	// But we also have to make a body for box2d to know about it
	Body body;

	Boundary(int x_,int y_, int w_, int h_,PApplet p, Box2DProcessing b2) {

		parent = p;
		box2d = b2;
		x = x_;
		y = y_;
		w = w_;
		h = h_;

		// Create the body
		BodyDef bd = new BodyDef();
		bd.position.set(box2d.coordPixelsToWorld(x,y));
		bd.type = BodyType.STATIC;	
		this.body = box2d.createBody(bd);

		// Define the polygon
		PolygonShape sd = new PolygonShape();
		// Figure out the box2d coordinates
		float box2dW = box2d.scalarPixelsToWorld(w/2);
		float box2dH = box2d.scalarPixelsToWorld(h/2);
		// We're just a box
		sd.setAsBox(box2dW, box2dH);

		// Attached the shape to the body using a Fixture
		this.body.createFixture(sd,1);
	}

	// Draw the boundary, if it were at an angle we'd have to do something fancier
	void draw() {
		Vec2 pos = this.box2d.getBodyPixelCoord(this.body);
		this.x = (int)pos.x;
		this.y = (int)pos.y;
		parent.fill(255);
		parent.stroke(0);
		parent.rectMode(PConstants.CENTER);
		parent.rect(this.x,this.y,w,h);
	}
}
