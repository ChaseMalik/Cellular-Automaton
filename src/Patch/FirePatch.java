package Patch;

import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Cell.Cell;

public class FirePatch extends Patch {


	private static final double minWood = 0.9;

	private static final double burnRate = 0.8;
	private static final int burning = 1;
	public FirePatch(Cell c, double state, double x, double y, Map<String,String> params) {
		super(c, state, x, y, params);
	}

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

	@Override
	public Paint getColor() {
		double wood = currentState;
		if(wood>=2.0) return Color.DARKGREEN;
		else if(wood>=1.0) return Color.GREEN;
		else if(wood>0.0) return Color.LIGHTGREEN;
		else return Color.YELLOW;
	}

}
