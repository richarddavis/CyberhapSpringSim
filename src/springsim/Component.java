package springsim;

import controlP5.ControlListener;

public abstract class Component implements ControlListener{
	
	int x;
	int y;
	int w;
	int h;
	
	public Component(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
	}

	public void controlEvent(){
		System.out.println("Override controlEvent");
	}
	
	public abstract void step();
	public abstract void draw();
	
}
