package cell;


import java.util.List;
import java.util.Map;

import patch.Patch;
import javafx.scene.paint.Color;

public class LifeCell extends Cell {

	private static final int alive = 1;
	private static final int dead = 0;

	public LifeCell(double state, int x, int y, Map<String,String> parameters) {
		super(state, x, y, parameters);
	}

	public LifeCell(LifeCell lifeCell) {
		super(lifeCell);
	}

	@Override
	public void updateStateandMove(Patch[][] patches) {
		double state = myCurrentState;
		int aliveNeighbors = aliveNeighbors(getNeighbors(patches));
		if(state == dead && aliveNeighbors == 3)
			myFutureState = alive;
		else if(state == dead)
			return;
		if(aliveNeighbors <2)
			myFutureState = dead;
		else if(aliveNeighbors <= 3)
			myFutureState = alive;
		else
			myFutureState = dead;
		patches[myCurrentX][myCurrentY].setFutureCell(new LifeCell(this));
	}

	/**
	 * Loops through the list of neighboring patches
	 * Returns the number of live neighbors
	 * 
	 * @param List<Patch> neighboring patches to the current cell
	 * @return int returns the number of live neighbors
	 */
	private int aliveNeighbors(List<Patch> neighborsList) {
		int result = 0;
		for (Patch p: neighborsList){
			if (p.getCurrentCell().getCurrentState() == alive)
				result++;
		}
		return result;
	}

	@Override
	public Color getColor() {
		if (myFutureState == dead)
			return Color.WHITE;
		else
			return Color.BLACK;
	}

	@Override
	protected void setDeltas() {
		myXDelta = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
		myYDelta = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
	}

	@Override
	protected void initialize() {
		//nothing
	}

}
