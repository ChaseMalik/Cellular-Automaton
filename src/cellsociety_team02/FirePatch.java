package cellsociety_team02;

public class FirePatch extends Patch {

	public FirePatch(double state, double x, double y) {
		super(state, x, y);
	}

	@Override
	public void updateState(Cell cell) {
		currentState = futureState;

	}

}
