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

	public FireCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
		probCatch = Double.parseDouble(parameters.get("probCatch"));
	}

	@Override
	public void updateStateandMove(Cell[][] cellList, Patch[][] patches) {
		double wood = patches[currentX][currentY].getCurrentState();
		getNeighbors(cellList);
		if (currentState==burning && wood>0)
			futureState = burning;
		else if(currentState==burning)
			futureState=notBurning;
		else if(currentState==notBurning && burningNeighbor(getNeighbors(cellList)) && wood>0){
			double num = Math.random();
			if (num<=probCatch)
				futureState=burning;
		}
		else futureState = notBurning;
	}
	
	private boolean burningNeighbor(List<Cell> neighbors){
		for(Cell c:neighbors)
			if(c.currentState==burning)
				return true;
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
}
