package Patch;

import java.util.Map;

import javafx.scene.paint.Paint;
import Cell.Cell;

public class NullPatch extends Patch {

	public NullPatch(Cell c, double state, double x, double y,Map<String,String> params) {
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
