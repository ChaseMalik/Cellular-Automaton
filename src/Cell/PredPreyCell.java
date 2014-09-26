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
	protected int myChronons;
	protected int myBreed;

	public PredPreyCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
	}

	public PredPreyCell(PredPreyCell c){
		super(c);
	}

	public void updateStateandMove(Patch[][] patches) {
		//overridden by subclasses for sharks and fish, does nothing for water
		if (currentState == WATER)
			return;
	}

	protected Patch pickMove(List<Patch> moves) {
		int random = (int)(Math.random()*moves.size());
		Patch nextMove = moves.get(random);
		futureX = (int)nextMove.getCurrentX();
		futureY = (int)nextMove.getCurrentY();
		return nextMove;
	}

	protected void checkBreed(Patch[][] patches) {
		if (myChronons >= myBreed){
			breed(patches);
			myChronons = INITIAL_CHRONONS;
		}
		else{
			patches[currentX][currentY].setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
			myChronons++;
		}
	}

	protected void breed(Patch[][] patches){
		//overridden by subclasses
	}

	public Cell makeCell() {
		switch((int)currentState) {
		case (int)WATER: return new PredPreyCell(currentState, currentX, currentY, myParameters);
		case (int)FISH: return new FishCell(currentState, currentX, currentY, myParameters, INITIAL_CHRONONS); 
		case (int)SHARK: return new SharkCell(currentState, currentX, currentY, myParameters);
		default: return null;
		}
	}

	@Override
	protected void setDeltas() {
		xDelta = new int[]{-1,0,0,1};
		yDelta = new int[]{0,-1,1,0};
	}

	@Override
	public Paint getColor() {
		return Color.ROYALBLUE;
	}

	@Override
	protected void initialize() {
		myChronons = INITIAL_CHRONONS;
		
	}
}
