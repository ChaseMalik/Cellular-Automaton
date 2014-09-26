package Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Patch.Patch;
import javafx.scene.paint.Paint;

/**
 * Abstract Cell Class
 * Stores the information of the cell
 * Updates and moves throughout the grid based on individual simulation rules
 * 
 * @author Chase Malik
 * @author Greg Lyons
 * @author Kevin Rhine
 */
public abstract class Cell {

	protected double currentState;
	protected double futureState;
	protected int currentX;
	protected int currentY;
	protected int futureX;
	protected int futureY;
	protected int[] xDelta;
	protected int[] yDelta;
	protected Map<String,String> myParameters;
	protected String boundaryType;
	/**
	 * Cell constructor
	 * 
	 * @param double state Takes the given state and sets the cell's current and future state
	 * @param int x Takes the given value and sets the cell's current and future x
	 * @param int y Takes the given value and sets the cell's current and future y
	 * @param parameters Takes the given value and sets the cell's parameter map
	 */
	public Cell(double state, int x, int y, Map<String,String> parameters) {
		currentState = state;
		futureState = state;
		currentX = x;
		currentY = y;
		futureX = x;
		futureY = y;
		myParameters = parameters;
		initialize();
		setDeltas();
		if(myParameters.containsKey("boundary"))
			boundaryType = (myParameters.get("boundary"));
		else boundaryType = "Finite";
	}
	/**
	 * Cell constructor
	 * 
	 * @param Cell c makes a new cell based on the cell c's future values
	 */
	public Cell(Cell c){
		this(c.getFutureState(),c.getFutureX(), c.getFutureY(), c.getParameters());
	}
	/**
	 * Return currentState of cell
	 * 
	 * @return double currentState
	 */
	public double getCurrentState(){
		return currentState;
	}
	/**
	 * Set currentState of cell
	 * Used for changing colors
	 * 
	 * @param double sets the cell's current state
	 */
	public void setCurrentState(double state){
		currentState = state;
	}
	/**
	 * Return futureState of cell
	 * 
	 * @return double futureState
	 */
	public double getFutureState(){
		return futureState;
	}
	/**
	 * Set futureState of cell
	 * 
	 * @param double sets the cell's future state
	 */
	public void setFutureState(double state) {
		futureState = state;
	}
	/**
	 * Return future x position of cell
	 * 
	 * @return double futureX
	 */
	public int getFutureX() {
		return futureX;
	}
	/**
	 * Return future y position of cell
	 * 
	 * @return double futureY
	 */
	public int getFutureY() {
		return futureY;
	}
	/**
	 * Return parameters associated with the cell
	 * 
	 * @return Map<String,String> myParameters
	 */
	public Map<String, String> getParameters() {
		return myParameters;
	}
	/**
	 * Return double value of parameter being queried
	 * 
	 * @param String string representing the parameter being looked up in the parameter map
	 * @param double default value of the parameter if it isn't in the map
	 * @return double value to be used for the parameter in question
	 */
	protected double errorCheck(String string, double result) {
		if(myParameters.containsKey(string)) return Double.parseDouble(myParameters.get(string));
		else return result;
	}
	/**
	 * Abstract method that updates the state of the cell and moves it to a new location based on the 
	 * simulation's logic
	 * 
	 * Implements the simulation's logic to find its future State
	 * Then based on this state, it will either move or stay put
	 * 
	 * @param Patch[][] patches an array of the patches on the board
	 */
	public abstract void updateStateandMove(Patch[][] patches);

	/**
	 * Abstract method that sets the delta values when checking neighbors
	 * Varies for each simulation, as some require four neighbors, while others look at eight
	 */
	protected abstract void setDeltas();
	
	/**
	 * Abstract method that sets the initial values of parameters and other information in the cell
	 * Varies for each simulation, as they all have different parameters that need to be initialized
	 */
	protected abstract void initialize();
	
	/**
	 * Method that gets the neighboring patches of the current cell
	 * Can use either the finite or the toroidal definition of neighbors
	 * 
	 * @param Patch[][] patches array of the patches on the board
	 * @return List<Patch> list of neighboring patches
	 */
	protected List<Patch> getNeighbors(Patch[][] patches){
		List<Patch> neighborsList = new ArrayList<Patch>();
		switch(boundaryType){
		case "Finite":
			for(int k=0; k<xDelta.length;k++){
				if(currentX+xDelta[k]>=0 && currentX+xDelta[k] <patches.length
						&& currentY+yDelta[k] >= 0 && currentY+yDelta[k] <patches[0].length){
					neighborsList.add(patches[currentX+xDelta[k]][currentY+yDelta[k]]);
				}
			} break;
		case "Toroidal" :
			for(int k=0; k<xDelta.length;k++){
				int neighborX = (currentX+xDelta[k])%patches.length;
				int neighborY = (currentY+yDelta[k])%patches[0].length;
				if (neighborX<0) neighborX += patches.length;  //corrects for negative result
				if (neighborY<0) neighborY += patches[0].length;  //corrects for negative result
				neighborsList.add(patches[neighborX][neighborY]);
			}break;
		}
		return neighborsList;
	}
	/**
	 * Abstract method that sets the color of the cell based on the possible states
	 */
	public abstract Paint getColor();



}
