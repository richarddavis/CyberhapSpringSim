package springsim;

import java.util.ArrayList;

public class SpringCollection {
	
	ArrayList<SpringInterface> springs;
	SpringInterface activeSpring; 
	
	public SpringCollection(){
		springs = new ArrayList<SpringInterface>();
	}
	
	public void draw() {
		for (SpringInterface s : springs) {
			s.draw();
		}
		printActiveForce();
	}
	
	public boolean add(SpringInterface s){
		return springs.add(s);
	}
	
	public void setActive(SpringInterface s){
		activeSpring = s;
	}
	
	public void updateActive(int mx, int my, boolean pressed) {
		for (SpringInterface s : springs) {
			if (s.getHand().contains(mx, my)) {
				this.setActive(s);
				break;
			}
		}
		this.activeSpring.mouseUpdate(mx, my, pressed);
	}
	
	public void printActiveForce() {
		//System.out.println(this.activeSpring.getForce());
	}
	
}
