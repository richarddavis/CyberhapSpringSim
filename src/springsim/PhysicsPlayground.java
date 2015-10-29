package springsim;

import processing.core.PApplet;

public class PhysicsPlayground implements Component{

	int x;
	int y;
	int w;
	int h;
	
	PApplet parent;
	
	public PhysicsPlayground(Main main, int _x, int _y, int _w, int _h) {
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		parent = main;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		int xRect = x+(w/2);
		int yRect = y+(h/2);
		
		parent.fill(255);
		parent.rect(xRect, yRect, w, h);
		parent.fill(0);
		parent.text("PhysicsPlayground", x, y);
		
	}

}
