package cellsociety_team02;

import java.io.File;
import java.util.Map;

import Cell.Cell;
import Patch.Patch;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class GridModel {
	private Cell[][] myCells;
	private Patch[][] myPatches;
	private String myGridType;
	private int myNumStates;
	private Map<String,String> myColorMap;
	
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
		myCells = parser.makeCells();
		myPatches = parser.makePatches();
		myGridType = parser.getGridType();
		myNumStates = parser.getNumStates();
		myColorMap = parser.makeColorMap();
	}
	
	
	public void update(){
		for(int i=0;i<myPatches.length;i++){
			for(int j=0; j<myPatches[0].length;j++){
				myPatches[i][j].updateState(myPatches);
			}
		}
		
		for(int i=0;i<myPatches.length;i++){
			for(int j=0; j<myPatches[0].length;j++){
				/*if(myCells[i][j] instanceof PredPreyCell)
					myCells[i][j]=((PredPreyCell) myCells[i][j]).getFutureCell();
				else{ 
					myCells[i][j].currentToFuture();*/
					myPatches[i][j].currentToFuture();
				//}
			}
		}
	}

	public Cell[][] getCells() {
		return myCells;
	}

	public Patch[][] getPatches() {
		return myPatches;
	}

	public String getGridType() {
		return myGridType;
	}
	
	public Map<String,String> getColorMap(){
		return myColorMap;
	}

	public void changeState(double x, double y) {
		Cell c = myPatches[(int)x][(int)y].getCurrentCell();
		double state = c.getCurrentState();
		double futureState = (state+1) % myNumStates;
		c.setCurrentState(futureState);
		c.setFutureState(futureState);
		
	}

	
	
	
}
