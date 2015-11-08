package springsim;

import processing.core.PApplet;

public class Ruler {

	PApplet p;
	int x;
	int y;
	int w;
	int h;
	int distance; //centimeters
	
	public Ruler(PApplet p, int x, int y, int w, int h, int distance){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.distance = distance;
		this.p = p;
		
		p.rect(x+(w/2), y+(h/2), w, h+20);
		
		int spacing = h / distance;
		
		for(int i=0; i<distance; i++){
			p.line(this.x+(this.w/2), this.y+10+(i*spacing), this.x+this.w, this.y+10+(i*spacing));
			p.fill(50);
			p.text(i, this.x+1, this.y+15+(i*spacing));
		}
		
	}
	
}
