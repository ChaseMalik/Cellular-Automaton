package cellsociety_team02;

import java.io.File;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application{

	private Grid myGrid;
	private double myInterval;
	private Timeline myAnimation;

	@Override
	public void start (Stage s)
	{
		//Platform.setImplicitExit(true);
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
		myAnimation.stop();
		KeyFrame frame = myGrid.startHandlers(myInterval);
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().clear();
		myAnimation.getKeyFrames().add(frame);
		myAnimation.play();
	}

	private void loadSimulation(Stage s){
		s.setTitle("CA Simulation");
		XMLParser parser = loadFileToParser(s);
		String model = parser.getModelAndInitialize();
		Map<String,String> parameters = parser.makeParameterMap();
		int[][] cellsArray = parser.makeCells();
		double[][] patchesArray = parser.makePatches();

		switch(model)  {
		case "Fire": myGrid = new FireGrid(parameters, cellsArray, patchesArray); break;
		case "PredPrey" : myGrid = new PredPreyGrid(parameters, cellsArray, patchesArray); break;
		case "Segregation": myGrid = new SegregationGrid(parameters,cellsArray, patchesArray); break;
		case "Life": myGrid = new LifeGrid(parameters,cellsArray, patchesArray); break;
		}
		
		Scene scene = myGrid.init(600, 700);
		s.setScene(scene);
		s.show();
		myInterval = 1.0;
                myAnimation = new Timeline();
		startAnimation();
		makeKeyHandler(s,scene);
	}



	private XMLParser loadFileToParser(Stage s) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose XML Source File");
		File file = fileChooser.showOpenDialog(s);
		XMLParser xml = new XMLParser(file);
		return xml;
	}

	private void makeKeyHandler(Stage s, Scene scene){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent ke) {
				switch (ke.getCode()){
				case UP: 
					if (myInterval <= Math.pow(0.5, 7))
						break;
					myInterval*=0.5; 
					startAnimation(); 
					break;
				case DOWN: 
					myInterval*=2;
					startAnimation();
					break;
				case SPACE: 
					myGrid.startStop(); 
					break;
				case RIGHT: 
					myGrid.step(); 
					break;
				case L: 
				        myAnimation.stop();
					//s.close();
					loadSimulation(s); 
					break;
				case Q:
				        System.exit(0);
				default:
					break;
				}
			}			
		});
	}

}
