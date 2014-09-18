package cellsociety_team02;

import java.awt.Dimension;
import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application{
	
    public static final Dimension DEFAULT_SIZE = new Dimension(600, 700);
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	private Grid myGrid;
	private KeyFrame frame;
	private double interval;
	private Timeline animation;
	private Stage myStage;
	private Scene myScene;
	private ResourceBundle myResources;

	@Override
	public void start (Stage s)
	{
		myStage = s;
		loadSimulation(s, "English");
	}

	/**
	 * Start the program.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}

	private void startAnimation() {
		animation.stop();
		frame = myGrid.startHandlers(interval);
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().clear();
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private void loadSimulation(Stage s, String language){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose XML Source File");
		File file = fileChooser.showOpenDialog(s);
		XMLParser xml = new XMLParser(file);
		String model = xml.getModelAndInitialize();
		Map<String,String> parameters = xml.makeParameterMap();
		int[][] cellsArray = xml.makeCells();
		double[][] patchesArray = xml.makePatches();
		xml.printArray();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		animation = new Timeline();
		s.setTitle("CA Simulation");
		switch(model)  {
		
		case "Fire": myGrid = new FireGrid(parameters, cellsArray, patchesArray); break;
		case "PredPrey" : myGrid = new PredPreyGrid(parameters, cellsArray, patchesArray); break;
		case "Segregation": myGrid = new SegregationGrid(parameters,cellsArray, patchesArray); break;
		case "Life": myGrid = new LifeGrid(parameters,cellsArray, patchesArray); break;
		}
		myScene = myGrid.init(DEFAULT_SIZE.width, DEFAULT_SIZE.height, myResources);
		s.setScene(myScene);
		s.show();
		interval = 1.0;
		myGrid.updateSpeedText(interval);
		startAnimation();
		makeKeyHandler();
	}
	
	private void makeKeyHandler(){
		myScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent ke) {
				switch (ke.getCode()){
				case UP: 
					if (interval <= Math.pow(0.5, 7))
						break;
					interval*=0.5; 
					myGrid.updateSpeedText(interval);
					startAnimation(); 
					break;
				case DOWN: 
					interval*=2;
					myGrid.updateSpeedText(interval);
					startAnimation();
					break;
				case SPACE: 
					myGrid.startStop(); 
					break;
				case RIGHT: 
					myGrid.step(); 
					break;
				case L: 
					myStage.close(); 
					loadSimulation(myStage, "English"); 
					break;
				default:
					break;
				}
			}			
		});
	}
	
}
