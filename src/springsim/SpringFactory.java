package springsim;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import controlP5.Button;
import controlP5.Chart;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.RadioButton;
import controlP5.Textfield;
import processing.core.PApplet;

public class SpringFactory extends Component {

	private ArrayList<int[]> data;
	static int REGULAR_SPRING = 0;
	static int PARALLEL_SPRING = 1;

	
	PApplet parent;
	Chart myChart;
	
	Textfield stiffness; 
	Textfield stiffness2; 
	Textfield length;
	Textfield label;
	Button add;
	RadioButton springType;
	
	ResearchData rData;
	
	int spacing = 70;

	SpringInterface current_spring;
	ParallelSpring ps;
	Canvas canvas;
	
	public SpringFactory(PApplet p, ControlP5 cp5, ResearchData rData, Canvas designCanvas, int _x, int _y, int _w, int _h) {
		super(_x,_y,_w,_h);
		parent = p;
		this.rData = rData;
		this.canvas = designCanvas;
		
		stiffness =  cp5.addTextfield("Stiffness (K) #1")
			      .setPosition(x+130,y+50+spacing)
			      .setSize(60,25)
			      .setFocus(false)
			      .setValue("25")
			      .setColorCaptionLabel(255)
				  .setAutoClear(false)
			      ;
		
		stiffness2 =  cp5.addTextfield("Stiffness (K) #2")
			      .setPosition(x+130,y+50+spacing)
			      .setSize(60,25)
			      .setFocus(false)
			      .setValue("25")
			      .setColorCaptionLabel(255)
				  .setAutoClear(false)
				  .setVisible(false);
			      ;
		
		length =  cp5.addTextfield("Length (cm)")
			      .setPosition(x+130,y+50+(spacing*2))
			      .setSize(60,25)
			      .setFocus(false)
			      .setValue("2")
			      .setColorCaptionLabel(255)
				  .setAutoClear(false)
			      ;
		
		label =  cp5.addTextfield("Label")
			      .setPosition(x+130,y+50+(spacing*3))
			      .setSize(60,25)
			      .setFocus(false)
			      .setValue("Spring X")
			      .setColorCaptionLabel(255)
				  .setAutoClear(false)
			      ;
		
		add = cp5.addButton("Add to Canvas")
			     .setValue(1)
			     .setPosition(x+50,y+h-30)
			     .setSize(110,20)
			     .setId(1);
		
		springType = cp5.addRadioButton("springType")
				        .setPosition(x+10,y+40)
				        .setSize(40,20)
				        .setColorForeground(parent.color(120))
				        .setColorActive(parent.color(200))
				        .setColorLabel(parent.color(0))
				        .setItemsPerRow(1)
				        .setSpacingColumn(50)
				        .addItem("Regular Spring", REGULAR_SPRING)
				        .addItem("Parallel Spring", PARALLEL_SPRING)
				        .setNoneSelectedAllowed(false)
				        //.addItem("Serial Spring",2)
				        .activate(0);
		
		cp5.addListener(this);
		
		generateSpring(springType.getValue());
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public void draw() {

		parent.fill(255);
		parent.rect(x, y, w, h);
		parent.fill(0);
		parent.text("Spring Generator", x+10, y+25);
		
		parent.tint(255, 126);
		parent.textSize(0);
		current_spring.draw();
		parent.textSize(18);
		parent.tint(255, 255);
		
	}


	@Override
	public void controlEvent(ControlEvent event) {
		if(event.isFrom(springType)){
			changeDisplay(springType.getArrayValue(1));
			generateSpring(springType.getArrayValue(1));
		}else if(event.isFrom(length)){
			length.setFocus(false);
			generateSpring(springType.getArrayValue(1));
		}else if(event.isFrom(stiffness)){
			stiffness.setFocus(false);
			current_spring.setK(Integer.parseInt(stiffness.getStringValue()));
		}else if(event.isFrom(stiffness2)){
			stiffness2.setFocus(false);
			current_spring.setK2(Integer.parseInt(stiffness2.getStringValue()));
		}else if(event.isFrom(label)){
			label.setFocus(false);
			current_spring.setLabel(label.getStringValue());
		}else if(event.isFrom(add)){
			addCurrentSpring();
		}
	}

	private void changeDisplay(float value) {
		if(value == REGULAR_SPRING){
			stiffness2.setVisible(false);
			length.setPosition(x+130,y+50+(spacing*2));
			label.setPosition(x+130,y+50+(spacing*3));
		}else if(value == PARALLEL_SPRING){
			stiffness2.setVisible(true);
			stiffness2.setPosition(x+130,y+50+(spacing*2));
			length.setPosition(x+130,y+50+(spacing*3));
			label.setPosition(x+130,y+50+(spacing*4));
		}
	}

	private void addCurrentSpring() {
		
		int len = Integer.parseInt(length.getText());
		System.out.println(len*Main.SCALE_FACTOR);
		int final_len = len*Main.SCALE_FACTOR;
		
		int y = Canvas.Y_ALL;
		ArrayList<Integer> x = new ArrayList<Integer>();
		int x_final=0, x_i = -1;
		
		int i=0;
		for(SpringInterface s : canvas.sc.springs){
			if(s==null){
				x_i = i;
				break;
			}else{
				i++;
			}
		}
		
		System.out.println("X_i"+x_i);
		
		if(x_i == 0){
			x_final = Canvas.X1;
		}else if(x_i == 1){
			x_final = Canvas.X2;
		}else if(x_i == 2){
			x_final = Canvas.X3;
		}else if(x_i == -1){
			JOptionPane.showMessageDialog (null, "Try deleting a spring to make room for this new one!", "Too Many Springs", JOptionPane.INFORMATION_MESSAGE);
		}
		
			if(x_final != 0){
				// TODO Auto-generated method stub
				if(springType.getArrayValue(1) == REGULAR_SPRING){
					System.out.println("reglar");
					current_spring = new Spring(x_final, y, Integer.parseInt(stiffness.getText()), final_len, label.getText(), parent, Canvas.box2d, rData);
				}else if(springType.getArrayValue(1) == PARALLEL_SPRING){
					System.out.println("parallel");
					current_spring = new ParallelSpring(x_final, y, Integer.parseInt(stiffness.getText()),Integer.parseInt(stiffness2.getText()) ,final_len, label.getText(), parent, Canvas.box2d, rData);
				}
				
				canvas.sc.springs.remove(x_i);
				canvas.sc.add(x_i,current_spring);
				canvas.sc.setActive(current_spring);
				
				System.out.println(canvas.sc.springs.indexOf(current_spring));
				
				generateSpring(springType.getValue());
			}
		
		
		
	}

	private void generateSpring(float value) {
		
		int len = Integer.parseInt(length.getText());
		System.out.println(len*Main.SCALE_FACTOR);
		int final_len = len*Main.SCALE_FACTOR;
		
		if(value == REGULAR_SPRING){
			current_spring = new Spring(this.x+(this.w/4), 150, Integer.parseInt(stiffness.getText()), final_len, label.getText(), parent, Canvas.box2d, rData);
		}else if(value == PARALLEL_SPRING){
			current_spring = new ParallelSpring(this.x+(this.w/4), 150, Integer.parseInt(stiffness.getText()), Integer.parseInt(stiffness2.getText()), final_len, label.getText(), parent, Canvas.box2d, rData);
		}
	}

}
