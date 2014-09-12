package cellsociety_team02;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
	public Cell(int x, int y, int state) {
		super(x,y);
		switch (state) {
			case 1: setFill(Color.WHITE);
			case 2: setFill(Color.RED);
			case 3: setFill(Color.ORANGE);
			case 4: setFill(Color.YELLOW);
			case 5: setFill(Color.GREEN);
			case 6: setFill(Color.BLUE);
			case 7: setFill(Color.VIOLET);
			case 8: setFill(Color.BLACK);		
		}
	}
}
