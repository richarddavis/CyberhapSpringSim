package springsim;

import java.util.ArrayList;

public class WeightCollection {
	
	ArrayList<Weight> hooks;
	
	public WeightCollection(){
		hooks = new ArrayList<Weight>();
	}
	
	public void draw() {
		for (Weight h : hooks) {
			h.draw();
		}
	}
	
	public boolean add(Weight h){
		return hooks.add(h);
	}
	
}
