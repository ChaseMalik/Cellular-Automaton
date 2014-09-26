package cellsociety_team02;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Main Class
 * Used to Run Cellular Automata Simulations
 * 
 * Starts the Application
 * 
 * @author Chase Malik
 * @author Greg Lyons
 * @author Kevin Rhine
 */
public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
        GridModel model = new GridModel();
        GridView view = new GridView(model, "English");
        stage.setScene(view.getScene());
        stage.show();
	}

}
