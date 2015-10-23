package springsim;

import java.util.Random;

//import jssc.SerialPort;
//import jssc.SerialPortEvent;
//import jssc.SerialPortEventListener;
//import jssc.SerialPortException;
import processing.core.PApplet;
import processing.serial.Serial;
import shiffman.box2d.Box2DProcessing;

public class Main extends PApplet {

	Box2DProcessing box2d;
	NewSpring s1;
	NewSpring s2;
	NewSpring s3;
	SpringCollection sc;
	//WeightCollection wc;

	Hand hand;
	Boundary ceiling;
	Boundary floor;
	//Weight weight;
	
	//Serial Data
	int serialPortIndex = 7;
	SerialComm serialData;
	double hapkitPos;
	
	int step;
	
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
		serialData = new SerialComm(this, Serial.list(), serialPortIndex);
		
// Ignore weights for now (study1)
//		wc = new WeightCollection();
//		Random rg = new Random();
//		
//		for (int i = 0; i < 20; i++) {
//			weight = new Weight((int) rg.nextGaussian() + this.width/2, 50 + rg.nextInt(10), this, box2d);
//			wc.add(weight);
//		}

		s1 = new NewSpring(100, 100, 10, 100, this, box2d);
		s2 = new NewSpring(200, 100, 40, 100, this, box2d);
		s3 = new NewSpring(300, 100, 70, 100, this, box2d);
		
		sc = new SpringCollection();
		sc.add(s1);
		sc.add(s2);
		sc.add(s3);
		
		//set initial active spring
		sc.setActive(s1);
		
		floor = new Boundary(this.width/2, this.height - 20, this.width - 20, 20, this, box2d);
		ceiling = new Boundary(10, 10, this.width - 20, 20, this, box2d);
		
		println("yes");
		
		step = 0;
	}

	public void draw() {
		background(255);
		this.box2d.step();
		stroke(255);

		sc.draw();
		floor.draw();
		//wc.draw();
		//ceiling.draw();
		
		readHapkitPos();
		    
		updateSpringPosition();
	}
	
	private void updateSpringPosition() {
		
		sc.updateActiveSpringY(hapkitPos);
	}

	public void readHapkitPos() {
		double rawValue = serialData.readIn();
		
		if(rawValue != 0.0){
			hapkitPos = rawValue;
		}
	}

	public void mousePressed() {
		sc.updateActiveSpring(this.mouseX, this.mouseY, true, false, serialData);
		//sc.updateActiveSpring(this.mouseX, this.mouseY, true, false);
	}
	
	public void mouseReleased() {
		sc.updateActiveSpring(this.mouseX, this.mouseY, false, false, serialData);
	}

	
}
