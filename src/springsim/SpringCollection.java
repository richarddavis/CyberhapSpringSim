package springsim;

import java.util.ArrayList;

public class SpringCollection {
	

	ArrayList<SpringInterface> springs;
	SpringInterface activeSpring; 
	ResearchData rData;
	
	public SpringCollection(ResearchData rData){
		this.springs = new ArrayList<SpringInterface>();
		this.rData = rData;
	}
	
	public void draw() {
		for (SpringInterface s : springs) {
			if(s != null){
				s.draw();
			}
		}
		printActiveForce();
	}
	

	public boolean add(Spring s){
		return springs.add(s);
	}
	
	public void setActive(Spring s){
		if(activeSpring == null){
			activeSpring = s;
			activeSpring.getHand().swapIcon();
		}else{
			activeSpring.getHand().swapIcon();
			s.hand.swapIcon();
			activeSpring = s;
		}
		
	}
	
	public void setSpringX(int K){
		springs.get(0).setK(K);
	}
	
	public void setSpringY(int K){
		springs.get(1).setK(K);
	}
	
	
	
	/**
	 * 
	 * @param mx
	 * @param my
	 * @param updatePosition
	 * @param serialData 
	 */
	public void updateActiveSpring(int mx, int my, boolean pressed, boolean updatePosition, Hapkit hapkit) {
		for (SpringInterface s : springs) {
			if (s.getHand().contains(mx, my)) {
				this.setActive(s);
				hapkit.setKConstant(s.getK());
				//serialData.writeToArduino();
				rData.logEvent(s.getK(), -1, "SWITCHING BETWEEN SPRINGS");
				break;
			}
		}
		if(updatePosition){
			this.activeSpring.mouseUpdate(mx, my, pressed);
		}
	}
	

	public boolean add(SpringInterface s){
		return springs.add(s);
	}
	
	public void setActive(SpringInterface s){
		activeSpring = s;
	}
	
	public void updateActive(int mx, int my, boolean pressed, boolean updatePosition, Hapkit hapkit) {
		for (SpringInterface s : springs) {
			if (s.getHand().contains(mx, my)) {
				this.setActive(s);
				hapkit.setKConstant(s.getK());
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
	
	public void setXSpringActive(){
		for (SpringInterface s : springs) {
		 if(s.getName().equals("X")){
			 this.setActive(s);
		 }
		}	
	}
	
	public void setYSpringActive(){
		for (SpringInterface s : springs) {
		 if(s.getName().equals("Y")){
			 this.setActive(s);
		 }
		}	
	}
	
	public float getActiveForce() {
		return this.activeSpring.getForce();
	}

	public void updateActiveSpringY(double hapkitPos) {

		int currentY = this.activeSpring.getY();
		int newY = (int) (currentY + hapkitPos);
		this.activeSpring.hapkitUpdate(newY);
		
	}
	
	public void displayForces(boolean display_on) {
		for (SpringInterface s : springs) {
			s.displayForce(display_on);
		}
	}
	
}
