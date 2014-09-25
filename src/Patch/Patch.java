package Patch;

import java.util.Map;

import javafx.scene.paint.Paint;
import Cell.Cell;

public abstract class Patch {
	protected double currentState;
	protected double futureState;
	protected double myX;
	protected double myY;
	protected Map<String, String> myParameters;
	
	public Patch(double state, double x, double y, Map<String, String> params) {
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
	
	public double getCurrentY() {
		return myY;
	}
	public double getCurrentX() {
		return myX;
	}
	
	public void setFutureState(double futureState) {
		this.futureState = futureState;
	}
	
	public abstract void updateState(Cell cell);

	public abstract Paint getColor();
	
	public void currentToFuture(){
		currentState = futureState;
	}
}
