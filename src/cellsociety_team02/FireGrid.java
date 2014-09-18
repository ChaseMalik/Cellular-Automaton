package cellsociety_team02;

import java.util.Map;

import javafx.scene.paint.Color;

public class FireGrid extends Grid {
	
	double probCatch;

	public FireGrid(Map<String, String> parametersMap, int[][] initialCells, double[][] initialPatches) {
		super(parametersMap, initialCells, initialPatches);
		probCatch = Double.parseDouble(parametersMap.get("percent"));
	}

	@Override
	protected void updateCellandPatch(int i, int j) {
		int burnStatus = currentCells[i][j];
		double wood = currentPatches[i][j];
		if (burnStatus==2 && wood>0)
			wood *= .8;
		else if(burnStatus==2 && wood<.6){
			burnStatus=1;
			wood = 0;
		}
		else if(burnStatus==0 && burningNeighbor(i,j)){
			double num = Math.random();
			if (num<probCatch)
				burnStatus=2;
		}
		futureCells[i][j]=burnStatus;
		futurePatches[i][j]=wood;
	}
	
	private boolean burningNeighbor(int r, int c){
		boolean North=false, East=false, South=false, West=false;
		if(r+1<currentCells.length) East = (currentCells[r+1][c]==2);
		if(r-1>0) West = (currentCells[r-1][c]==2);
		if(c+1<currentCells[0].length) South = (currentCells[r][c+1]==2);
		if(c-1>0) North = (currentCells[r][c-1]==2);
		return (North||East||South||West);
	}

	@Override
	protected void setColors() {
		colorMap.put(0, Color.GREEN);
		colorMap.put(1, Color.YELLOW);
		colorMap.put(2, Color.RED);
	}
}
