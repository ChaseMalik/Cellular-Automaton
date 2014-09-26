package cellsociety_team02;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Cell.Cell;
import Patch.Patch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class GridView {

	private Scene myScene;
	public static final Dimension DEFAULT_SIZE = new Dimension(600, 700);
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	//private Label myStatus;

	private ResourceBundle myResources;
	private GridModel myModel;
	private Timeline myAnimation;
	private double myInterval;
	private List<Shape> myShapeList;
	private BorderPane root;
	private boolean isRunning;
	private String gridType;
	
	public GridView (GridModel model, String language) {
		myModel = model;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		myAnimation = new Timeline();
		myInterval = 1.0;
		myShapeList = new ArrayList<Shape>();
		isRunning = false;
		initialize();
		load();
	}

	private Node makeButtons() {
		HBox box = new HBox();

		Button playPauseButton = makeButton("PlayPauseButton", new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				playPause();
			}
		});
		box.getChildren().add(playPauseButton);
		Button loadButton = makeButton("LoadButton", new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				load();
			}
		});
		box.getChildren().add(loadButton);

		Button stepButton = makeButton("StepButton", new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				step();
			}
		});
		box.getChildren().add(stepButton);
		
		box.getChildren().add(setUpSpeedSlider());
		return box;
	}

	private Slider setUpSpeedSlider() {
		Slider s = new Slider();
		s.setMin(0.1);
		s.setMax(2.2);
		s.setValue(1);
		s.setShowTickMarks(true);
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    updateSpeed(new_val.doubleValue());
            }
        });
		
		return s;
	}

	protected void playPause() {
		isRunning = !isRunning;
		startAnimation();
	}

	private void load(){
		isRunning = false;
		myAnimation.stop();
		myModel.load();
		gridType = myModel.getGridType();
		root.setCenter(makeGrid());
	}

	private Button makeButton (String property, EventHandler<ActionEvent> handler) {
		Button result = new Button();
		result.setText(myResources.getString(property));
		result.setOnAction(handler);
		return result;
	}

	private Node makeGraph() {
		// TODO Auto-generated method stub
		return null;
	}


	public void initialize() {
		root = new BorderPane();
		root.setTop(makeGraph());
		root.setBottom(makeButtons());
		myScene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	}

	public Scene getScene() {
		return myScene;
	}

	// Maybe move to model
	private void startAnimation() {
		myAnimation.stop();
		KeyFrame frame = startHandler(myInterval);
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().clear();
		myAnimation.getKeyFrames().add(frame);
		myAnimation.play();
	}

	public KeyFrame startHandler(double interval) {
		KeyFrame kf = new KeyFrame(Duration.seconds(interval), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(isRunning){
					step();
				}
			}
		});
		return kf;
	}

	public void updateSpeed(double value) {
		myInterval = value;
		startAnimation();
	}

	public void step(){
		myModel.update();
		root.setCenter(makeGrid());
	}

	private Node makeGrid() {
		Draw draw = new Draw();
		Group g = new Group();
		myShapeList.clear();
		Cell[][] cells = myModel.getCells();
		Patch[][] patches = myModel.getPatches();
		double height = cells.length;
		double width = cells[0].length;
		/*double cellHeight = 500.0/(cells.length);
		double cellWidth = 500.0/cells[0].length;*/
		for(int i=0;i<patches.length;i++){
			for(int j=0; j<patches[0].length;j++){
				Cell c = patches[i][j].getCurrentCell();
				Shape newDisplay = draw.drawShape(height,width,i,j,gridType);
				if(c.getColor() != null) newDisplay.setFill(c.getColor());
				else newDisplay.setFill(patches[i][j].getColor());
				myShapeList.add(newDisplay);
				g.getChildren().add(newDisplay);
			}
		}
		return g;
	}
}
