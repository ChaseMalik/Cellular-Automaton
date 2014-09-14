package cellsociety_team02;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
	public Cell(double x, double y, int state) {
		super(x,y);
		switch (state) {
			case 0: setFill(Color.WHITE);
			case 1: setFill(Color.BLACK);
			case 2: setFill(Color.RED);
			case 3: setFill(Color.ORANGE);
			case 4: setFill(Color.YELLOW);
			case 5: setFill(Color.GREEN);
			case 6: setFill(Color.BLUE);
			case 7: setFill(Color.VIOLET);		
		}
	}
}
