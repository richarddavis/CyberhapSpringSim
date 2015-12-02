package springsim;

import java.util.ArrayList;

import controlP5.Button;
import controlP5.Chart;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.RadioButton;
import controlP5.Textfield;
import processing.core.PApplet;

public class SpringFactory extends Component {

	private ArrayList<int[]> data;
	
	PApplet parent;
	Chart myChart;
	
	Textfield stiffness; 
	Textfield length;
	Textfield label;
	Button add;
	RadioButton springType;
	
	int spacing = 40;

	Spring s;
	ParallelSpring ps;
	
	public SpringFactory(Main main, ControlP5 cp5, int _x, int _y, int _w, int _h) {
		super(_x,_y,_w,_h);
		parent = main;
		
		stiffness =  cp5.addTextfield("Stiffness")
			      .setPosition(x+145,y+50+spacing)
			      .setSize(60,25)
			      .setFocus(false)
			      .setValue("25");
			      ;
		
		length =  cp5.addTextfield("Length")
			      .setPosition(x+145,y+50+(spacing*2))
			      .setSize(60,25)
			      .setFocus(false)
			      .setValue("10");
			      ;
		
		label =  cp5.addTextfield("Label")
			      .setPosition(x+145,y+50+(spacing*3))
			      .setSize(60,25)
			      .setFocus(false)
			      .setValue("Spring X");
			      ;
		
		add = cp5.addButton("Add to Canvas")
			     .setValue(1)
			     .setPosition(x+50,y+250+(spacing*3))
			     .setSize(110,20)
			     .setId(1);
		
		springType = cp5.addRadioButton("springType")
				        .setPosition(x+10,y+35)
				        .setSize(40,20)
				        .setColorForeground(parent.color(120))
				        .setColorActive(parent.color(200))
				        .setColorLabel(parent.color(0))
				        .setItemsPerRow(1)
				        .setSpacingColumn(50)
				        .addItem("Regular Spring",0)
				        .addItem("Parallel Spring",1)
				        //.addItem("Serial Spring",2)
				        .activate(0);
		
		cp5.addListener(this);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @param spring - the index of spring that the data applies to
	 * @param x - displacement (m)
	 * @param y - force (n)
	 */
	public void addDataPlot(int spring, int x, int y){
		data.add(new int[]{spring,x,y});
	}
	
	
	@Override
	public void draw() {

		parent.fill(255);
		parent.rect(x, y, w, h);
		parent.fill(0);
		parent.text("Spring Factory", x+10, y+25);
		
		
	}


	@Override
	public void controlEvent(ControlEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
