package springsim;

public interface SpringInterface {
	public Hand getHand();
	public void draw();
	public void mouseUpdate(int mx, int my, boolean pressed);
	public float getLength();
	public float getForce();
	public int getX();
	public int getY();	
}
