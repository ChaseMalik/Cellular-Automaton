package cellsociety_team02;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cell.Cell;
import patch.Patch;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//This entire file is part of my masterpiece
//Kevin Rhine

/**
 * Model for the grid
 * Loads the grid
 * Updates the states of the cells and patches on the grid
 * Stores data for the view to access
 * 
 * @author Chase Malik
 * @author Greg Lyons
 * @author Kevin Rhine
 */
public class GridModel {
	private Cell[][] myCells;
	private Patch[][] myPatches;
	private String myGridType;
	private int myNumStates;
	private Map<Integer, XYChart.Series> seriesMap;
	/**
	 * load the file into the xml parser
	 * Prompts the user for an xml file
	 * Then passes the chosen file to the XMLParser and returns the XMLParser
	 * @return XMLParser parser that can access the chosen file
	 */
	private XMLParser loadFileToParser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showOpenDialog(new Stage());
		XMLParser parser = new XMLParser(file);
		return parser;
	}
	/**
	 * loads the file into the xml parser
	 * Then initializes the xml parser and gets the cells, patches, gridType, and number of cell states from it
	 * It also initializes the series for the graph
	 */
	public void load(){
		XMLParser parser = loadFileToParser();
		parser.initialize();
		myCells = parser.makeCells();
		myPatches = parser.makePatches();
		myGridType = parser.getGridType();
		myNumStates = parser.getNumStates();
		seriesMap = new HashMap<Integer, XYChart.Series>();
		initSeries();
	}
	/**
	 * Updates the patches and cells in the grid
	 * Once complete, it ticks forward by setting the current values to all of their future values
	 */
	public void update(){
		updatePatchesAndCells();
		setCurrentToFuture();
	}
	/**
	 * Loops through all of the patches to update all of the cells and patches in the grid
	 */
	private void updatePatchesAndCells() {
		for(int i=0;i<myPatches.length;i++){
			for(int j=0; j<myPatches[0].length;j++){
				myPatches[i][j].updateState(myPatches);
			}
		}
	}
	/**
	 * Loops through all of the patches to set its currentCell equal to its futureCell
	 */
	private void setCurrentToFuture() {
		for(int i=0;i<myPatches.length;i++){
			for(int j=0; j<myPatches[0].length;j++){
				myPatches[i][j].currentToFuture();
			}
		}
	}
	/**
	 * Returns the patches in the grid
	 * 
	 * @return Patch[][] array of all the patches on the grid
	 */
	public Patch[][] getPatches() {
		return myPatches;
	}
	/**
	 * Returns the type of grid used in this simulation
	 * 
	 * @return String of the grid type
	 */
	public String getGridType() {
		return myGridType;
	}
	/**
	 * Changes the cell's state by one
	 * Used for user interaction
	 * 
	 * @param double x row in the array of the cell to change
	 * @param double y column in the array of the cell to change
	 */
	public void changeState(double x, double y) {
		Cell c = myPatches[(int)x][(int)y].getCurrentCell();
		double state = c.getCurrentState();
		double futureState = (state+1) % myNumStates;
		c.setCurrentState(futureState);
		c.setFutureState(futureState);
	}
	
	//MASTERPIECE: This is the graph code that I completely refactored 
	/**
	 * Creates the appropriate number of series for the graph
	 */
	//seriesMap used to be "dataMap" and stored XYChart.Data instead of .Series
	private void initSeries(){
		for(int i=0;i<myNumStates;i++){
			seriesMap.put(i, new XYChart.Series());
		}
	}
	/**
	 * Updates the map from cell state to its data series
	 * by adding the new data points
	 * @param int newX data point to be added (number of frames)
	 * @return Map updated map
	 */
	//used to have separate "addData" and "getDataMap" methods
	//when I switched to "seriesMap", I combined these two into one
	
	public Map<Integer,XYChart.Series> addData(int newX){
		for(int i=0;i<seriesMap.size();i++){
			seriesMap.get(i).getData().add(new XYChart.Data(newX, numState(i)));
		}
		return seriesMap;
	}
	/**
	 * Counts how many cells of a particular state are on the board and returns the count
	 * 
	 * @param int state - the state of interest
	 * @return int count of the state on the board currently
	 */
	//this method was the same in both implementations
	private int numState(int state){
		int total = 0;
		for (int i = 0; i<myPatches.length; i++){
			for(int j=0; j<myPatches[0].length; j++){
				double current = myPatches[i][j].getCurrentCell().getCurrentState();
				if(current==state)
					total++;
			}
		}
		return total;
	}
}
