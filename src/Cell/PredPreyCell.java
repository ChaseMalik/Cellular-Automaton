// This entire file is part of my masterpiece.
// GREG LYONS

package Cell;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Patch.Patch;

/**
 * @author Greg Lyons
 * @author Chase Malik
 * @author Kevin Rhine
 * 
 * PredPreyCell is a super-class for FishCell and SharkCell, which have similar (but slightly different) behavior.
 * PredPreyCell also serves as a default empty cell for the Wa-Tor simulation (representing a water cell).
 * 
 * Because it can be instantiated to represent a water cell, it cannot be an abstract class.
 * However, extending this class (for SharkCell and FishCell) requires overriding the breed() and updateStateandMove() methods.
 * These methods are empty in this class because water does not need to breed or update.
 * 
 * The makeCell() method serves as a sort of mini-factory, and it is called from within the larger Factory class.
 * Fish and sharks must be instantiated with their specific implementation to behave properly.
 * makeCell() helps to avoid additional if-statements within the Factory class, and hides the separate implementations inside this class
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
	 * @param: The array of Patches on the grid
	 */
	public void updateStateandMove(Patch[][] patches) {
	}

	
	/**
	 * Picks a random move from a list of possible moves
	 * These possible moves are more specific than just neighboring locations - these are the valid moves
	 * 
	 * @param moves: a list of the possible moves
	 */
	protected Patch pickMove(List<Patch> moves) {
		int random = (int)(Math.random()*moves.size());
		Patch nextMove = moves.get(random);
		futureX = nextMove.getCurrentX();
		futureY = nextMove.getCurrentY();
		return nextMove;
	}

	/**
	 * METHOD IS SET AS PUBLIC FOR RUNNING J-UNIT TESTS
	 * METHOD SHOULD BE SET AS PROTECTED WHILE RUNNING THE PROGRAM
	 * 
	 * Checks to see if it is time for the PredPreyCell to breed
	 * If it is, call its breed method to create a Cell of the correct implementation (fish or shark)
	 * @param patches
	 */
	public boolean checkBreed(Patch[][] patches) {
		if (myChronons >= myBreed){
			breed(patches);
			myChronons = INITIAL_CHRONONS;
			return true;
		}
		else{
			patches[currentX][currentY].setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
			myChronons++;
			return false;
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
	 * Must cast states to ints because they are stored as doubles by Cell convention
	 */
	public Cell makeCell() {
		switch( (int)currentState) {
		case (int)WATER: return new PredPreyCell(currentState, currentX, currentY, myParameters);
		case (int)FISH: return new FishCell(currentState, currentX, currentY, myParameters); 
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
