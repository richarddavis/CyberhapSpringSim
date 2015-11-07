package springsim;

import processing.core.PApplet;
import processing.serial.Serial;


public class Hapkit {

	Serial myPort;
	int SERIAL_WRITE_LENGTH = 32; 
	PApplet p;
	int k_constant;
	int gain_val; // legal values are 1, 2, and 3
	int feedback_on; // 1 for on, 0 for off
	int min_k = 0;
	int max_k = 10000;
	
	public Hapkit(PApplet p, String[] serialPorts, int index){
		this.p = p;
		System.out.println("Available serial devices:");
		for (int i=0; i < serialPorts.length; i++) {
			System.out.println("The device on port " + i + " is: " + serialPorts[i]);
		}
		System.out.println("The selected device is: " + serialPorts[index]);
		myPort = new Serial(this.p, serialPorts[index], 9600);
		myPort.bufferUntil('\n');
		
		// Initialize Hapkit with sensible values
		this.k_constant = 10;
		this.gain_val = 1;
		this.feedback_on = 1;
		
		this.writeToArduino();
	}
	
	public void setFeedback(boolean on) {
		boolean update_arduino = false;
		if (on == true) {
			System.out.println("Hapkit feedback turned on.");
			if (this.feedback_on != 1) {
				this.feedback_on = 1;
				update_arduino = true;
			}
		} else if (on == false) {
			System.out.println("Hapkit feedback turned off.");
			if (this.feedback_on != 0) {
				this.feedback_on = 0;
				update_arduino = true;
			}
		} else {
			System.out.println("setFeedback only accepts true or false as arguments.");
		}
		if (update_arduino == true) {
			this.writeToArduino();
		}
	}
	
	public void setGain(int gain) {
		boolean update_arduino = false;
		if (gain != this.gain_val) {
			update_arduino = true;
		}
		if (gain == 1) {
			System.out.println("Setting Hapkit gain to 1x.");
			this.gain_val = 1;
		} else if (gain == 2) {
			System.out.println("Setting Hapkit gain to 2x.");
			this.gain_val = 2;
		} else if (gain == 3) {
			System.out.println("Setting Hapkit gain to 3x.");
			this.gain_val = 3;
		} else {
			System.out.println("Unknown gain requested.");
			update_arduino = false;
		}
		if (update_arduino == true) {
			this.writeToArduino();
		}
	}
	
	public void setKConstant(int k_cons) {
		boolean update_arduino = false;
		if (k_cons != this.k_constant) {
			update_arduino = true;
		}
		if (k_cons < this.min_k || k_cons > this.max_k) {
			System.out.println("k_constant must be between " + this.min_k + " and " + this.max_k + ".");
			return;
		}
		this.k_constant = k_cons;
		if (update_arduino == true) {
			this.writeToArduino();
		}
	}
	
	public int getForce() {
		return 10;
	}
	
	public double readIn(){
		String inString = "";
	    double value = 0.0;
	    while(myPort.available() > 0)
	    {
	      inString = myPort.readStringUntil('\n');
	    }
	    //PApplet.println(inString);
	    if (inString != "" && inString != null)
	    {
	       try {
	        
	        String[] list = inString.split(",");
	        
	        String xString = list[0].replaceAll("\\s",""); // trim off whitespaces.
	        //PApplet.println(xString);
	        double xByte = Double.parseDouble(xString);         // convert to a number.

	        if(!Double.isNaN(xByte) && xByte != 0){
	          int pixelsPerMeter = 500;
	          value = xByte * pixelsPerMeter;
	        }       
	        
	       } catch(NumberFormatException e){
	    	   PApplet.println("data parse error");
	       }
	    
	     }
	    
	    return value;
	}
	
	public void writeToArduino(){
		myPort.write(this.k_constant);
		myPort.write(this.feedback_on);
		myPort.write(this.gain_val);
	}
	
	/**
	 * courtesy of: http://stackoverflow.com/questions/7505991/arduino-map-equivalent-function-in-java
	 * 
	 * @param x
	 * @param in_min
	 * @param in_max
	 * @param out_min
	 * @param out_max
	 * @return
	 */
	public long map(long x, long in_min, long in_max, long out_min, long out_max)
	{
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
}

