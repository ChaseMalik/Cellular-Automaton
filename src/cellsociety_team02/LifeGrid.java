package cellsociety_team02;

import java.util.Map;

import javafx.scene.paint.Color;

public class LifeGrid extends Grid {
	
	private static final int alive = 1;
	private static final int dead = 0;
	
	public LifeGrid(Map<String, String> parametersMap,
			int[][] initialCells, double[][] initialPatches) {
		super(parametersMap, initialCells, initialPatches);
		
	}

	@Override
	protected void updateCellandPatch(int r, int c) {
		int state = currentCells[r][c];
		int aliveNeighbors = getNeighbors(r,c, alive);
		if(state == dead && aliveNeighbors == 3)
			futureCells[r][c] = alive;
		else if(state == dead)
			return;
		if(aliveNeighbors <2)
			futureCells[r][c] = dead;
		else if(aliveNeighbors <= 3)
			futureCells[r][c] = alive;
		else
			futureCells[r][c] = dead;
	}

	@Override
	protected Color setColor(int i, int j) {
		int state = futureCells[i][j];
		if(state == alive) return Color.BLACK;
		else return Color.WHITE;

	}
	private int getNeighbors(int i, int j, int s) {
		int same = 0;
		if(i-1>=0 && j-1>=0 && currentCells[i-1][j-1] == s) same++;
		if(i-1>=0 && currentCells[i-1][j] == s) same++;
		if(i-1>=0 && j+1<currentCells[0].length && currentCells[i-1][j+1] == s) same++;
		if(j-1>=0 && currentCells[i][j-1] == s) same++;
		if(j+1<currentCells[0].length && currentCells[i][j+1] == s) same++;
		if(i+1<currentCells.length && j-1>=0 && currentCells[i+1][j-1] == s) same++;
		if(i+1<currentCells.length && currentCells[i+1][j] == s) same++;
		if(i+1<currentCells.length && j+1<currentCells[0].length && currentCells[i+1][j+1] == s) same++;

		return same;
	}


}
