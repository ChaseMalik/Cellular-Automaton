package cellsociety_team02;

import static org.junit.Assert.*;

import java.util.Map;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import org.junit.Test;

import patch.FirePatch;
import patch.Patch;
import cell.Cell;
import cell.FireCell;
/**
**@author Kevin Rhine
*/
public class GraphTester {
	private Patch[][] myPatches = new Patch[5][5];
	private Map<String, String> myParams;
	private Map<Integer, XYChart.Series> seriesMap;
	private GridModel myModel;

	private void init(){
		for(int i =0; i<myPatches.length;i++){
			for(int j=0;j<myPatches[0].length;j++){
				Cell c = new FireCell(0, i, j, myParams);
				myPatches[i][j] = new FirePatch(c,1,i,j , myParams);
			}
		}
		for(int i=0;i<2;i++){
			seriesMap.put(i, new XYChart.Series());
		}
	}
	
//Added these methods to this document because they use undesired logic (myNumStates, myPatches from the XML, etc.) in normal implementation
	private void addData(int x) {
		for(int i=0;i<seriesMap.size();i++){
			seriesMap.get(i).getData().add(new XYChart.Data(x, numState(i)));
		}
	}
	
	private int numState(int state){
		int total = 0;
		for (int i = 0; i<myPatches.length; i++){
			for(int j=0; j<myPatches[0].length; j++){
				double current = myPatches[i][j].getCurrentCell().getCurrentState();
				if(current==state)
					total++;
			}
		}
		return total;
	}
	
	@Test
	public void testData(){
		init();
		addData(0);
		int length0 = seriesMap.get(0).getData().size();
		int length1 = seriesMap.get(1).getData().size();
		assertFalse(length0==length1); //unequal # of state 0s and stat 1s
		Cell fire = new FireCell(1,0,0,myParams);
		myPatches[0][0] = new FirePatch(fire,1,0,0,myParams);
		addData(1);
		int newLength0 = seriesMap.get(0).getData().size();
		int newLength1 = seriesMap.get(1).getData().size();
		assertFalse(length0==newLength0);
		assertFalse(length1==newLength1);//Should have more data points now
	}
	
	//Would test the axis as well (and that they shift properly), but that requires the entire scene and chart with all that unprovided logic

}
