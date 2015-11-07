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
		printActiveForce();
	}
	
	public boolean add(Spring s){
		return springs.add(s);
	}
	
	public void setActive(Spring s){
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
	 * @param serialData 
	 */
	public void updateActiveSpring(int mx, int my, boolean pressed, boolean updatePosition, Hapkit hapkit) {
		for (Spring s : springs) {
			if (s.hand.contains(mx, my)) {
				this.setActive(s);
				hapkit.setKConstant(s.k);
				//serialData.writeToArduino();
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
	
	public float getActiveForce() {
		return this.activeSpring.getForce();
	}

	public void updateActiveSpringY(double hapkitPos) {

		int currentY = this.activeSpring.getY();
		int newY = (int) (currentY + hapkitPos);
		this.activeSpring.hapkitUpdate(newY);
		
	}
	
}
