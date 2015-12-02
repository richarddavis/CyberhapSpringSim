package springsim;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

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
	
//	private void balanceXPositions() {
//		for(int i=0; i<springs.size(); i++){
//			int interval = (Canvas.w/springs.size());
//			int new_x = (int) ((interval*i)+(0.5*interval));
//			
//
//			Class<?> clazz = springs.get(i).getClass();
//			Constructor<?> constructor;
//			
//			try {
//				constructor = clazz.getConstructor(int.class, int.class, int.class, int.class,String.class, PApplet.class, Box2DProcessing.class, ResearchData.class);
//				Object instance = constructor.newInstance(Canvas.x+new_x, springs.get(i).y, springs.get(i).k, springs.get(i).originalLen, springs.get(i).getLabel(), springs.get(i).parent, Canvas.box2d, rData);
//				springs.remove(springs.get(i));
//				springs.add((SpringInterface) instance);
//			}catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

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
	
	public void spaceSpringsProportionately(int w){
		for(int i=0;i<springs.size();i++){
			if(springs.get(i) !=null){
				int interval_width = (w/springs.size());
				int x = (interval_width*i)+(interval_width/2);
				springs.get(i).setX(x);
			}
		}
	}
	
	public void updateActiveSpring(int mx, int my, boolean pressed, Hapkit hapkit) {
		for (SpringInterface s : springs) {
			if (s!= null && s.getHand().contains(mx, my)) {
				this.setActive(s);
				
				if(rData.getInputMode() == ResearchData.HAPKIT_MODE){
					hapkit.setKConstant(s.getK());
					// MAKES ALL OTHER SPRING ACT NORMALLY AGAIN:
					destroyOldHapkitJoints();
				}
				
				rData.logEvent(s.getK(), -1, "SWITCHING BETWEEN SPRINGS");
				break;
			}
		}
		
		if(rData.getInputMode() == ResearchData.MOUSE_MODE){
			this.activeSpring.mouseUpdate(mx, my, pressed);
		}else{
			this.activeSpring.hapkitUpdate(my);
		}
	}
	
	private void destroyOldHapkitJoints() {
		for (SpringInterface s : springs) {
			if(s != null && !s.equals(activeSpring)){
				s.hand.destroy();
			}
		}
	}

	public void updateActiveSpringYPosition(double hapkitPos) {
		int currentY = this.activeSpring.getY();
		int newY = (int) (currentY + hapkitPos);
		this.activeSpring.hapkitUpdate(newY);	
	}

	public void delete(int value) {
		springs.remove(value);
		springs.add(value, null);
		
	}

	public void add(int x_i, SpringInterface s) {
			springs.add(x_i, s);
	}
	
}
