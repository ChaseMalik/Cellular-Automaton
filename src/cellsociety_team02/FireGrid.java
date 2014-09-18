package cellsociety_team02;

import java.util.Map;

import javafx.scene.paint.Color;

public class FireGrid extends Grid {
	private static final int burning = 1;
	private static final int notBurning = 0;
	private static final double minWood = 1;
	
	double probCatch;

	public FireGrid(Map<String, String> parametersMap, int[][] initialCells, double[][] initialPatches) {
		super(parametersMap, initialCells, initialPatches);
		probCatch = Double.parseDouble(parametersMap.get("probCatch"));
	}

	@Override
	protected void updateCellandPatch(int i, int j) {
		int burnStatus = currentCells[i][j];
		double wood = currentPatches[i][j];
		if (burnStatus==burning && wood>minWood)
			wood *= .8;
		else if(burnStatus==burning){
			burnStatus=notBurning;
			wood = 0;
		}
		else if(burnStatus==notBurning && burningNeighbor(i,j) && wood>0){
			double num = Math.random();
			if (num<probCatch)
				burnStatus=burning;
		}
		futureCells[i][j]=burnStatus;
		futurePatches[i][j]=wood;
	}
	
	private boolean burningNeighbor(int r, int c){
		boolean North=false, East=false, South=false, West=false;
		if(r+1<currentCells.length) East = (currentCells[r+1][c]==burning);
		if(r-1>=0) West = (currentCells[r-1][c]==burning);
		if(c+1<currentCells[0].length) South = (currentCells[r][c+1]==burning);
		if(c-1>=0) North = (currentCells[r][c-1]==burning);
		return (North||East||South||West);
	}

	@Override
	protected Color setColor(int i, int j) {
		if(currentCells[i][j] == burning) return Color.RED;
		else return colorOfPatch(i,j);
	}

	private Color colorOfPatch(int i, int j) {
		if(futurePatches[i][j]>=2) return Color.DARKGREEN;
		else if(futurePatches[i][j]>=1) return Color.GREEN;
		else if(futurePatches[i][j]>0) return Color.LIGHTGREEN;
		else return Color.YELLOW;
	}
}
