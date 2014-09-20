package cellsociety_team02;

import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class GridModel {
	private List<Cell> myCells;
	private List<Patch> myPatches;
	private Map<String, String> myParameters;
	private Timeline myAnimation;
	private double myInterval;
	
	public GridModel(){

	}
	
	public void initialize(List<Cell> cells, List<Patch> patches, Map<String, String> parameters){
		myCells = cells;
		myPatches = patches;
		myParameters = parameters;
		myAnimation = new Timeline();
		myInterval = 1.0;
		startAnimation();
		System.out.println(myCells.get(0).getCurrentState());
	}
	
	private void startAnimation() {
		myAnimation.stop();
		KeyFrame frame = startHandler(myInterval);
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().clear();
		myAnimation.getKeyFrames().add(frame);
		myAnimation.play();
	}
	
	public KeyFrame startHandler(double interval) {
		KeyFrame kf = new KeyFrame(Duration.seconds(interval), new EventHandler<ActionEvent>() {
	    @Override
	    	public void handle(ActionEvent event) {
	    		update();
	    	}
	    });
		return kf;
	}
	
	public void updateSpeed(double value) {
		myInterval = value;
		startAnimation();
	}
	
	public void step(){
		update();
	}
	
	private void update(){
		for (Cell c: myCells) {
			c.updateStateandMove(myCells);
		}
		for (Patch p: myPatches) {
			
		}
		for (Cell c: myCells) {
			c.currentToFuture();
		}
	}

	
	
	
}
