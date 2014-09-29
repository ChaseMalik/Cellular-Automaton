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
 * Subclass of PredPreyCell, implemented to use shark behavior in the simulation
 *
 */

public class SharkCell extends PredPreyCell {

	private static final int DEFAULT_STARVE = 4;
	private static final int DEFAULT_SHARKBREED = 5;
	private int starve;
	private int myHunger;

	public SharkCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
		myChronons = INITIAL_CHRONONS;
		myHunger = INITIAL_HUNGER;
	}

	public SharkCell(SharkCell sharkCell, int chronons, int hunger) {
		super(sharkCell);
		myChronons = chronons;
		myHunger = hunger;

	}

	@Override
	public Paint getColor(){
		return Color.RED;
	}

	/**
	 * Updating is similar to fish
	 * Sharks prioritize eating fish over an empty water location
	 * Sharks can overwrite the move of a fish trying to escape in the same round
	 * Sharks also have a hunger factor that increases if they don't eat, and they eventually starve
	 * 
	 * @param patches
	 */
	public void updateStateandMove(Patch[][] patches) {
		if (myHunger >= starve) {
			patches[currentX][currentY].setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
			return;
		}
		List<Patch> neighbors = getNeighbors(patches);
		List<Patch> fishMoves = new ArrayList<Patch>();
		List<Patch> waterMoves = new ArrayList<Patch>();
		for (Patch p: neighbors) {
			if (p.getCurrentCell().getCurrentState() == WATER && p.getFutureCell().getCurrentState() == WATER)
				waterMoves.add(p);
			if (p.getCurrentCell().getCurrentState() == FISH && p.getFutureCell().getCurrentState() != SHARK)
				fishMoves.add(p);
		}
		if (fishMoves.size()>0){
			Patch nextMove = pickMove(fishMoves);
			FishCell escapingFish = (FishCell)nextMove.getCurrentCell();
			if (escapingFish.getNewMove()!= null) {
				Patch water = escapingFish.getNewMove();
				water.setFutureCell(new PredPreyCell(WATER,(int)water.getCurrentX(), (int)water.getCurrentY(), myParameters));
			}
			escapingFish.food();
			checkBreed(patches);
			nextMove.setFutureCell(new SharkCell(this, myChronons, INITIAL_HUNGER));
		}
		else if (waterMoves.size()>0) {
			Patch nextMove = pickMove(waterMoves);
			checkBreed(patches);
			nextMove.setFutureCell(new SharkCell(this, myChronons, myHunger+1));
		}
		else{
			myChronons++;
			myHunger++; 
		}
	}
	
	@Override
	protected void breed(Patch[][] patches){
		patches[currentX][currentY].setFutureCell(new SharkCell(SHARK, currentX, currentY, myParameters));
	}
	
	@Override
	protected void initialize(){
		starve = (int) errorCheck("sharkStarve", DEFAULT_STARVE);
		myBreed = (int) errorCheck("sharkBreed", DEFAULT_SHARKBREED);
	}


}
