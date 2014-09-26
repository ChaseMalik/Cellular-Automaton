package Cell;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SegregationCell extends Cell{

	private static final int empty = 0;
	private static final int stateX = 1;
	private static final int stateY = 2;
	private static final String THRESHOLD = "threshold";
	private static final double DEFAULT_THRESHOLD = 0.3;
	private double threshold;
	
	public SegregationCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
	}

	public SegregationCell(SegregationCell segregationCell) {
		super(segregationCell);
	}

	@Override
	public void updateStateandMove(Patch[][] patches) {
		double state = currentState;
		if(state == empty) return;
		double xNeighbors = 0;
		double yNeighbors = 0;
		
		for(Patch p: getNeighbors(patches)){
			int neighborState = (int) p.getCurrentCell().getCurrentState();
			if(neighborState == stateX) xNeighbors++;
			else if(neighborState == stateY) yNeighbors++;
		}
		
		if(xNeighbors + yNeighbors == 0){move(patches); return;}
		double xRatio = xNeighbors/(xNeighbors+yNeighbors);
		double yRatio = yNeighbors/(xNeighbors+yNeighbors);
		if(!(state==stateX && xRatio>=threshold) && !(state==stateY && yRatio>=threshold)){
			move(patches);
		}
		else futureState = currentState;
	}
	/**
	 * Moves the current cell to a random empty cell
	 * Loops through the list of all patches and finds ones which do not have cells in the future
	 * Then it randomly chooses one of these patches and moves there 
	 * by setting its future cell equal to this and setting this locations current cell equal to
	 * an empty cell
	 * 
	 * @param Patch[][] patches array of all patches on the grid
	 */
	private void move(Patch[][] patches) {
		List<Point2D> possibleDest = new ArrayList<>();
		for(int r=0;r<patches.length;r++){
			for(int c=0;c<patches[0].length;c++){
				if((patches[r][c].getFutureCell().getCurrentState() == empty)){ //(cellList[r][c].getCurrentState() == 0) && 
					possibleDest.add(new Point2D.Double(r,c));
				}
			}
		}
		int index = new Random().nextInt(possibleDest.size());
		int x = (int) possibleDest.get(index).getX();
		int y = (int) possibleDest.get(index).getY();
		futureX = x;
		futureY = y;
		patches[x][y].setFutureCell(new SegregationCell(this));
		patches[currentX][currentY].setFutureCell(new SegregationCell(empty,currentX,currentY,myParameters));
	}

	@Override
	public Paint getColor() {
		if(futureState == stateX) return Color.RED;
		else if(futureState == stateY) return Color.BLUE;
		else return Color.WHITE;
	}

	@Override
	protected void setDeltas() {
		xDelta = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
		yDelta = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
	}

	@Override
	protected void initialize() {
		threshold = errorCheck(THRESHOLD,DEFAULT_THRESHOLD);
	}

}
