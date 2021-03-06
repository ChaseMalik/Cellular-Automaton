package Cell;

import java.util.List;
import java.util.Map;

import Patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FireCell extends Cell {
	
	double probCatch;
	private static final int burning = 1;
	private static final int notBurning = 0;
	private static final double DEFAULT_CATCH = 0.3;

	public FireCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
	}

	public FireCell(FireCell fireCell) {
		super(fireCell);
	}
	/**
	 * Cell's logic for the fire spreading simulation
	 * 
	 * @param Patch[][] patches on the grid
	 */
	@Override
	public void updateStateandMove(Patch[][] patches) {
		double wood = patches[currentX][currentY].getCurrentState();
		if (currentState==burning && wood>0)
			futureState = burning;
		else if(currentState==burning)
			futureState=notBurning;
		else if(currentState==notBurning && burningNeighbor(getNeighbors(patches)) && wood>0){
			double num = Math.random();
			if (num<=probCatch){
				futureState=burning;
			}
		}
		else futureState = notBurning;
		patches[currentX][currentY].setFutureCell(new FireCell(this));
	}
	/**
	 * Loops through the list of neighboring patches
	 * If one of them contains a burning cell then return true
	 * 
	 * @param List<Patch> neighboring patches to the current cell
	 * @return boolean returns true if the cell has a burning neighbor
	 */
	private boolean burningNeighbor(List<Patch> neighbors){
		for(Patch p:neighbors){
			if(p.getCurrentCell().getCurrentState()==burning)
				return true;
		}
		return false;
	}

	@Override
	public Paint getColor() {
		if(currentState == burning) return Color.RED;
		else return null;
	}


	@Override
	protected void setDeltas() {
		xDelta = new int[]{-1,0,0,1};
		yDelta = new int[]{0,-1,1,0};
		
	}

	@Override
	protected void initialize() {
		probCatch = errorCheck("probCatch", DEFAULT_CATCH);
	}
}
