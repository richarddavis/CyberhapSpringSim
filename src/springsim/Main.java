package springsim;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Main extends PApplet {
	
	Box2DProcessing box2d;
	Spring s1;
	SpringCollection sc;
	Hand hand;
	
	public void setup() {
		box2d = new Box2DProcessing(this);
		box2d.createWorld();
		
		hand = new Hand();

		s1 = new Spring(100, 100, 40, 200, this, box2d);
		s1.bind(hand.getX(), hand.getY(), hand);
		
		sc = new SpringCollection();
		
		size(1000,800);
		background(255);
	}

	public void draw() {
		stroke(255);
		if (mousePressed) {
			line(mouseX,mouseY,pmouseX,pmouseY);
		}
		sc.draw();
	}
	
}
