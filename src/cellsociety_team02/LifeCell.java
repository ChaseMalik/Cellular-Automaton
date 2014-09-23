package cellsociety_team02;


import java.util.Map;

import javafx.scene.paint.Color;

public class LifeCell extends Cell {

	private static final int alive = 1;
	private static final int dead = 0;

	public LifeCell(double state, int x, int y, Map<String,String> parameters) {
		super(state, x, y, parameters);
	}

	@Override
	public void updateStateandMove(Cell[][] cellList, Patch[][] patches) {
		double state = currentState;
		getNeighbors(cellList);
		int aliveNeighbors = aliveNeighbors();
		if(state == dead && aliveNeighbors == 3)
			futureState = alive;
		else if(state == dead)
			return;
		if(aliveNeighbors <2)
			futureState = dead;
		else if(aliveNeighbors <= 3)
			futureState = alive;
		else
			futureState = dead;
	}

	@Override
	protected void getNeighbors(Cell[][] cellList) {
		neighborsList.clear();
		int[] xDelta = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] yDelta = {-1, 0, 1, -1, 1, -1, 0, 1};

		for(int k=0; k<xDelta.length;k++){
			if(currentX+xDelta[k]>=0 && currentX+xDelta[k] <cellList.length
					&& currentY+yDelta[k] >= 0 && currentY+yDelta[k] <cellList[0].length){
				neighborsList.add(cellList[currentX+xDelta[k]][currentY+yDelta[k]]);
			}
		}
	}

	private int aliveNeighbors() {
		int result = 0;
		for (Cell c: neighborsList){
			if (c.getCurrentState() == alive)
				result++;
		}
		return result;
	}

	@Override
	public Color getColor() {

		if (futureState == 0)
			myColor = Color.WHITE;
		else
			myColor = Color.BLACK;
		return myColor;
	}

}
