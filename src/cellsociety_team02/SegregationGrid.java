package cellsociety_team02;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SegregationGrid extends Grid {

	double threshold;
	public SegregationGrid(Map<String, String> parametersMap,
			int[][] initialStates) {
		super(parametersMap, initialStates);
		threshold = Double.parseDouble(parametersMap.get("threshold"));

	}

	@Override
	protected void updateCell(int i, int j) {
		int state = current[i][j];
		if(state == 0) return;
		List<Point2D> possibleDest = new ArrayList<>();
		int notState;
		if(state == 1){notState = 2;}
		else{notState = 1;}
		double sameNeighbor = getNeighbors(i,j, state);
		double difNeighbor = getNeighbors(i,j, notState);
		if((sameNeighbor+difNeighbor != 0) && sameNeighbor/(difNeighbor+sameNeighbor) > threshold){
			future[i][j] = current[i][j];
		}
		else{
			for(int r=0;r<current.length;r++){
				for(int c=0;c<current[0].length;c++){
					if((current[r][c] == 0) && (future[r][c] == 0)){
						possibleDest.add(new Point2D.Double(r,c));
					}
				}
			}
			int index = new Random().nextInt(possibleDest.size());
			int x = (int) possibleDest.get(index).getX();
			int y = (int) possibleDest.get(index).getY();
			future[x][y] = state;
			future[i][j] = 0;
			return;
		}



	}

	private double getNeighbors(int i, int j, int s) {
		double same = 0;
		if(i-1>0 && j-1>0 && current[i-1][j-1] == s) same++;
		if(i-1>0 && current[i-1][j] == s) same++;
		if(i-1>0 && j+1<current[0].length && current[i-1][j+1] == s) same++;
		if(j-1>0 && current[i][j-1] == s) same++;
		if(j+1<current[0].length && current[i][j+1] == s) same++;
		if(i+1<current.length && j-1>0 && current[i+1][j-1] == s) same++;
		if(i+1<current.length && current[i+1][j] == s) same++;
		if(i+1<current.length && j+1<current[0].length && current[i+1][j+1] == s) same++;

		return same;
	}

}
