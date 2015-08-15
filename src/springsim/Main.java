package springsim;

import processing.core.PApplet;
import processing.serial.Serial;
import shiffman.box2d.Box2DProcessing;

public class Main extends PApplet {

	Box2DProcessing box2d;
	Spring s1;
	SpringCollection sc;
	Hand hand;
	
	//serialports 
	// Arduino board serial port index, machine-dependent:
	int serialPortIndex = 0;
	int SERIAL_WRITE_LENGTH = 32;
	Serial myPort;
	
	public void setup() {
		box2d = new Box2DProcessing(this);
		box2d.createWorld();
		
		// Initialize Serial Comms
		myPort = new Serial(this, Serial.list()[0], 9600); 
		myPort.bufferUntil('\n');

		s1 = new Spring(100, 100, 40, 200, this, box2d);
		s1.bind(mouseX, mouseY);
		
		sc = new SpringCollection();
		sc.add(s1);
		sc.setActive(s1);
		
		sc.activeSpring.update(sc.activeSpring.x, mouseY);
		
		size(1000,800);
		background(100, 100, 100);
	}

	public void draw() {
		stroke(255);
//		if (mousePressed) {
//			line(mouseX,mouseY,pmouseX,pmouseY);
//			hand.setH(0.5);
//			hand.setW(0.5);
//		}
		sc.draw();
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
