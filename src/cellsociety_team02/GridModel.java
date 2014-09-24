package cellsociety_team02;

import java.io.File;
import java.util.List;
import java.util.Map;

import Cell.Cell;
import Patch.Patch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GridModel {
	private Cell[][] myCells;
	private Patch[][] myPatches;
	private Map<String, String> myParameters;

	
	public GridModel(){

	}
	
	private XMLParser loadFileToParser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // Needs to be tested on Macs +"\\xml files"
		File file = fileChooser.showOpenDialog(new Stage());
		XMLParser parser = new XMLParser(file);
		return parser;
	}
	
	public void load(){
		XMLParser parser = loadFileToParser();
		parser.initialize();
		Map<String,String> parameters = parser.makeParameterMap();
		Cell[][] initialCells = parser.makeCells();
		Patch[][] initialPatches = parser.makePatches();
		initialize(initialCells, initialPatches, parameters);
	}
	
	public void initialize(Cell[][] cells, Patch[][] patches, Map<String, String> parameters){
		myCells = cells;
		myPatches = patches;
		myParameters = parameters;

		//System.out.println(myCells.get(0).getCurrentState());
	}
	
	
	
	public void update(){
		for(int i=0;i<myCells.length;i++){
			for(int j=0; j<myCells[0].length;j++){
				myCells[i][j].updateStateandMove(myCells,myPatches);
				//System.out.print(myCells[i][j].getCurrentState());
				//myPatches[i][j].updateState(myCells[i][j]);
			}
		}
		/*for (Patch p: myPatches) {
			
		}*/
		for(int i=0;i<myCells.length;i++){
			for(int j=0; j<myCells[0].length;j++){
				myCells[i][j].currentToFuture();
				//myPatches[i][j].updateState(myCells[i][j]);
			}
		}
		//System.out.println();
	}

	public Cell[][] getCells() {
		return myCells;
	}

	
	
	
}
