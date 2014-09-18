package cellsociety_team02;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;

public class SegregationGrid extends Grid {
	private static final int stateX = 1;
	private static final int stateY = 2;

	double threshold;
	public SegregationGrid(Map<String, String> parametersMap, int[][] initialCells, double[][] initialPatches) {
		super(parametersMap, initialCells, initialPatches);
		threshold = Double.parseDouble(parametersMap.get("threshold"));

	}

	@Override
	protected void updateCellandPatch(int i, int j) {
		if(currentCells[i][j] == 0) return;

		int state = currentCells[i][j];
		double xNeighbors = getNeighbors(i,j, stateX);
		double yNeighbors = getNeighbors(i,j, stateY);
		
		if(xNeighbors+yNeighbors == 0){
			move(i,j);
			return;
		}
		
		double xRatio = xNeighbors/(xNeighbors+yNeighbors);
		double yRatio = yNeighbors/(xNeighbors+yNeighbors);
		if(!(state==stateX && xRatio>threshold) && !(state==stateY && yRatio>threshold)){
			move(i,j);
		}
		else{
			futureCells[i][j] = state;
		}
	}

	private void move(int i, int j) {
		List<Point2D> possibleDest = new ArrayList<>();
		for(int r=0;r<currentCells.length;r++){
			for(int c=0;c<currentCells[0].length;c++){
				if((currentCells[r][c] == 0) && (futureCells[r][c] == 0)){
					possibleDest.add(new Point2D.Double(r,c));
				}
			}
		}
		int index = new Random().nextInt(possibleDest.size());
		int x = (int) possibleDest.get(index).getX();
		int y = (int) possibleDest.get(index).getY();
		futureCells[x][y] = currentCells[i][j];
		futureCells[i][j] = 0;
		return;
	}

	private double getNeighbors(int i, int j, int s) {
		double same = 0;
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

	@Override
	protected void setColors() {
		colorMap.put(0, Color.WHITE);
		colorMap.put(1, Color.RED);
		colorMap.put(2, Color.BLUE);
	}

}
