package cell;

import java.util.List;
import java.util.Map;

import patch.Patch;
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
		double wood = patches[myCurrentX][myCurrentY].getCurrentState();
		if (myCurrentState==burning && wood>0)
			myFutureState = burning;
		else if(myCurrentState==burning)
			myFutureState=notBurning;
		else if(myCurrentState==notBurning && burningNeighbor(getNeighbors(patches)) && wood>0){
			double num = Math.random();
			if (num<=probCatch){
				myFutureState=burning;
			}
		}
		else myFutureState = notBurning;
		patches[myCurrentX][myCurrentY].setFutureCell(new FireCell(this));
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
		if(myCurrentState == burning) return Color.RED;
		else return null;
	}


	@Override
	protected void setDeltas() {
		myXDelta = new int[]{-1,0,0,1};
		myYDelta = new int[]{0,-1,1,0};
		
	}

	@Override
	protected void initialize() {
		probCatch = errorCheck("probCatch", DEFAULT_CATCH);
	}
}
