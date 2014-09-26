package Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Patch.Patch;
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
	protected String boundaryType;

	public Cell(double state, int x, int y, Map<String,String> parameters) {
		currentState = state;
		futureState = state;
		currentX = x;
		currentY = y;
		futureX = x;
		futureY = y;
		myParameters = parameters;
		setDeltas();
		boundaryType = parameters.get("boundary");
	}
	public Cell(Cell c){
		this(c.getFutureState(),c.getFutureX(), c.getFutureY(), c.getParameters());
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

	public Map<String, String> getParameters() {
		return myParameters;
	}

	public void setFutureY(int futureY) {
		this.futureY = futureY;
	}


	protected double errorCheck(String string, double result) {
		if(myParameters.containsKey(string)) return Double.parseDouble(myParameters.get(string));
		else return result;
	}

	public abstract void updateStateandMove(Patch[][] patches);

	protected abstract void setDeltas();

	public void currentToFuture(){
		currentX = futureX;
		currentY = futureY;
		currentState = futureState;
	}

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

	public abstract Paint getColor();

	public void setFutureState(double futureState) {
		this.futureState = futureState;
	}



}
