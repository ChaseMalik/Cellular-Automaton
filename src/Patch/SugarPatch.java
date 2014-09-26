package Patch;

import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Cell.Cell;

/**
 * 
 * @author Greg Lyons
 * @author Chase Malik
 * @author Kevin Rhine
 * 
 * SugarPatch implements the abstract Patch class
 * 
 * SugarPatch updates its own state (grows back its sugar) in addition to updating its Cell
 * 
 * The color of the Patch is based on the amount of sugar available
 *
 */

public class SugarPatch extends Patch {
	
	private int sugarRate;
	private int sugarInterval;
	private int maxCapacity;
	private int currentTime;
	
	public static final int DEFAULT_SUGAR_RATE = 1;
	public static final int DEFAULT_SUGAR_INTERVAL = 1;
	public static final int DEFAULT_MAX_CAPACITY = 4;
	
	public SugarPatch(Cell c,double state, int x, int y, Map<String,String> params) {

		super(c, state, x, y, params);
		sugarRate = errorCheck("sugarRate", DEFAULT_SUGAR_RATE);
		sugarInterval = errorCheck("sugarInterval", DEFAULT_SUGAR_INTERVAL);
		maxCapacity = errorCheck("maxCapacity", DEFAULT_MAX_CAPACITY);
		currentTime = 0;
	}

	/**
	 *  Checks to see if parameter map contains a given value
	 *  If not, returns a default constant
	 * 
	 * @param string
	 * @param result
	 * @return int
	 */
	private int errorCheck(String string, int result) {
		if(myParameters.containsKey(string)) return Integer.parseInt(myParameters.get(string));
		else return result;
	}

	/**
	 * Updates both the Patch's state and its Cell's state
	 * 
	 */
	@Override
	public void updateState(Patch[][] patches) {
		myCurrentCell.updateStateandMove(patches);
		currentTime++;
		if(currentTime == sugarInterval){
			currentTime = 0;
			futureState+= sugarRate;
			if(futureState > maxCapacity) futureState = maxCapacity;
		}
	}

	/**
	 * Sets the color based on the amount of sugar available
	 * Darker = more sugar available
	 */
	@Override
	public Paint getColor() {
		if(currentState>=4) return Color.DARKORANGE;
		else if(currentState>=3) return Color.ORANGE;
		else if(currentState>=2) return Color.PEACHPUFF;
		else if(currentState>=1) return Color.LIGHTSALMON;
		else return Color.WHITE;
	}
}
