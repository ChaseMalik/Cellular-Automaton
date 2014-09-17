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
	protected int[][] currentCells;
	protected int[][] futureCells;
	protected double[][] currentPatches;
	protected double[][] futurePatches;
	protected boolean isRunning;
	protected Collection<Cell> cells;
	
	protected double cellWidth;
	protected double cellHeight;
	
	public Grid(Map<String,String> parametersMap, int[][] initialCells, double[][] initialPatches) {
		map = parametersMap;
		futureCells = initialCells;
		futurePatches = initialPatches;
		currentCells = new int[futureCells.length][];
		currentPatches = new double[futurePatches.length][];
		for(int i = 0; i < futureCells.length; i++) {
		    currentCells[i] = futureCells[i].clone();
		    currentPatches[i] = futurePatches[i].clone();
		}
		cellHeight = 600.0/(initialCells.length);
		cellWidth = 600.0/(initialCells[0].length);
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
		for (int r=0; r<currentCells.length; r++) {
			for (int c=0; c<currentCells[0].length; c++) {
				updateCellandPatch(r, c);
			}
		}
	}
	
	protected void updateDisplay(){
		group.getChildren().removeAll(cells);
		cells.clear();
		for (int r=0; r<futureCells.length; r++) {
			for (int c=0; c<futureCells[0].length; c++) {
				Cell newCell = new Cell(c*cellWidth, r*cellHeight, cellWidth, cellHeight, futureCells[r][c]);
				cells.add(newCell);
				group.getChildren().add(newCell);
			}
		}
		currentCells = new int[futureCells.length][];
		currentPatches = new double[futurePatches.length][];
		for(int i = 0; i < futureCells.length; i++) {
		    currentCells[i] = futureCells[i].clone();
		    currentPatches[i] = futurePatches[i].clone();
		}
	}
	
	protected abstract void updateCellandPatch(int r, int c);
	
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
