package Patch;

import Cell.Cell;

public class NullPatch extends Patch {

	public NullPatch(double state, double x, double y) {
		super(state, x, y);
	}

	@Override
	public void updateState(Cell cell) {

	}

}
