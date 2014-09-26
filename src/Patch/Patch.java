package Patch;

import java.util.Map;

import javafx.scene.paint.Paint;
import Cell.Cell;

public abstract class Patch {
	protected double currentState;
	protected double futureState;
	protected double myX;
	protected double myY;
	protected Cell myCurrentCell;
	protected Cell myFutureCell;
	protected Map<String, String> myParameters;
	
	public Patch(Cell c, double state, double x, double y, Map<String, String> params) {
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
	
	public double getCurrentY() {
		return myY;
	}
	public double getCurrentX() {
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
