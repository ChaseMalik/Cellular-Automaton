package cellsociety_team02;

import java.util.List;
import java.util.Map;

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
	}

	@Override
	public void updateStateandMove(Cell[][] cellList, Patch[][] patches) {
		patchArray=patches;
		Patch wood = patchArray[currentX][currentY];
		getNeighbors(cellList);
		if (currentState==burning && wood.getCurrentState()>minWood)
			wood.setFutureState(wood.currentState*=.8);
		else if(currentState==burning){
			futureState=notBurning;
			wood.setFutureState(0);
		}
		else if(currentState==notBurning && burningNeighbor(neighborsList) && wood.getCurrentState()>0){
			double num = Math.random();
			if (num<=probCatch)
				futureState=burning;
		}
	}

	@Override
	protected void getNeighbors(Cell[][] cellList) {
		neighborsList.clear();
		int[] xDelta = {-1, 0, 0, 1,};
		int[] yDelta = {0, 1, -1, 0};

		for(int k=0; k<xDelta.length;k++){
			if(currentX+xDelta[k]>=0 && currentX+xDelta[k] <cellList.length
					&& currentY+yDelta[k] >= 0 && currentY+yDelta[k] <cellList[0].length){
				neighborsList.add(cellList[currentX+xDelta[k]][currentY+yDelta[k]]);
			}
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
}
