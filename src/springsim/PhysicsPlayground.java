package springsim;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.PApplet;

public class PhysicsPlayground extends Component{

	PApplet parent;
	Canvas c; 
	
	Textfield tf1;
	Textfield tf2;
	Textfield tf3;
	
	int spacing = 40; 
	
	public PhysicsPlayground(Main main, ControlP5 cp5, Canvas _c, int _x, int _y, int _w, int _h) {
		super(_x,_y,_w,_h);
		parent = main;
		this.c = _c;
		
		tf1 = cp5.addTextfield("MassInput")
	      .setPosition(x+60,y+25)
	      .setSize(60,25)
	      .setFocus(true)
	      ;
		
		tf1.getCaptionLabel().setVisible(false);
		
		tf2 = cp5.addTextfield("KConstantInput")
			      .setPosition(x+140,y+25+spacing)
			      .setSize(60,25)
			      .setFocus(true)
			      ;
		
		tf2.getCaptionLabel().setVisible(false);
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		tf2.setText(Integer.toString(c.sc.activeSpring.getK()));
	}

	@Override
	public void draw() {

		parent.fill(255);
		parent.rect(x, y, w, h);
		parent.fill(0);
		parent.text("Spring Properties", x+10, y+15);
		parent.text("mass = ", x+10, y+spacing);
		parent.text("spring stiffness (K) = ", x+10, y+(2*spacing));
		
	}

	@Override
	public void controlEvent(ControlEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
