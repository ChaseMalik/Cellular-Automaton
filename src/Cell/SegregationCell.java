package cell;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SegregationCell extends Cell{

	private static final int empty = 0;
	private static final int stateX = 1;
	private static final int stateY = 2;
	private static final String THRESHOLD = "threshold";
	private static final double DEFAULT_THRESHOLD = 0.3;
	private double myThreshold;
	
	public SegregationCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
	}

	public SegregationCell(SegregationCell segregationCell) {
		super(segregationCell);
	}

	@Override
	public void updateStateandMove(Patch[][] patches) {
		double state = myCurrentState;
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
		if(!(state==stateX && xRatio>=myThreshold) && !(state==stateY && yRatio>=myThreshold)){
			move(patches);
		}
		else myFutureState = myCurrentState;
	}
	/**
	 * Moves the current cell to a random empty cell
	 * Finds possible destinations
	 * Then it randomly chooses one of these patches and moves there 
	 * by setting its future cell equal to this and setting this locations current cell equal to
	 * an empty cell
	 * 
	 * @param Patch[][] patches array of all patches on the grid
	 */
	private void move(Patch[][] patches) {
		List<Point2D> possibleDest = getPossibleDestination(patches);
		int index = new Random().nextInt(possibleDest.size());
		myFutureX = (int) possibleDest.get(index).getX();
		myFutureY = (int) possibleDest.get(index).getY();
		patches[myFutureX][myFutureY].setFutureCell(new SegregationCell(this));
		patches[myCurrentX][myCurrentY].setFutureCell(new SegregationCell(empty,myCurrentX,myCurrentY,myParameters));
	}

	private List<Point2D> getPossibleDestination(Patch[][] patches) {
		List<Point2D> possibleDest = new ArrayList<>();
		for(int r=0;r<patches.length;r++){
			for(int c=0;c<patches[0].length;c++){
				if((patches[r][c].getFutureCell().getCurrentState() == empty)){
					possibleDest.add(new Point2D.Double(r,c));
				}
			}
		}
		return possibleDest;
	}

	@Override
	public Paint getColor() {
		if(myFutureState == stateX) return Color.RED;
		else if(myFutureState == stateY) return Color.BLUE;
		else return Color.WHITE;
	}

	@Override
	protected void setDeltas() {
		myXDelta = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
		myYDelta = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
	}

	@Override
	protected void initialize() {
		myThreshold = errorCheck(THRESHOLD,DEFAULT_THRESHOLD);
	}

}
