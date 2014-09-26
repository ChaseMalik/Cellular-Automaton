package Cell;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Patch.Patch;

/**
 * 
 * @author Greg Lyons
 * @author Chase Malik
 * @author Kevin Rhine
 * 
 * PredPreyCell serves as a default cell for the Wa-Tor simulation (representing water cells)
 * It also serves as a super-class for FishCell and SharkCell, which have similar behavior
 * 
 * Some of the methods, like breed() and updateStateandMove(), are empty, 
 * because water does not need to breed or update.  FishCell and SharkCell override these methods.
 * 
 * The makeCell() method serves as a sort of mini-factory, which is used within the larger Factory class
 *
 */

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

	/**
	 * Empty for water cells, but will be overridden by subclasses
	 * 
	 */
	public void updateStateandMove(Patch[][] patches) {
		if (currentState == WATER)
			return;
	}

	/**
	 * Picks a random move from a list of possible moves (not simply neighbors)
	 * 
	 * @param moves
	 */
	protected Patch pickMove(List<Patch> moves) {
		int random = (int)(Math.random()*moves.size());
		Patch nextMove = moves.get(random);
		futureX = (int)nextMove.getCurrentX();
		futureY = (int)nextMove.getCurrentY();
		return nextMove;
	}

	/**
	 * Checks to see if it is time for the Cell to breed
	 * If it is, call its breed method to create a Cell of the correct type
	 * 
	 * @param patches
	 */
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

	/**
	 * Empty for water cells, but will be overridden by subclasses
	 * Called from within checkBreed
	 * 
	 * @param patches
	 */
	protected void breed(Patch[][] patches){
	}

	/**
	 * Used as a mini-factory to make cells of the correct type in the larger Factory
	 * This makes it easier to be less specific in the larger Factory
	 *
	 */
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
