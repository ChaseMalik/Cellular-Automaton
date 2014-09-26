package cellsociety_team02;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Cell.Cell;
import Patch.Patch;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class GridModel {
	private Cell[][] myCells;
	private Patch[][] myPatches;
	private String myGridType;
	private int myNumStates;
	private Map<String,String> myColorMap;
	private Map<Integer, XYChart.Series> seriesMap;
	
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
		myCells = parser.makeCells();
		myPatches = parser.makePatches();
		myGridType = parser.getGridType();
		myNumStates = parser.getNumStates();
		seriesMap = new HashMap<Integer, XYChart.Series>();
		initSeries();
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
	
	private void initSeries(){
		for(int i=0;i<myNumStates;i++){
			seriesMap.put(i, new XYChart.Series());
		}
	}
	
	public Map<Integer,XYChart.Series> addData(int newX){
		for(int i=0;i<seriesMap.size();i++){
			seriesMap.get(i).getData().add(new XYChart.Data(newX, numState(i)));
		}
		return seriesMap;
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
	
	
	
}
