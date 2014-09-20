package cellsociety_team02;

import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class GridModel {
	private List<Cell> myCells;
	private List<Patch> myPatches;
	private Map<String, String> myParameters;

	
	public GridModel(){

	}
	
	public void initialize(List<Cell> cells, List<Patch> patches, Map<String, String> parameters){
		myCells = cells;
		myPatches = patches;
		myParameters = parameters;

		System.out.println(myCells.get(0).getCurrentState());
	}
	
	
	
	public void update(){
		for (Cell c: myCells) {
			c.updateStateandMove(myCells);
			System.out.print(c.getCurrentState());
		}
		for (Patch p: myPatches) {
			
		}
		for (Cell c: myCells) {
			c.currentToFuture();
		}
		System.out.println();
	}

	public List<Cell> getCells() {
		return myCells;
	}

	
	
	
}
