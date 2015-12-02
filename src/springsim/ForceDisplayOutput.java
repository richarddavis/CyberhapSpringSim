package springsim;

import java.util.ArrayList;

import controlP5.Button;
import controlP5.Chart;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.PApplet;

public class ForceDisplayOutput extends Component {

	private ArrayList<int[]> data;
	
	PApplet parent;
	Chart myChart;
	
	Textfield spring; 
	Textfield distance;
	Textfield force;
	Button add;
	
	
	public ForceDisplayOutput(Main main, ControlP5 cp5, int _x, int _y, int _w, int _h) {
		super(_x,_y,_w,_h);
		parent = main;
		
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
