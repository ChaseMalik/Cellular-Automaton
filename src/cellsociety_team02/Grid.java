package cellsociety_team02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public abstract class Grid {
	
	protected Group group;
	protected Map<String,String> map;
	protected int[][] currentCells;
	protected int[][] futureCells;
	protected double[][] currentPatches;
	protected double[][] futurePatches;
	protected boolean isRunning;
	protected Collection<Rectangle> cellsAndPatches;
	private int stepCount;
	private Text stepText;
	private Text speedText;

	
	protected double cellWidth;
	protected double cellHeight;
	
	public Grid(Map<String,String> parametersMap, int[][] initialCells, double[][] initialPatches) {
		map = parametersMap;
		futureCells = initialCells;
		futurePatches = initialPatches;
		currentCells = new int[futureCells.length][];
		currentPatches = new double[futurePatches.length][];
		setCurrentToFuture();
		cellHeight = 600.0/(initialCells.length);
		cellWidth = 600.0/(initialCells[0].length);
		isRunning = false;
	}

	private void setCurrentToFuture() {
		for(int i = 0; i < futureCells.length; i++) {
		    currentCells[i] = futureCells[i].clone();
		    currentPatches[i] = futurePatches[i].clone();
		}
	}
	
	public Scene init(int width, int height, ResourceBundle bundle) {
		group = new Group();
		Scene scene = new Scene(group, width, height, Color.WHITE);
		cellsAndPatches = new ArrayList<Rectangle>();
		group.getChildren().addAll(cellsAndPatches);
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
		group.getChildren().removeAll(cellsAndPatches);
		cellsAndPatches.clear();
		for (int r=0; r<futureCells.length; r++) {
			for (int c=0; c<futureCells[0].length; c++) {
				Rectangle newDisplay = new Rectangle(c*cellWidth, r*cellHeight, cellWidth, cellHeight);
				newDisplay.setFill(setColor(r,c));
				cellsAndPatches.add(newDisplay);
				group.getChildren().add(newDisplay);
			}
		}
		currentCells = new int[futureCells.length][];
		currentPatches = new double[futurePatches.length][];
		setCurrentToFuture();
	}
	
	protected abstract void updateCellandPatch(int r, int c);
	
	protected abstract Color setColor(int r, int c);
	
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
