package cellsociety_team02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public abstract class Grid {
	
	protected Scene scene;
	protected Group group;
	protected Map<String,String> map;
	protected int[][] current;
	protected int[][] future;
	protected boolean isRunning;
	protected Collection<Cell> cells;
	
	protected double cellWidth;
	protected double cellHeight;
	
	public Grid(Map<String,String> parametersMap, int[][] initialStates) {
		map = parametersMap;
		future = initialStates;
		current = new int[future.length][];
		for(int i = 0; i < future.length; i++)
		    current[i] = future[i].clone();
		
		cellHeight = 600.0/(initialStates.length);
		cellWidth = 600.0/(initialStates[0].length);
		isRunning = false;
	}
	
	public Scene init(int width, int height) {
		group = new Group();
		scene = new Scene(group, width, height, Color.WHITE);
		cells = new ArrayList<Cell>();
		group.getChildren().addAll(cells);
		updateDisplay();
		return scene;
	}
	
	public KeyFrame startHandlers(double speed) {
		
		KeyFrame kf = new KeyFrame(Duration.seconds(speed), new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
	    	if (isRunning){
	    		updateStates();
	    		updateDisplay();
	    	}
	       }
	    });
		return kf;
	}
	
	protected void updateStates(){
		for (int r=0; r<current.length; r++) {
			for (int c=0; c<current[0].length; c++) {
				updateCell(r, c);
			}
		}
	}
	
	protected void updateDisplay(){
		group.getChildren().removeAll(cells);
		cells.clear();
		for (int r=0; r<future.length; r++) {
			for (int c=0; c<future[0].length; c++) {
				Cell newCell = new Cell(c*cellWidth, r*cellHeight, cellWidth, cellHeight, future[r][c]);
				cells.add(newCell);
				group.getChildren().add(newCell);
			}
		}
		current = new int[future.length][];
		for(int i = 0; i < future.length; i++)
		    current[i] = future[i].clone();
	}
	
	protected abstract void updateCell(int r, int c);
	
	public void startStop() {
		isRunning = !isRunning;
	}
	
	public void step() {
		if (!isRunning) {
			updateStates();
			updateDisplay();
		}
	}
}
