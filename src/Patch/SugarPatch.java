package Patch;

import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Cell.Cell;

public class SugarPatch extends Patch {
	
	private int sugarRate;
	private int sugarInterval;
	private int maxCapacity;
	private int currentTime;
	public SugarPatch(Cell c,double state, int x, int y, Map<String,String> params) {
		super(c, state, x, y, params);
		sugarRate = check("sugarRate", 1);
		sugarInterval = check("sugarInterval", 1);
		maxCapacity = check("maxCapacity", 4);
		currentTime = 0;
	}

	private int check(String string, int result) {
		if(myParameters.containsKey(string)) return Integer.parseInt(myParameters.get(string));
		else return result;
	}

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

	@Override
	public Paint getColor() {
		if(currentState>=4) return Color.DARKORANGE;
		else if(currentState>=3) return Color.ORANGE;
		else if(currentState>=2) return Color.PEACHPUFF;
		else if(currentState>=1) return Color.LIGHTSALMON;
		else return Color.WHITE;
	}
}
