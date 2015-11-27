package springsim;

import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.PApplet;

public class PhysicsPlayground implements Component{

	int x;
	int y;
	int w;
	int h;
	
	PApplet parent;
	Canvas c; 
	
	Textfield tf1;
	Textfield tf2;
	Textfield tf3;
	
	int spacing = 40; 
	
	public PhysicsPlayground(Main main, ControlP5 cp5, Canvas _c, int _x, int _y, int _w, int _h) {
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		parent = main;
		this.c = _c;
		
		tf1 = cp5.addTextfield("MassInput")
	      .setPosition(x+60,y+25)
	      .setSize(60,25)
	      .setFocus(true)
	      ;
		
		tf2 = cp5.addTextfield("KConstantInput")
			      .setPosition(x+140,y+25+spacing)
			      .setSize(60,25)
			      .setFocus(true)
			      ;
		
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		tf2.setText(Integer.toString(c.sc.activeSpring.getK()));
	}

	@Override
	public void draw() {
		int xRect = x+(w/2);
		int yRect = y+(h/2);
		
		parent.fill(255);
		parent.rect(xRect, yRect, w, h);
		parent.fill(0);
		parent.text("Spring Properties", x+10, y+15);
		
		parent.text("mass = ", x+10, y+spacing);
		
		parent.text("spring stiffness (K) = ", x+10, y+(2*spacing));
		
	}

}
