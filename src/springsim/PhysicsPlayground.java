package springsim;

import java.awt.Font;

import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.core.PFont;

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
		
		tf1 = cp5.addTextfield("Length")
	      .setPosition(x+140,y+35)
	      .setSize(60,25)
	      .setFocus(false)
	      .setAutoClear(false)
	      ;
		
		tf1.getCaptionLabel().setVisible(false);
		
		tf2 = cp5.addTextfield("CurrentLabel")
			      .setPosition(x+140,y+25+spacing)
			      .setSize(60,25)
			      .setFocus(false)
			      .setAutoClear(false)
			      ;
		
		tf2.getCaptionLabel().setVisible(false);
		
		tf2.setText(c.sc.activeSpring.getLabel());
		tf1.setText(Integer.toString(c.sc.activeSpring.originalLen/Main.SCALE_FACTOR));
		
		cp5.addListener(this);
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw() {

		parent.fill(255);
		parent.rect(x, y, w, h);
		parent.fill(0);
		parent.text("Spring Properties", x+10, y+25);
		
		parent.pushMatrix();
		Font p1 = parent.getFont();
		PFont p2 = parent.createFont("Verdana",12);
		parent.textFont(p2);
		parent.text("LENGTH (CM) = ", x+15, y+spacing+15);
		parent.text("LABEL = ", x+15, y+5+(2*spacing));
		parent.setFont(p1);
		parent.textSize(18);
		parent.popMatrix();

		
	}

	@Override
	public void controlEvent(ControlEvent event) {
		if(event.isFrom(tf1)){
			int len = Integer.parseInt(tf1.getStringValue())*Main.SCALE_FACTOR;
			c.sc.activeSpring.originalLen = len;
			c.sc.activeSpring.getHand().hapkitUpdate(len);
			System.out.println(c.sc.activeSpring.originalLen);
		}else if(event.isFrom(tf2)){
			c.sc.activeSpring.setLabel(tf2.getStringValue());
		}
		
	}

}
