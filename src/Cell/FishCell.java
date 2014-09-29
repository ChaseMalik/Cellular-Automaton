package cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * 
 * @author Greg Lyons
 * @author Kevin Rhine
 * @author Chase Malik
 * 
 * Subclass of PredPreyCell, implemented to use fish behavior in the simulation
 *
 */

public class FishCell extends PredPreyCell {

	private boolean eaten;  
	private Patch newMove;  //keeps track of where the fish is trying to escape (from a shark)
	private static final int DEFAULT_FISHBREED = 3;

	public FishCell(double state, int x, int y, Map<String, String> parameters, int chronons) {
		super(state, x, y, parameters);
		myChronons = chronons;
		
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
	 * Called by a SharkCell
	 * Marks that the fish has been eaten when a shark moves to its position
	 * The fish will not be able to update
	 * 
	 * @param void
	 */
	public void food(){
		eaten = true;
	}
	
	
	/**
	 * Called by a SharkCell
	 * Returns the Patch that the FishCell is trying to escape to (away from a shark eating it)
	 * 
	 * @param void
	 * @return Patch newMove
	 */
	public Patch getNewMove(){
		return newMove;
	}
	
	
	/**
	 * Implements the logic of a FishCell's movement
	 * 
	 * @param patches - the full array of Patches in the grid
	 */
	@Override
	public void updateStateandMove(Patch[][] patches) {
		List<Patch> neighbors = getNeighbors(patches);
		List<Patch> moves = new ArrayList<Patch>();
		for (Patch p: neighbors) {
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
	 * Override from superclass
	 * 
	 * Breeds a new fish at the current location when called
	 * Called from within the superclass's checkBreed method
	 * 
	 * @param patches - an array of Patches
	 * 
	 */
	@Override
	protected void breed(Patch[][] patches){
		patches[currentX][currentY].setFutureCell(new FishCell(this, INITIAL_CHRONONS));
	}
	@Override
	protected void initialize() {
		myBreed = (int) errorCheck("fishBreed",DEFAULT_FISHBREED);
		eaten = false;
		newMove = null;
	}
	
	
}
