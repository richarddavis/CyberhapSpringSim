package springsim;

import java.util.ArrayList;

public class SpringCollection {
	
	ArrayList<Spring> springs;
	Spring activeSpring; 
	
	public SpringCollection(){
		springs = new ArrayList<Spring>();
	}
	
	public void draw(int x, int y) {
		for (Spring s : springs) {
			s.draw();
			s.update(x, y);
		}
	}
	
	public boolean add(Spring s){
		return springs.add(s);
	}
	
	public void setActive(Spring s){
		activeSpring = s;
	}
	
}
