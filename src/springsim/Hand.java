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
	Spring touchingSpring; 
	PImage hand_img;
	Body body;
	
	int x;
	int y;
	int w;
	int h;

	public Hand(PApplet p, Box2DProcessing b2, int x, int y){

		//constructor
		this.parent = p;
		this.box2d = b2;
		this.touchingSpring = null;
		this.x = x;
		this.y = y;

		hand_img = p.loadImage("hand.png");
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
	
	public void setW(float scale) {
		this.w *= scale;
		hand_img.resize(this.w, this.h);
	}
	
	public void setH(float scale) {
		this.h *= scale;
		hand_img.resize(this.w, this.h);
	}
	
}
