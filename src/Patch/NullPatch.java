package Patch;

import java.util.Map;

import javafx.scene.paint.Paint;
import Cell.Cell;

public class NullPatch extends Patch {

	public NullPatch(double state, double x, double y,Map<String,String> params) {
		super(state, x, y,params);
	}

	@Override
	public void updateState(Cell cell) {

	}

	@Override
	public Paint getColor() {
		return null;
	}

}
