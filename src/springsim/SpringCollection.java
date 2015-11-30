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
	
	public boolean add(SpringInterface s){
		return springs.add(s);
	}
	
	public void setActive(SpringInterface s){
		if(activeSpring == null){
			activeSpring = s;
			activeSpring.getHand().swapIcon();
		}else{
			activeSpring.getHand().swapIcon();
			s.hand.swapIcon();
			activeSpring = s;
		}
	}
	
	public float getActiveForce() {
		return this.activeSpring.getForce();
	}
	
	public void displayForces(boolean display_on) {
		for (SpringInterface s : springs) {
			s.displayForce(display_on);
		}
	}
	
	public void draw() {
		for (SpringInterface s : springs) {
			if(s != null){
				s.draw();
			}
		}
	}
	
	public void updateActiveSpring(int mx, int my, boolean pressed, Hapkit hapkit) {
		for (SpringInterface s : springs) {
			if (s.getHand().contains(mx, my)) {
				this.setActive(s);
				
				if(rData.getInputMode() == rData.HAPKIT_MODE){
					hapkit.setKConstant(s.getK());
					// MAKES ALL OTHER SPRING ACT NORMALLY AGAIN:
					destroyOldHapkitJoints();
				}
				
				rData.logEvent(s.getK(), -1, "SWITCHING BETWEEN SPRINGS");
				break;
			}
		}
		
		if(rData.getInputMode() == rData.MOUSE_MODE){
			this.activeSpring.mouseUpdate(mx, my, pressed);
		}else{
			this.activeSpring.hapkitUpdate(my);
		}
	}
	
	private void destroyOldHapkitJoints() {
		for (SpringInterface s : springs) {
			if(!s.equals(activeSpring)){
				s.hand.destroy();
			}
		}
	}

	public void updateActiveSpringYPosition(double hapkitPos) {
		int currentY = this.activeSpring.getY();
		int newY = (int) (currentY + hapkitPos);
		this.activeSpring.hapkitUpdate(newY);	
	}
	
}
