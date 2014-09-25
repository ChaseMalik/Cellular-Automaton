package Cell;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Patch.Patch;

public class PredPreyCell extends Cell {

	public static final double WATER = 0;
	public static final double FISH = 1;
	public static final double SHARK = 2;
	public static final int INITIAL_CHRONONS = 0;
	public static final int INITIAL_HUNGER = 0;
	
	protected PredPreyCell futureCell;
	protected int myChronons;
	protected int myBreed;

	public PredPreyCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
		myChronons = INITIAL_CHRONONS;
		futureCell = this;
	}

	@Override
	public void updateStateandMove(Cell[][] cells, Patch[][] patches) {
		if (currentState == WATER)
			return;
	}


	public Cell makeCell() {
		switch((int)currentState) {
		case (int)WATER: return new PredPreyCell(currentState, currentX, currentY, myParameters);
		case (int)FISH: return new FishCell(currentState, currentX, currentY, myParameters, INITIAL_CHRONONS); 
		case (int)SHARK: return new SharkCell(currentState, currentX, currentY, myParameters, INITIAL_CHRONONS, INITIAL_HUNGER);
		default: return null;
		}
	}

	@Override
	protected void setDeltas() {
		xDelta = new int[]{-1,0,0,1};
		yDelta = new int[]{0,-1,1,0};
	}

	protected void setNewStates(double newState, int newChronons, int newBreed) {
		futureState = newState;
		myChronons = newChronons;
		myBreed = newBreed;
		
	}
	
	public void setFutureCell(PredPreyCell c) {
		futureCell = c;
	}
	
	public PredPreyCell getFutureCell() {
		return futureCell;
	}
	
	@Override
	public Paint getColor(Patch patch) {
		if (currentState == SHARK)
			return Color.RED;
		if (currentState == FISH)
			return Color.LIMEGREEN;		
		else
			return Color.ROYALBLUE;
		
	}

}
