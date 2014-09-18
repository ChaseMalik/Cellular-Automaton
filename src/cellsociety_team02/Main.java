package cellsociety_team02;

import java.io.File;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application{

	private Grid myGrid;
	private KeyFrame frame;
	private double interval;
	private Timeline animation;
	private Stage myStage;
	private Scene myScene;

	@Override
	public void start (Stage s)
	{
		myStage = s;
		loadSimulation(s);
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

	private void loadSimulation(Stage s){
		XMLParser parser = loadFileToParser(s);

		String model = parser.getModelAndInitialize();
		Map<String,String> parameters = parser.makeParameterMap();
		int[][] cellsArray = parser.makeCells();
		double[][] patchesArray = parser.makePatches();

		animation = new Timeline();
		s.setTitle("CA Simulation");

		switch(model)  {
		case "Fire": myGrid = new FireGrid(parameters, cellsArray, patchesArray); break;
		case "PredPrey" : myGrid = new PredPreyGrid(parameters, cellsArray, patchesArray); break;
		case "Segregation": myGrid = new SegregationGrid(parameters,cellsArray, patchesArray); break;
		case "Life": myGrid = new LifeGrid(parameters,cellsArray, patchesArray); break;
		}
		myScene = myGrid.init(600, 700);
		s.setScene(myScene);
		s.show();
		interval = 1.0;
		startAnimation();
		makeKeyHandler();
	}



	private XMLParser loadFileToParser(Stage s) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose XML Source File");
		File file = fileChooser.showOpenDialog(s);
		XMLParser xml = new XMLParser(file);
		return xml;
	}

	private void makeKeyHandler(){
		myScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent ke) {
				switch (ke.getCode()){
				case UP: 
					if (interval <= Math.pow(0.5, 7))
						break;
					interval*=0.5; 
					startAnimation(); 
					break;
				case DOWN: 
					interval*=2;
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
					loadSimulation(myStage); 
					break;
				default:
					break;
				}
			}			
		});
	}

}
