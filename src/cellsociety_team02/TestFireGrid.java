package cellsociety_team02;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.HashMap;

import javafx.scene.paint.Color;

import org.junit.Test;

public class TestFireGrid {
	private HashMap<String, String> map = new HashMap<String,String>();

	@Test
	public void TestState(){
		map.put("probCatch", "0.6");
		int[][] cells = new int[5][5];
		double[][] patches = new double[5][5];
		
		Grid grid = new FireGrid(map, cells, patches);
		
		assertEquals(grid.setColor(0,0), Color.YELLOW);
	}

}
