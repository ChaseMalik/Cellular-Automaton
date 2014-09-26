package Patch;

import java.util.Map;

import javafx.scene.paint.Paint;
import Cell.Cell;

/**
 * 
 * 
 * @author Greg Lyons
 * @author Chase Malik
 * @author Kevin Rhine
 * 
 * The Patch class is the basis for the grid
 * Each Patch has its own state, and it holds a Cell that also has its own state
 * 
 * Patches update by checking the grid based on the current state and changing a future state
 * Each Patch updates its given Cell - Cells can manipulate the Patch's futureCell
 *
 *GridModel loops over the array of Patches and calls each one's updateState method
 *Each of the Patches updates its Cell from within the updateState method
 *
 *In this sense, the Patches serve as locations for the Cells to inhabit
 *The two objects are able to interact with each other depending on the nature of the simulation
 *
 */

public abstract class Patch {
	protected double currentState;
	protected double futureState;
	protected int myX;
	protected int myY;
	protected Cell myCurrentCell;
	protected Cell myFutureCell;
	protected Map<String, String> myParameters;
	
	public Patch(Cell c, double state, int x, int y, Map<String, String> params) {
		myCurrentCell = c;
		myFutureCell = c;
		currentState = state;
		futureState = state;
		myX = x;
		myY = y;
		myParameters = params;
	}
	
	public double getCurrentState(){
		return currentState;
	}
	
	public double getFutureState(){
		return futureState;
	}
	
	public Cell getCurrentCell(){
		return myCurrentCell;
	}
	
	public Cell getFutureCell(){
		return myFutureCell;
	}
	public void setFutureCell(Cell c){
		myFutureCell = c;
	}
	
	public int getCurrentY() {
		return myY;
	}
	public int getCurrentX() {
		return myX;
	}
	
	public void setFutureState(double futureState) {
		this.futureState = futureState;
	}
	
	public abstract void updateState(Patch[][] patches);

	public abstract Paint getColor();
	
	public void currentToFuture(){
		currentState = futureState;
		myCurrentCell = myFutureCell;
	}
}
