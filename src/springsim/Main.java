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
		
		hand = new Hand(this, box2d, 300, 300);

		s1 = new Spring(100, 100, 40, 200, this, box2d);
		s1.bind(hand.getX(), hand.getY(), hand);
		
		sc = new SpringCollection();
		sc.add(s1);
		
		size(1000,800);
		background(100, 100, 100);
	}

	public void draw() {
		stroke(255);
		if (mousePressed) {
			line(mouseX,mouseY,pmouseX,pmouseY);
			hand.setH(0.5);
			hand.setW(0.5);
		}
		sc.draw(mouseX, mouseY);
		hand.draw();
	}
	
}
