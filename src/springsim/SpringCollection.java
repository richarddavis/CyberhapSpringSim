package springsim;

import java.util.ArrayList;

public class SpringCollection {
	
	ArrayList<NewSpring> springs;
	NewSpring activeSpring; 
	
	public SpringCollection(){
		springs = new ArrayList<NewSpring>();
	}
	
	public void draw() {
		for (NewSpring s : springs) {
			s.draw();
		}
	}
	
	public boolean add(NewSpring s){
		return springs.add(s);
	}
	
	public void setActive(NewSpring s){
		activeSpring = s;
	}
	
	public void updateActive(int mx, int my, boolean pressed) {
		this.activeSpring.mouseUpdate(mx, my, pressed);
	}
	
}
