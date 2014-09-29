package patch;

import java.util.Map;

import cell.Cell;
import javafx.scene.paint.Paint;

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
