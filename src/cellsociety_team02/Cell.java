package cellsociety_team02;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public abstract class Cell {
	
	protected double currentState;
	protected double futureState;
	protected int currentX;
	protected int currentY;
	protected int futureX;
	protected int futureY;
	protected List<Cell> neighborsList;
	protected Map<String,String> myParameters;
	
	public Cell(double state, int x, int y, Map<String,String> parameters) {
		currentState = state;
		futureState = state;
		currentX = x;
		currentY = y;
		futureX = x;
		futureY = y;
		neighborsList = new ArrayList<Cell>();
		myParameters = parameters;
	}
	
	public double getCurrentState(){
		return currentState;
	}
	
	public double getFutureState(){
		return futureState;
	}
	
	public int getCurrentX() {
		return currentX;
	}
	
	public int getCurrentY() {
		return currentY;
	}

	public int getFutureX() {
		return futureX;
	}

	public void setFutureX(int futureX) {
		this.futureX = futureX;
	}

	public int getFutureY() {
		return futureY;
	}

	public void setFutureY(int futureY) {
		this.futureY = futureY;
	}

	
	public abstract void updateStateandMove(Cell[][] cellList, Patch[][] patches);
	
	public void currentToFuture(){
		currentX = futureX;
		currentY = futureY;
		currentState = futureState;
	}
	
	protected abstract void getNeighbors(Cell[][] cellList);

	public abstract Paint getColor();

	public void setFutureState(double futureState) {
		this.futureState = futureState;
	}
	
}
