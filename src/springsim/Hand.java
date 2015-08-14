package springsim;

import processing.core.PApplet;
import processing.core.PImage;

public class Hand {
	
	Spring touchingSpring; 
	int x;
	int y;
	PImage hand_img;
	PApplet parent;

	public Hand(PApplet p, int x, int y){
		//constructor
		this.touchingSpring = null;
		this.x = x;
		this.y = y;
		this.parent = p;
		hand_img = p.loadImage("hand.png");
		hand_img.resize(hand_img.width/2, hand_img.height/2);
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
	
}
