package Patch;

import java.util.List;

import Cell.Cell;

public abstract class Patch {
	protected double currentState;
	protected double futureState;
	protected double myX;
	protected double myY;
	
	public Patch(double state, double x, double y) {
		currentState = state;
		futureState = state;
		myX = x;
		myY = y;
	}
	
	public double getCurrentState(){
		return currentState;
	}
	
	public double getFutureState(){
		return futureState;
	}
	
	public double getCurrentY() {
		return myY;
	}
	
	public void setFutureState(double futureState) {
		this.futureState = futureState;
	}
	
	public abstract void updateState(Cell cell);
}
