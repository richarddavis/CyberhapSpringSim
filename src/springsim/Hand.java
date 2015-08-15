package springsim;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import processing.core.PApplet;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class Hand {
	
	PApplet parent;
	Box2DProcessing box2d;
	PImage hand_img;
	Body body;
	
	int x;
	int y;
	int w;
	int h;

	public Hand(int _x, int _y, PApplet p, Box2DProcessing b2){
		
		//constructor
		this.parent = p;

		this.box2d = b2;
		this.x = _x;
		this.y = _y;

		hand_img = p.loadImage("hand.png");
		this.w = hand_img.width;
		this.h = hand_img.height;
		hand_img.resize(hand_img.width/2, hand_img.height/2);
		
		BodyDef bd = new BodyDef();
	    bd.position.set(box2d.coordPixelsToWorld(new Vec2((int) x,(int) y)));
	    bd.type = BodyType.DYNAMIC;
	    bd.fixedRotation = true;
	    
	    body = box2d.createBody(bd);
	    body.setGravityScale(0);
	    
	    PolygonShape sd = new PolygonShape();
	    float box2dW = box2d.scalarPixelsToWorld(w/2);
	    float box2dH = box2d.scalarPixelsToWorld(h/2);
	    sd.setAsBox(box2dW, box2dH);
	    
	    // Define a fixture
	    FixtureDef fd = new FixtureDef();
	    fd.shape = sd;
	    
	    // Parameters that affect physics
	    fd.density = 1f;
	    fd.friction = 0.3f;
	    fd.restitution = 0.5f;
	    
	    body.createFixture(fd);
	}
	
	private double calculateForce(){
		//TODO
		return 0.0;
	}
	
	public void draw() {
		parent.image(hand_img, this.x, this.y);
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
		hand_img.resize(this.w, this.h);
	}
	
	public void setH(double scale) {
		this.h *= scale;
		hand_img.resize(this.w, this.h);
	}
	
}
