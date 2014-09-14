package cellsociety_team02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
		current = initialStates;
		future = initialStates;
		cellHeight = 600.0/(initialStates.length);
		cellWidth = 600.0/(initialStates[0].length);
		isRunning = true;
	}
	
	public Scene init(int width, int height) {
		group = new Group();
		scene = new Scene(group, width, height, Color.WHITE);
		cells = new ArrayList<Cell>();
		group.getChildren().addAll(cells);
		updateDisplay();
		return scene;
	}
	
	public KeyFrame startHandlers() {
		group.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent ke) {
				keyPressed(ke);
			}			
		});
		
		KeyFrame kf = new KeyFrame(Duration.seconds(1.0), new EventHandler<ActionEvent>() {
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
		for (int i=0; i<current.length; i++) {
			for (int j=0; j<current[0].length; j++) {
				updateCell(i, j);
			}
		}
	}
	
	protected void updateDisplay(){
		System.out.println(cellWidth);
		System.out.println(cellHeight);
		group.getChildren().removeAll(cells);
		for (int i=0; i<future.length; i++) {
			for (int j=0; j<future[0].length; j++) {
				Cell c = new Cell(j*cellWidth, i*cellHeight, cellWidth, cellHeight, future[i][j]);
				cells.add(c);
				group.getChildren().add(c);
			}
		}
	}
	
	protected abstract void updateCell(int i, int j);
	
	protected void keyPressed(KeyEvent ke) {
		if (ke.getCode() == KeyCode.SPACE){
			isRunning = !isRunning;  //pauses if currently running, resumes if currently paused
		}
	}
}
