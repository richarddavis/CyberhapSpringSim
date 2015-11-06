package springsim;

import controlP5.Chart;
import controlP5.ControlP5;
import processing.core.PApplet;

public class ForceDisplayOutput implements Component {

	int x;
	int y;
	int w;
	int h;
	
	PApplet parent;
	Chart myChart;
	
	public ForceDisplayOutput(Main main, ControlP5 cp5, int _x, int _y, int _w, int _h) {
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		parent = main;
		
		myChart = cp5.addChart("hello")
	               .setPosition(this.x+10, this.y+10)
	               .setSize(this.w-20, this.h-20)
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
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		int xRect = x+(w/2);
		int yRect = y+(h/2);
		
		parent.fill(255);
		parent.rect(xRect, yRect, w, h);
		parent.fill(0);
		parent.text("Force Display Output", x, y);
		
		generateGraph();
	}

	private void generateGraph() {
		// TODO Auto-generated method stub
		
	}

}
