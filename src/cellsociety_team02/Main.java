package cellsociety_team02;

import java.io.File;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application{
	
	Grid myGrid;
	
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
		
		// sets the game's loop 
		KeyFrame frame = myGrid.startHandlers();
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * Start the program.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}
