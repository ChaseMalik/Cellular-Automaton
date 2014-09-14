package cellsociety_team02;

import java.io.File;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainTest extends Application{
	@Override
	public void start (Stage s)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose XML Source File");
		File file = fileChooser.showOpenDialog(s);
		DOMExampleJava xml = new DOMExampleJava(file);
		String model = xml.getModel();
		Map<String,String> parameters = xml.makeParameterMap();
		int[][] array = xml.makeArray();
		xml.printArray();
	}

	/**
	 * Start the program.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}
