package cellsociety_team02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
	protected Map<Integer, Color> colorMap;
	private int stepCount;
	private Text stepText;
	private Text speedText;
	
	protected double cellWidth;
	protected double cellHeight;
	
	public Grid(Map<String,String> parametersMap, int[][] initialCells, double[][] initialPatches) {
		map = parametersMap;
		colorMap = new HashMap<Integer, Color>();
		setColors();
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
	
	public Scene init(int width, int height, ResourceBundle bundle) {
		group = new Group();
		scene = new Scene(group, width, height, Color.WHITE);
		cells = new ArrayList<Cell>();
		group.getChildren().addAll(cells);
		stepCount = 0;
		updateDisplay();
		updateStepCounter();
		makeText(bundle);
		return scene;
	}
	
	private void makeText(ResourceBundle bundle) {
		group.getChildren().add(new Text(30, 620, bundle.getString("SpeedDisplay")));
		group.getChildren().add(new Text(30, 640, bundle.getString("StepsDisplay")));
		group.getChildren().add(new Text(220, 615, bundle.getString("StartStopCommand")));
		group.getChildren().add(new Text(220, 630, bundle.getString("StepCommand")));
		group.getChildren().add(new Text(220, 645, bundle.getString("IncreaseSpeedCommand")));
		group.getChildren().add(new Text(220, 660, bundle.getString("DecreaseSpeedCommand")));
		group.getChildren().add(new Text(220, 675, bundle.getString("LoadCommand")));
	}
	
	public void updateSpeedText(double interval){
		group.getChildren().remove(speedText);
		speedText = new Text(120, 620, String.valueOf(1.0/interval));
		group.getChildren().add(speedText);
	}
	
	private void updateStepCounter() {
		group.getChildren().remove(stepText);
		stepText = new Text(150, 640, String.valueOf(stepCount));
		group.getChildren().add(stepText);
		stepCount++;
	}
	
	public KeyFrame startHandlers(double interval) {
		
		KeyFrame kf = new KeyFrame(Duration.seconds(interval), new EventHandler<ActionEvent>() {
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
		updateStepCounter();
	}
	
	protected void updateDisplay(){
		group.getChildren().removeAll(cells);
		cells.clear();
		for (int r=0; r<futureCells.length; r++) {
			for (int c=0; c<futureCells[0].length; c++) {
				Cell newCell = new Cell(c*cellWidth, r*cellHeight, cellWidth, cellHeight, futureCells[r][c], colorMap);
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
	
	protected abstract void setColors();
	
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
