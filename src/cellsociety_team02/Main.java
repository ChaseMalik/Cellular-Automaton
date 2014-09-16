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
	
	@Override
	public void start (Stage s)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose XML Source File");
		File file = fileChooser.showOpenDialog(s);
		XMLParser xml = new XMLParser(file);
		String model = xml.getModel();
		Map<String,String> parameters = xml.makeParameterMap();
		int[][] array = xml.makeArray();
		animation = new Timeline();
		xml.printArray();
		
		
		s.setTitle("CA Simulation");
		switch(model)  {
		case "Fire": myGrid = new FireGrid(parameters, array); break;
		case "PredPrey" : myGrid = new PredPreyGrid(parameters, array); break;
		case "Segregation": myGrid = new SegregationGrid(parameters,array); break;

		}
		Scene scene = myGrid.init(600, 700);
		s.setScene(scene);
		s.show();
		speed = 1.0;
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent ke) {
				switch (ke.getCode()){
				case UP: speed*=0.5; startAnimation(); System.out.println("Up accessed");break;
				case DOWN: speed*=2; startAnimation(); System.out.println("Down accessed");break;
				case SPACE: myGrid.startStop(); break;
				case RIGHT: myGrid.step(); break;
				default:
					break;
				}
			}			
		});
		
		startAnimation();
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
	
}
