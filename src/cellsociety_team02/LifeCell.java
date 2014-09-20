package cellsociety_team02;

import java.util.List;

public class LifeCell extends Cell {
	
	private static final int alive = 1;
	private static final int dead = 0;

	public LifeCell(double state, double x, double y) {
		super(state, x, y);
	}

	@Override
	public void updateStateandMove(List<Cell> cellList) {
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
	protected void getNeighbors(List<Cell> cellList) {
		for (Cell c: cellList) {
			double x = c.getCurrentX();
			double y = c.getCurrentY();
			if ((x == currentX-1 && y == currentY-1)||
				(x == currentX-1 && y == currentY)||
				(x == currentX-1 && y == currentY+1)||
				(x == currentX && y == currentY-1)||
				(x == currentX && y == currentY+1)||
				(x == currentX+1 && y == currentY-1)||
				(x == currentX+1 && y == currentY)||
				(x == currentX+1 && y == currentY+1))
					neighborsList.add(c);
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

}
