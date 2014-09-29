package patch;

import java.util.Map;

import cell.Cell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * 
 * 
 * @author Greg Lyons
 * @author Chase Malik
 * @author Kevin Rhine
 *
 *FirePatch implements the abstract Patch class
 *
 *It is able to store and update its own state in addition to it's Cell's state
 *It can change color based on the amount of wood it has available
 *
 */

public class FirePatch extends Patch {


	private static final double minWood = 1.0;
	private static final double burnRate = 0.8;
	private static final int burning = 1;
	
	public FirePatch(Cell c, double state, int x, int y, Map<String,String> params) {
		super(c, state, x, y, params);
	}

	/**
	 * FirePatch updates both its Cell and its own state (the amount of wood available)
	 * 
	 * @param patches
	 */
	@Override
	public void updateState(Patch[][] patches) {
		myCurrentCell.updateStateandMove(patches);
		if(myCurrentCell.getCurrentState() == burning && currentState > minWood)
			futureState  = currentState * burnRate;
		else if(myCurrentCell.getCurrentState() == burning){
			futureState = 0;
		}
		else futureState = currentState;
	}

	/**
	 * Color is based on the amount of wood available
	 */
	@Override
	public Paint getColor() {
		double wood = currentState;
		if(wood>=2.0) return Color.DARKGREEN;
		else if(wood>=1.0) return Color.GREEN;
		else if(wood>0.0) return Color.LIGHTGREEN;
		else return Color.YELLOW;
	}

}
