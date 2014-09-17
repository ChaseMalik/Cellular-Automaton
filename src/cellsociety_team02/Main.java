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
	private double speed;
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
		frame = myGrid.startHandlers(speed);
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().clear();
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private void loadSimulation(Stage s){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose XML Source File");
		File file = fileChooser.showOpenDialog(s);
		XMLParser xml = new XMLParser(file);
		String model = xml.getModel();
		Map<String,String> parameters = xml.makeParameterMap();
		int[][] cellsArray = xml.makeCells();
		double[][] patchesArray = xml.makePatches();
		animation = new Timeline();
		xml.printArray();
		s.setTitle("CA Simulation");
		switch(model)  {
		case "Fire": myGrid = new FireGrid(parameters, cellsArray, patchesArray); break;
		case "PredPrey" : myGrid = new PredPreyGrid(parameters, cellsArray, patchesArray); break;
		case "Segregation": myGrid = new SegregationGrid(parameters,cellsArray, patchesArray); break;
		}
		myScene = myGrid.init(600, 700);
		s.setScene(myScene);
		s.show();
		speed = 1.0;
		startAnimation();
		makeKeyHandler();
	}
	
	private void makeKeyHandler(){
		myScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent ke) {
				switch (ke.getCode()){
				case UP: speed*=0.5; startAnimation(); System.out.println("Up accessed");break;
				case DOWN: speed*=2; startAnimation(); System.out.println("Down accessed");break;
				case SPACE: myGrid.startStop(); break;
				case RIGHT: myGrid.step(); break;
				case L: myStage.close(); loadSimulation(myStage); break;
				default:
					break;
				}
			}			
		});
	}
	
}
