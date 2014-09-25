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
	private static final double minWood = .9;
	private Patch[][] patchArray;

	public FireCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
		probCatch = Double.parseDouble(parameters.get("probCatch"));
		xDelta = new int []{-1,0,0,1};
		yDelta = new int []{0,-1,1,0};
	}

	@Override
	public void updateStateandMove(Cell[][] cellList, Patch[][] patches) {
		patchArray=patches;
		double wood = patchArray[currentX][currentY].getCurrentState();
		getNeighbors(cellList);
		if (currentState==burning && wood>minWood)
			patchArray[currentX][currentY].setFutureState(wood*=.8);
		else if(currentState==burning){
			futureState=notBurning;
			patchArray[currentX][currentY].setFutureState(0);
		}
		else if(currentState==notBurning && burningNeighbor(getNeighbors(cellList)) && wood>0){
			double num = Math.random();
			if (num<=probCatch)
				futureState=burning;
		}
	}
	
	private boolean burningNeighbor(List<Cell> neighbors){
		for(Cell c:neighbors)
			if(c.currentState==burning)
				return true;
		return false;
	}

	@Override
	public Paint getColor(Patch patch) {
		if(futureState == burning) return Color.RED;
		else return colorOfPatch(patch.getCurrentState());
	}

	private Paint colorOfPatch(double wood) {
		if(wood>=2.0) return Color.DARKGREEN;
		else if(wood>=1.0) return Color.GREEN;
		else if(wood>0.0) return Color.LIGHTGREEN;
		else return Color.YELLOW;
	}

	@Override
	protected void setDeltas() {
		xDelta = new int[]{-1,0,0,1};
		yDelta = new int[]{0,-1,1,0};
		
	}
}
