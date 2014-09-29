// This entire file is part of my masterpiece.
// GREG LYONS

package Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * @author Greg Lyons
 * @author Kevin Rhine
 * @author Chase Malik
 * 
 * Subclass of PredPreyCell, implements fish behavior
 */

public class FishCell extends PredPreyCell {

	private boolean eaten;  //whether or not the fish has been eaten by a shark on this turn
	private Patch newMove;  //where the fish is trying to escape (from a shark)
	private static final int DEFAULT_FISHBREED = 3;

	public FishCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
		myChronons = INITIAL_CHRONONS;
	}
	
	public FishCell(FishCell c, int chronons){
		super(c);
		myChronons = chronons;
	}

	@Override
	public Paint getColor(){
		return Color.LIMEGREEN;
	}
	
	/**
	 * Called by a SharkCell if the shark eats the fish
	 * Fish will be unable to move if it comes after the shark in the iteration for this turn
	 * If the fish moves before the shark, the SharkCell will erase its move anyway
	 */
	public void food(){
		eaten = true;
	}
	
	/**
	 * Called by a SharkCell
	 * Returns the Patch that the FishCell is trying to escape to (away from a shark eating it)
	 * @return Patch newMove
	 */
	public Patch getNewMove(){
		return newMove;
	}
	
	/**
	 * Moves the FishCell by creating a clone at the new location
	 * Only moves if the location is currently water and has not been occupied yet for the next round
	 * @param patches - the full array of Patches in the grid
	 */
	@Override
	public void updateStateandMove(Patch[][] patches) {
		List<Patch> neighbors = getNeighbors(patches);
		List<Patch> moves = new ArrayList<Patch>();
		for (Patch p: neighbors) {
			if (p == null || p.getCurrentCell() == null) continue;
			if (p.getCurrentCell().getCurrentState() == WATER && p.getFutureCell().getCurrentState() == WATER)
				moves.add(p);
		}
		if (moves.size()>0 && !eaten) {
			Patch nextMove = pickMove(moves);
			checkBreed(patches);
			nextMove.setFutureCell(new FishCell(this, myChronons));
		}
		else 
			myChronons++;
	}
	
	/**
	 * Breeds a new fish at the current location when called
	 * Called from within the superclass's checkBreed method
	 * 
	 * @param patches - an array of Patches
	 */
	@Override
	protected void breed(Patch[][] patches){
		patches[currentX][currentY].setFutureCell(new FishCell(this, INITIAL_CHRONONS));
	}
	
	/**
	 * Called within the superclass (Cell) constructor, but overridden by each subclass
	 * Sets up parameters and checks for errors (if errors, sets to default constants)
	 */
	@Override
	protected void initialize() {
		myBreed = (int) errorCheck("fishBreed",DEFAULT_FISHBREED);
		eaten = false;
		newMove = null;
	}
	
	/**
	 * THIS METHOD EXISTS SOLELY FOR J-UNIT TESTING AND IS NOT PART OF THE PROGRAM
	 * @return eaten - whether or not the fish has been eaten
	 */
	public boolean isEaten(){
		return eaten;
	}
}
