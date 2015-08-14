package springsim;

import java.util.ArrayList;

public class SpringCollection {
	
	ArrayList<Spring> springs;
	Spring activeSpring; 
	
	public SpringCollection(){
		springs = new ArrayList<Spring>();
	}
	

	public void draw() {
		for (Spring s : springs) {
			s.draw();
		}
	}
	
	public boolean add(Spring s){
		return springs.add(s);
	}
	
	public void setActive(Spring s){
		activeSpring = s;
	}
	
}
