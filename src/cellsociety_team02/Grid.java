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

/**
 * Represents the CA simulation grid
 * Controls UI
 * Stores the current and future arrays of cells and patches
 * 
 * @author Chase Malik
 * @author Greg Lyons
 * @author Kevin Rhine
 */
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
	
	    /**
	     * Constructs a grid with the given initial states and parameters
	     * 
	     * @param parametersMap map of the parameters for the given grid
	     * @param initialCells initial states of cells at each location
             * @param initialPatches initial states of patches at each location
	     */
	public Grid(Map<String,String> parametersMap, int[][] initialCells, double[][] initialPatches) {
		map = parametersMap;
		futureCells = initialCells;
		futurePatches = initialPatches;
		setCurrentToFuture();
		cellHeight = 600.0/(initialCells.length);
		cellWidth = 600.0/(initialCells[0].length);
		isRunning = false;
	}
	/**
         * Copies futureCells into currentCells
         */
	private void setCurrentToFuture() {
                currentCells = new int[futureCells.length][];
                currentPatches = new double[futurePatches.length][];
		for(int i = 0; i < futureCells.length; i++) {
		    currentCells[i] = futureCells[i].clone();
		    currentPatches[i] = futurePatches[i].clone();
		}
	}
	/**
         * Creates a scene with the initial width and height
         * Sets up the text bundle
         * 
         * @param width width of the scene
         * @param height of the scene
         * @return bundle bundle of appropriate language
         */
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
	/**
         * Add appropriate text to the group, so it can be displayed
         * 
         * @param bundle bundle of appropriate language
         */
	private void makeText(ResourceBundle bundle) {
		group.getChildren().add(new Text(30, 620, bundle.getString("SpeedDisplay")));
		group.getChildren().add(new Text(30, 640, bundle.getString("StepsDisplay")));
		group.getChildren().add(new Text(220, 615, bundle.getString("StartStopCommand")));
		group.getChildren().add(new Text(220, 630, bundle.getString("StepCommand")));
		group.getChildren().add(new Text(220, 645, bundle.getString("IncreaseSpeedCommand")));
		group.getChildren().add(new Text(220, 660, bundle.getString("DecreaseSpeedCommand")));
		group.getChildren().add(new Text(220, 675, bundle.getString("LoadCommand")));
	}
	/**
         * Remove the speedText and display the new value
         * 
         * @param interval double that is used to define the spring
         */
	public void updateSpeedText(double interval){
		group.getChildren().remove(speedText);
		speedText = new Text(120, 620, String.valueOf(1.0/interval));
		group.getChildren().add(speedText);
	}
	/**
         * Update the step counter display
         * 
         */
	private void updateStepCounter() {
		group.getChildren().remove(stepText);
		stepText = new Text(150, 640, String.valueOf(stepCount));
		group.getChildren().add(stepText);
		stepCount++;
	}
	/**
         * start the Handler which updates the states of the cells and the patches every so often
         * The handler also updates the display
         * 
         * @param interval defines how often a new key frame is made 
         */
	public KeyFrame startHandler(double interval) {

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
	/**
         * Loops through all of the grid locations and calls updateCellandPatch at each location
         * 
         */
	protected void updateStates(){
		for (int r=0; r<currentCells.length; r++) {
			for (int c=0; c<currentCells[0].length; c++) {
				updateCellandPatch(r, c);
			}
		}
		updateStepCounter();
	}
	/**
         * Loops through all of the grid locations and calls
         * At each location a new rectangle is created 
         * It is colored based on the patch and cell at the location
         *
         * It then calls setCurrentToFuture() 
         */
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
		setCurrentToFuture();
	}
	/**
	* Updates the states of the cell and patch at the location (r,c) in the array
	*/
	protected abstract void updateCellandPatch(int r, int c);
	/**
	* Gets the appropriate color of the grid location at (r,c) based on the cell and patch there
	*/
	protected abstract Color setColor(int r, int c);
	/**
	* Starts or stops the program by toggling the boolean isRunning
	*/
	public void startStop() {
		isRunning = !isRunning;
	}
	/**
	* Runs one tick of time if the simulation is not already running
	*/
	public void step() {
		if (!isRunning) {
			updateStates();
			updateDisplay();
		}
	}
}
