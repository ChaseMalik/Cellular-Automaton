package cellsociety_team02;

import java.util.HashMap;
import java.util.Map;

public class FireGrid extends Grid {

	public FireGrid(Map<String, String> parametersMap, int[][] initialStates) {
		super(parametersMap, initialStates);
	}

	@Override
	protected void updateCell(int i, int j) {
		if (i+1<current.length && current[i+1][j] == 1) {
			future [i][j] = 1;
		}
	}

}
