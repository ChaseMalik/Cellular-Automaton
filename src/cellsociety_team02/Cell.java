package cellsociety_team02;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
	public Cell(double x, double y, double w, double h, int state) {
		super(x,y, w, h);
		switch (state) {
			case 0: setFill(Color.WHITE); break;
			case 1: setFill(Color.BLACK); break;
			case 2: setFill(Color.RED); break;
			case 3: setFill(Color.ORANGE); break;
			case 4: setFill(Color.YELLOW); break;
			case 5: setFill(Color.GREEN); break;
			case 6: setFill(Color.BLUE); break;
			case 7: setFill(Color.VIOLET);	break;
		}
	}
}
