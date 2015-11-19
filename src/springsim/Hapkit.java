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
	double current_pos;
	CSVLogOutput log;
	
	public Hapkit(PApplet p, String[] serialPorts, int index, CSVLogOutput log){
		
		this.p = p;
		this.log = log;
		
		System.out.println("Available serial devices:");
		for (int i=0; i < serialPorts.length; i++) {
			System.out.println("The device on port " + i + " is: " + serialPorts[i]);
		}
		System.out.println("The selected device is: " + serialPorts[index]);
		
		myPort = new Serial(this.p, serialPorts[index], 9600);
		
		//nolonger applies -> not using Serial.println on arduino
		//myPort.bufferUntil('\n');
		
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
	
	public void writeToArduino(){
		myPort.write(this.k_constant);
		myPort.write(this.feedback_on);
		myPort.write(this.gain_val);
		
		CSVLogEvent e = new CSVLogEvent(-1,-1,k_constant, current_pos);
		e.setNotes("request sent to arduino to update values");
		log.addEvent(e);
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
	public long map(long x, long in_min, long in_max, long out_min, long out_max) {
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	public double getPos() {
		return current_pos;
	}
	
	public void serialEvent(Serial p){

		byte[] inBytes = p.readBytesUntil(225);
		
		try{
			if(inBytes != null && inBytes.length > 5){

				int x_bits; 
						
				x_bits = (inBytes[1] & 0xFF) 
			            | ((inBytes[2] & 0xFF) << 8)
			            |  ((inBytes[3] & 0xFF) << 16) 
			            | ((inBytes[4] & 0xFF) << 24);

				float X = Float.intBitsToFloat(x_bits);
				
				// check for rogue numbers...
				if((Math.abs(X-current_pos) > 1000)){
					// do nothing
				}else{
					current_pos = (double) X * 2000;
				}
				
			}else if(inBytes != null && inBytes.length > 3){
				int k_bits;
				k_bits = ((inBytes[1] & 0xFF));
				
				CSVLogEvent e = new CSVLogEvent(-1,-1,k_bits, current_pos);
				e.setNotes("Hapkit confirmed updated K-constant is rendering");
				log.addEvent(e);
			}
			
		}catch(Exception e){
			this.p.println(e);
		}
	}
}

