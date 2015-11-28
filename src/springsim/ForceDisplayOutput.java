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
		
		data = new ArrayList<int[]>();
		
		// Generate Chart 
		// TODO: consider having graph be separate class
		myChart = cp5.addChart("hello")
	               .setPosition(this.x+10, this.y+10)
	               .setSize(this.w-20, 180)
	               .setRange(-20, 20)
	               .setView(Chart.LINE) // use Chart.LINE, Chart.PIE, Chart.AREA, Chart.BAR_CENTERED
	               .setValueLabel("Force/Displacement")
	               .setLabelVisible(true) 
	               ;
		
		  myChart.getColor().setBackground(parent.color(120, 100));

		  myChart.addDataSet("world");
		  myChart.setColors("world", parent.color(255,0,255),parent.color(255,0,0));
		  myChart.setData("world", new float[4]);

		  myChart.setStrokeWeight((float) 0);

		  myChart.addDataSet("earth");
		  myChart.setColors("earth", parent.color(255), parent.color(0, 255, 0));
		  myChart.updateData("earth", 1, 2, 10, 3);
		  
		  parent.fill(50);
		  // Add data entry method
		  spring = cp5.addTextfield("SpringInput")
				      .setPosition(x+10,y+200)
				      .setSize(40,25)
				      .setFocus(true)
				      ;
		  
		  force = cp5.addTextfield("forceInput")
			      .setPosition(x+70,y+200)
			      .setSize(40,25)
			      .setFocus(true)
			      ;
		  
		  distance = cp5.addTextfield("distanceInput")
			      .setPosition(x+130,y+200)
			      .setSize(40,25)
			      .setFocus(true)
			      ;
		  
		  add = cp5.addButton("ADD")
				     .setValue(1)
				     .setPosition(x+180,y+200)
				     .setSize(30,30)
				     .setId(1);
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
		parent.text("Force Display Output", x, y);
		generateGraph();
	}

	private void generateGraph() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlEvent(ControlEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
