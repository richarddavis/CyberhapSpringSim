package springsim;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class Connector implements PConstants {

	PApplet parent;
	Box2DProcessing box2d;
	PImage connector_img;
	Body body;

	int x;
	int fixed_x;
	int y;
	int w;
	int h;

	public Connector(int _x, int _y, PApplet p, Box2DProcessing b2) {

		//constructor
		this.parent = p;
		this.box2d = b2;
		this.x = _x;
		this.fixed_x = this.x;
		this.y = _y;

		this.connector_img = p.loadImage("connector.png");
		
		this.w = this.connector_img.width / 30;
		this.h = this.connector_img.height / 30;

		this.connector_img.resize(this.w, this.h);

		BodyDef bd = new BodyDef();
		bd.position.set(box2d.coordPixelsToWorld(new Vec2((int) x,(int) y)));
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = false;

		//bd.angularDamping = 0;
		bd.linearDamping = (float) 0.3;

		this.body = box2d.createBody(bd);
		//body.setGravityScale(0);

		CircleShape circle = new CircleShape();

		circle.m_radius = box2d.scalarPixelsToWorld(this.w/2);
		Vec2 offset = new Vec2(0,this.h/2);
	    offset = box2d.vectorPixelsToWorld(offset);
	    circle.m_p.set(offset.x,offset.y);

	    // Define a fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = circle;

		// Parameters that affect physics
		fd.density = 0.7f; 
		fd.friction = 0.01f;
		fd.restitution = 0.9f;
		//fd.setSensor(true);

		this.body.createFixture(fd);
		this.body.resetMassData();
	}

	public void draw() {
		//parent.image(hand_img, this.x, this.y);
		Vec2 pos = this.box2d.getBodyPixelCoord(this.body);
		this.x = (int)pos.x;
		this.y = (int)pos.y;
		parent.imageMode(PConstants.CENTER);
		parent.image(connector_img, this.x, this.y+10);
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

	public void setW(double scale) {
		this.w *= scale;
		connector_img.resize(this.w, this.h);
	}

	public void setH(double scale) {
		this.h *= scale;
		connector_img.resize(this.w, this.h);
	}

	public boolean contains(int x, int y) {
		Vec2 worldPoint = this.box2d.coordPixelsToWorld(x, y);
		Fixture f = this.body.getFixtureList();
		boolean inside = f.testPoint(worldPoint);
		return inside;
	}

}
