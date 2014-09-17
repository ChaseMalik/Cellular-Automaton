package cellsociety_team02;

import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
	public Cell(double x, double y, double w, double h, int state, Map colorMap) {
		super(x,y, w, h);
		setFill((Color) colorMap.get(state));
	}
}
