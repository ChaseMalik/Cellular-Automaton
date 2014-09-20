package cellsociety_team02;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public abstract class Cell {
	
	protected double currentState;
	protected double futureState;
	protected double currentX;
	protected double currentY;
	protected double futureX;
	protected double futureY;
	protected List<Cell> neighborsList;
	protected Color myColor;
	
	public Cell(double state, double x, double y) {
		currentState = state;
		futureState = state;
		currentX = x;
		currentY = y;
		futureX = x;
		futureY = y;
		neighborsList = new ArrayList<Cell>();
	}
	
	public double getCurrentState(){
		return currentState;
	}
	
	public double getFutureState(){
		return futureState;
	}
	
	public double getCurrentX() {
		return currentX;
	}
	
	public double getCurrentY() {
		return currentY;
	}

	public double getFutureX() {
		return futureX;
	}

	public void setFutureX(double futureX) {
		this.futureX = futureX;
	}

	public double getFutureY() {
		return futureY;
	}

	public void setFutureY(double futureY) {
		this.futureY = futureY;
	}

	
	public abstract void updateStateandMove(List<Cell> cellList);
	
	public void currentToFuture(){
		currentX = futureX;
		currentY = futureY;
		currentState = futureState;
	}
	
	protected abstract void getNeighbors(List<Cell> cellList);

	public Paint getColor() {
		return myColor;
	}
	
}
