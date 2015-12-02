package springsim;

import controlP5.Button;
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
	private Button submit; 
	
	public PhysicsPlayground(Main main, ControlP5 cp5, Canvas _c, int _x, int _y, int _w, int _h) {
		super(_x,_y,_w,_h);
		parent = main;
		this.c = _c;
		
		tf1 = cp5.addTextfield("MassInput")
	      .setPosition(x+125,y+35)
	      .setSize(60,25)
	      .setFocus(true)
	      ;
		
		tf1.getCaptionLabel().setVisible(false);
		
		tf2 = cp5.addTextfield("KConstantInput")
			      .setPosition(x+145,y+25+spacing)
			      .setSize(60,25)
			      .setFocus(true)
			      ;
		
		tf2.getCaptionLabel().setVisible(false);
		
		submit = cp5.addButton("Update")
				     .setValue(1)
				     .setPosition(x+147,y+100)
				     .setSize(60,20)
				     .setId(1);
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
		parent.text("Spring Properties", x+10, y+25);
		parent.text("mass (Kg) = ", x+15, y+spacing+15);
		parent.text("stiffness (K) = ", x+15, y+5+(2*spacing));
		
	}

	@Override
	public void controlEvent(ControlEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
