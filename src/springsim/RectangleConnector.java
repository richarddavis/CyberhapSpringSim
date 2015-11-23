package springsim;

import org.jbox2d.collision.shapes.PolygonShape;
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

public class RectangleConnector implements PConstants {

	PApplet parent;
	Box2DProcessing box2d;
	PImage connector_img;
	Body body;

	int x;
	int fixed_x;
	int y;
	int w;
	int h;

	public RectangleConnector(int _x, int _y, boolean _dynamic, PApplet p, Box2DProcessing b2) {

		//constructor
		this.parent = p;
		this.box2d = b2;
		this.x = _x;
		this.fixed_x = this.x;
		this.y = _y;
		this.w = 50;
		this.h = 20;

		BodyDef bd = new BodyDef();
		bd.position.set(box2d.coordPixelsToWorld(new Vec2((int) this.x,(int) this.y)));
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = false;

		this.body = box2d.createBody(bd);

		PolygonShape sd = new PolygonShape();
		float box2dW = box2d.scalarPixelsToWorld(this.w/2);
		float box2dH = box2d.scalarPixelsToWorld(this.h/2);
		sd.setAsBox(box2dW, box2dH);

		// Define a fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = sd;

		// Parameters that affect physics
		fd.density = 1f;
		fd.friction = 0.3f;
		fd.restitution = 0.1f;

		this.body.createFixture(fd);
		this.body.resetMassData();
	}

	public void draw() {
		Vec2 pos = this.box2d.getBodyPixelCoord(this.body);
		this.x = (int)pos.x;
		this.y = (int)pos.y;
		parent.fill(255);
		parent.stroke(0);
		parent.strokeWeight(1);
		parent.rect(this.x, this.y, this.w, this.h);
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
	}

	public void setH(double scale) {
		this.h *= scale;
	}

	public boolean contains(int x, int y) {
		Vec2 worldPoint = this.box2d.coordPixelsToWorld(x, y);
		Fixture f = this.body.getFixtureList();
		boolean inside = f.testPoint(worldPoint);
		return inside;
	}

}
