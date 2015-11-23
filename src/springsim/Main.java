package springsim;

import java.util.Random;

import processing.core.PApplet;
import processing.serial.Serial;
import shiffman.box2d.Box2DProcessing;

public class Main extends PApplet {

	Box2DProcessing box2d;
	SerialSpring s1;
	ComboSpring s2;
	ParallelSpring s3;
	Spring s4;
	SpringCollection sc;
	WeightCollection wc;

	Hand hand;
	Boundary ceiling;
	Boundary floor;
	Weight weight;
	
	//serialports 
	// Arduino board serial port index, machine-dependent:
	int serialPortIndex = 0;
	int SERIAL_WRITE_LENGTH = 32;
	Serial myPort;
	
	public void setup() {
		size(500, 800);
		background(255);
		
		box2d = new Box2DProcessing(this);
		box2d.createWorld();
		box2d.setScaleFactor(500);
		box2d.setGravity(0, -2);
		
		// This prevents dynamic bodies from sticking to static ones
		org.jbox2d.common.Settings.velocityThreshold = 0.2f;
		
		// Initialize Serial Comms
		//myPort = new Serial(this, Serial.list()[0], 9600); 
		//myPort.bufferUntil('\n');
		
		wc = new WeightCollection();
		Random rg = new Random();
		
		for (int i = 0; i < 20; i++) {
			weight = new Weight((int) rg.nextGaussian() + this.width/2, 50 + rg.nextInt(10), this, box2d);
			wc.add(weight);
		}

		s1 = new SerialSpring(50, 100, 30, 100, this, box2d);
		s2 = new ComboSpring(150, 100, 30, 100, this, box2d);
		s3 = new ParallelSpring(300, 100, 30, 100, this, box2d);
		s4 = new Spring(400, 100, 30, 100, this, box2d);
		
		sc = new SpringCollection();
		sc.add(s1);
		sc.add(s2);
		sc.add(s3);
		sc.add(s4);
		sc.setActive(s1);
		
		floor = new Boundary(this.width/2, this.height - 20, this.width - 20, 20, this, box2d);
		ceiling = new Boundary(10, 10, this.width - 20, 20, this, box2d);
	}

	public void draw() {
		background(255);
		this.box2d.step();
		stroke(255);

		sc.draw();
		floor.draw();
		wc.draw();
		//ceiling.draw();
	}

	
	public void mousePressed() {
		sc.updateActive(this.mouseX, this.mouseY, true);
	}
	
	public void mouseReleased() {
		sc.updateActive(this.mouseX, this.mouseY, false);
	}
	
//	/**
//	* TODO: Document
//	*/
//	public void serialEvent(Serial port) {
//		String inString = "";
//		
//		while(myPort.available() > 0)
//		{
//		  inString = myPort.readStringUntil('\n');
//		}
//		
//		if (inString != null)
//		{
//		   try {
//		    
//		    String[] list = split(inString, ',');
//		    
//		    // trim off whitespaces.
//			String xString = trim(list[0]); 
//		 
//			// convert to a number.
//		    int xByte = Integer.valueOf(xString);  
//
//		    if(!Float.isNaN(xByte) && xByte != 0){
//		      double inputX = xByte;
//		      
//		      sc.activeSpring.update(sc.activeSpring.x, xByte);
//		      // TODO: Set spring X value to be target of virtual spring?
//		      //sector_pulley_position = updated_x;
//		      // Spring.update() here or similar... 
//		    }       
//		    
//		   } catch (Exception e) {}
//		
//		 }
//	}
	
}
