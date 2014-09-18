package cellsociety_team02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;

public class PredPreyGrid extends Grid {
	
	/*
	 * STATES:
	 * 0 = empty
	 * 1 = fish
	 * 2 = shark
	 * 
	 * Needs heavy re-factoring
	 * 
	 */
	
	private int[][] fishState;
	private int[][] sharkState;
	private int[][] sharkStarve;
	private int fbreed;
	private int sbreed;
	private int starve;
	private int state;

	public PredPreyGrid(Map<String, String> parametersMap, int[][] initialCells, double[][] initialPatches) {
		super(parametersMap, initialCells, initialPatches);
		fbreed = Integer.parseInt(map.get("fishBreed"));
		sbreed = Integer.parseInt(map.get("sharkBreed"));
		starve = Integer.parseInt(map.get("sharkStarve"));
		fishState = new int[initialCells.length][initialCells[0].length];
		sharkState = new int[initialCells.length][initialCells[0].length];
		sharkStarve = new int[initialCells.length][initialCells[0].length];
		for (int i = 0; i<fishState.length;i++) {
			for (int j = 0; j<fishState[0].length;j++) {
				fishState[i][j] = 0;
				sharkState[i][j] = 0;
				sharkStarve[i][j] = 0;
			}
		}
			
	}
	
	@Override
	protected void setColors() {
		colorMap.put(0, Color.ROYALBLUE);
		colorMap.put(1, Color.LIMEGREEN);
		colorMap.put(2, Color.RED);
	}

	@Override
	protected void updateCellandPatch(int r, int c) {
		state = currentCells[r][c];
		if (state == 0) return;
		if (sharkStarve[r][c] >= starve){
			sharkStarve[r][c] = 0;
			futureCells[r][c] = 0;
			return;
		}
		ArrayList<newMove> possibleMoves = getMoves(r,c);
		int random = (int)(Math.random()*possibleMoves.size());
		if (possibleMoves.size() == 0) {
			if (state == 1) {
				fishState[r][c]++;
			}
			if (state == 2) {
				sharkState[r][c]++;
				sharkStarve[r][c]++;
			}
			return;
		}
		newMove nextMove = possibleMoves.get(random);
		nextMove.doNextMove(r,c);
	}
	
	public class newMove {
		private int newR;
		private int newC;
		public newMove(int r, int c, int s) {
			newR = r;
			newC = c;
		}
		public void doNextMove(int currentR, int currentC) {
			futureCells[newR][newC] = currentCells[currentR][currentC];
			if (state == 1) {
				if (fishState[currentR][currentC] >= fbreed){
					futureCells[currentR][currentC] = 1;
					fishState[currentR][currentC] = 0;
					fishState[newR][newC] = 0;
				}
				else{
					futureCells[currentR][currentC] = 0;
					fishState[newR][newC] = fishState[currentR][currentC]+1;
				}
				fishState[currentR][currentC] = 0;	
			}
			if (state == 2) {
				if (sharkState[currentR][currentC] >= sbreed){
					futureCells[currentR][currentC] = 2;
					sharkState[currentR][currentC] = 0;
					sharkState[newR][newC] = 0;
				}
				else{
					futureCells[currentR][currentC] = 0;
					sharkState[newR][newC] = sharkState[currentR][currentC]+1;
				}
				
				if (currentCells[newR][newC] == 1) {
					sharkStarve[newR][newC] = 0;
					currentCells[newR][newC] = 0;
					fishState[newR][newC] = 0;
				}
				else {
					sharkStarve[newR][newC] = sharkStarve[currentR][currentC]+1;
				}
				sharkState[currentR][currentC] = 0;
				sharkStarve[currentR][currentC] = 0;
			}
		}
	}
	/*
	private int checkNeighbor(int r, int c) {
		if (r>=currentCells.length && r<0 && c>=currentCells[0].length && c<0)
			return -1;
		else
			return currentCells[r][c];
	}
	*/
	private ArrayList<newMove> getMoves(int r, int c){
		boolean fishAvailable = false;
		ArrayList<newMove> moves = new ArrayList<newMove>(); 
		
		//If shark, check for available fish spaces
		if (state == 2) {
			if (r+1<currentCells.length && currentCells[r+1][c] == 1 && futureCells[r+1][c] == 1){
				moves.add(new newMove(r+1, c, currentCells[r+1][c]));
				fishAvailable = true;
			}
			if (r-1>=0 && currentCells[r-1][c] == 1 && futureCells[r-1][c] == 1){
				moves.add(new newMove(r-1, c, currentCells[r-1][c]));
				fishAvailable = true;
			}
			if (c+1<currentCells[0].length && currentCells[r][c+1] == 1 && futureCells[r][c+1] == 1){
				moves.add(new newMove(r, c+1, currentCells[r][c+1]));
				fishAvailable = true;
			}
			if (c-1>=0 && currentCells[r][c-1] == 1 && futureCells[r][c-1] == 1){
				moves.add(new newMove(r, c-1, currentCells[r][c-1]));
				fishAvailable = true;
			}
			if (fishAvailable)
				return moves;
		}
		
		//Else, check for blank spaces (for fish and sharks)
		if (r+1<currentCells.length && currentCells[r+1][c] == 0 && futureCells[r+1][c] == 0){
			moves.add(new newMove(r+1, c, currentCells[r+1][c]));
		}
		if (r-1>=0 && currentCells[r-1][c] == 0 && futureCells[r-1][c] == 0) {
			moves.add(new newMove(r-1, c, currentCells[r-1][c]));
		}
		if (c+1<currentCells[0].length && currentCells[r][c+1] == 0 && futureCells[r][c+1] == 0){
			moves.add(new newMove(r, c+1, currentCells[r][c+1]));
		}
		if (c-1>=0 && currentCells[r][c-1] == 0 && futureCells[r][c-1] == 0){
			moves.add(new newMove(r, c-1, currentCells[r][c-1]));
		}
		
		return moves;
	}

}
