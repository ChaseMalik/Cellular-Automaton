package Patch;

import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Cell.Cell;

public class FirePatch extends Patch {


	private static final double minWood = 1;
	private static final int burning = 1;
	public FirePatch(double state, double x, double y, Map<String,String> params) {
		super(state, x, y, params);
	}

	@Override
	public void updateState(Cell cell) {
		if(cell.getCurrentState() == burning && currentState > minWood)
			futureState  = currentState * 0.8;
		else if(cell.getCurrentState() == burning){
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
