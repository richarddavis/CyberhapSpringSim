package springsim;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import controlP5.ControlP5;
import processing.core.PApplet;

public class Ruler implements MouseListener, MouseMotionListener {

	PApplet p;
	int x;
	int y;
	int w;
	int h;
	int distance; //centimeters
	private boolean locked;
	private int orig_x;
	private int orig_y;
	private int diff_x;
	private int diff_y;
	
	public Ruler(PApplet p, ControlP5 cp5, int x, int y, int w, int h, int distance){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.distance = distance;
		this.p = p;
		p.addMouseListener(this);
		p.addMouseMotionListener(this);
		
	}

	public void draw() {
		p.rect(x, y, w, h+20);
		
		int spacing = h / distance;
		
		for(int i=0; i<=distance; i++){
			p.line(this.x+(this.w/2), this.y+10+(i*spacing), this.x+this.w, this.y+10+(i*spacing));
			p.fill(50);
			p.text(i, this.x+1, this.y+15+(i*spacing));
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		  if (onRuler(e)) { 
			    locked = true;
				diff_x = this.x - e.getX();
				diff_y = this.y - e.getY();
			  } 
			  else {
			    locked = false;
			  }
	}

	private boolean onRuler(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(mx > this.x
				&& mx < this.x+this.w
				&& my > this.y
				&& my < this.y+this.h){
			return true;
		}
		
		return false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		locked = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		 if (locked) {
			    this.x = p.mouseX+diff_x; 
			    this.y = p.mouseY+diff_y;
			  }
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(onRuler(e)){
			p.cursor(p.HAND);
		}else{
			p.cursor(0);
		}
		
	}
	
}
