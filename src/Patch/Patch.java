package Patch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Paint;
import Cell.Cell;

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
