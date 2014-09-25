package Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


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
	
	public Cell(double state, int x, int y, Map<String,String> parameters) {
		currentState = state;
		futureState = state;
		currentX = x;
		currentY = y;
		futureX = x;
		futureY = y;
		myParameters = parameters;
		setDeltas();
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
	
	protected abstract void setDeltas();
	
	public void currentToFuture(){
		currentX = futureX;
		currentY = futureY;
		currentState = futureState;
	}
	
	protected List<Cell> getNeighbors(Cell[][] cellList){
		List<Cell> neighborsList = new ArrayList<Cell>();
		for(int k=0; k<xDelta.length;k++){
			if(currentX+xDelta[k]>=0 && currentX+xDelta[k] <cellList.length
					&& currentY+yDelta[k] >= 0 && currentY+yDelta[k] <cellList[0].length){
				neighborsList.add(cellList[currentX+xDelta[k]][currentY+yDelta[k]]);
			}
		}
		return neighborsList;
	}

	public abstract Paint getColor(Patch patch);

	public void setFutureState(double futureState) {
		this.futureState = futureState;
	}
	
	
	
}
