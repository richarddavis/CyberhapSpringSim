package springsim;

import processing.core.PApplet;
import processing.serial.Serial;


public class SerialComm {

	Serial myPort;
	int SERIAL_WRITE_LENGTH = 32; 
	PApplet p;
	
	public SerialComm(PApplet p, String[] serialPorts, int index){
		this.p = p;
		myPort = new Serial(this.p, serialPorts[index], 9600);
		myPort.bufferUntil('\n');
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
	        PApplet.println(xString);
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
	
	public void writeToArduino(int x){
		myPort.write(x);
		myPort.write(255);
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

