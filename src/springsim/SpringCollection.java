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
		if(activeSpring == null){
			activeSpring = s;
			activeSpring.hand.swapIcon();
		}else{
			activeSpring.hand.swapIcon();
			s.hand.swapIcon();
			activeSpring = s;
		}
	}
	
	
	/**
	 * 
	 * @param mx
	 * @param my
	 * @param updatePosition
	 */
	public void updateActiveSpring(int mx, int my, boolean pressed, boolean updatePosition) {
		for (NewSpring s : springs) {
			if (s.hand.contains(mx, my)) {
				this.setActive(s);
				break;
			}
		}
		if(updatePosition){
			this.activeSpring.mouseUpdate(mx, my, pressed);
		}
	}
	
	public void printActiveForce() {
		//System.out.println(this.activeSpring.getForce());
	}

	public void updateActiveSpringY(double hapkitPos) {

		int currentY = this.activeSpring.getY();
		int newY = (int) (currentY + hapkitPos);
		this.activeSpring.hapkitUpdate(newY);
		
	}
	
}
