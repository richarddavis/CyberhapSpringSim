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
		printActiveForce();
	}
	
	public boolean add(NewSpring s){
		return springs.add(s);
	}
	
	public void setActive(NewSpring s){
		activeSpring = s;
	}
	
	public void updateActive(int mx, int my, boolean pressed) {
		for (NewSpring s : springs) {
			if (s.hand.contains(mx, my)) {
				this.setActive(s);
				break;
			}
		}
		this.activeSpring.mouseUpdate(mx, my, pressed);
	}
	
	public void printActiveForce() {
		System.out.println(this.activeSpring.getForce());
	}
	
}
