package Patch;

import java.util.Map;

import javafx.scene.paint.Paint;
import Cell.Cell;

public class GenericPatch extends Patch {

	public GenericPatch(Cell c, double state, int x, int y,Map<String,String> params) {
		super(c, state, x, y,params);
	}

	@Override
	public void updateState(Patch[][] patches) {
		myCurrentCell.updateStateandMove(patches);
	}

	@Override
	public Paint getColor() {
		return myCurrentCell.getColor();
	}

}
