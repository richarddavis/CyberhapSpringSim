package springsim;

import java.util.ArrayList;

public class HookCollection {
	
	ArrayList<Hook> hooks;
	
	public HookCollection(){
		hooks = new ArrayList<Hook>();
	}
	
	public void draw() {
		for (Hook h : hooks) {
			h.draw();
		}
	}
	
	public boolean add(Hook h){
		return hooks.add(h);
	}
	
}
