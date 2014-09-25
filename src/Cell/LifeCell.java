package Cell;


import java.util.List;
import java.util.Map;

import Patch.Patch;
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
		int aliveNeighbors = aliveNeighbors(getNeighbors(cellList));
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


	private int aliveNeighbors(List<Cell> neighborsList) {
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
			return Color.WHITE;
		else
			return Color.BLACK;
	}

	@Override
	protected void setDeltas() {
		xDelta = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
		yDelta = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
	}

}
