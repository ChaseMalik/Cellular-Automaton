package cellsociety_team02;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Cell.Cell;
import Patch.Patch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class GridView {

	private Scene myScene;
	public static final Dimension DEFAULT_SIZE = new Dimension(600, 700);
	public static final int GRAPH_WIDTH = 600;
	public static final int GRAPH_HEIGHT = 150;
	public static final int AXIS_WIDTH = 50;
	public static final int GRAPH_INTERVAL=2;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	private static final double MIN_INTERVAL = 0.1;
	private static final double MAX_INTERVAL = 2.2;
	private static final double INITIAL_INTERVAL = 1.0;
	private ResourceBundle myResources;
	private GridModel myModel;
	private Timeline myAnimation;
	private double myInterval;
	private List<Shape> myShapeList;
	private BorderPane root;
	private boolean isRunning;
	private String gridType;
	private int numFrames=0;
	private NumberAxis xAxis = new NumberAxis();
	private NumberAxis yAxis = new NumberAxis();
	private LineChart<Number,Number> popChart;
	/**
	 * Sets up the initial viewer
	 * Initializes certain values and loads the resource bundle
	 * Then calls load to prompt the user for a file and create the scene
	 * 
	 * @param model GridModel being manipulated
	 * @param String language of the resource package
	 */
	public GridView (GridModel model, String language) {
		myModel = model;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		myAnimation = new Timeline();
		myInterval = INITIAL_INTERVAL;
		myShapeList = new ArrayList<Shape>();
		isRunning = false;
		xAxis.setAutoRanging(false);
		xAxis.setUpperBound(AXIS_WIDTH);
		initialize();
		load();
	}
	/**
	 * Creates all of the necessary buttons
	 * Play/Pause,Load,Step,Speed Slider
	 */
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
	/**
	 * Creates the slider to define the speed of the simulations
	 */
	private Slider setUpSpeedSlider() {
		Slider s = new Slider();
		s.setMin(MIN_INTERVAL);
		s.setMax(MAX_INTERVAL);
		s.setValue(INITIAL_INTERVAL);
		s.setShowTickMarks(true);
		s.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				updateSpeed(new_val.doubleValue());
			}
		});
		return s;
	}
	/**
	 * Action to occur when the Play/Pause button is pushed
	 * Changes whether or not the simulation is running
	 */
	protected void playPause() {
		isRunning = !isRunning;
		startAnimation();
	}
	/**
	 * Action to occur when the Load button is pushed
	 * Stops the current animation and prompts the user for a new simulation
	 */
	private void load(){
		isRunning = false;
		myAnimation.stop();
		myModel.load();
		gridType = myModel.getGridType();
		root.setCenter(makeGrid());
		root.setTop(makeGraph());
	}
	/**
	 * Makes a button with a particular name in the resource file and a particular EventHandler
	 * @param String property name in the resource file
	 * @param EventHandler handler for the new button
	 * @return Button with the appropriate label and handler
	 */
	private Button makeButton (String property, EventHandler<ActionEvent> handler) {
		Button result = new Button();
		result.setText(myResources.getString(property));
		result.setOnAction(handler);
		return result;
	}
	/**
	 * Makes the graph
	 * @return Node containing the graph
	 */
	private Node makeGraph() {
		Map<Integer,XYChart.Series> series = myModel.addData(numFrames);
		List<XYChart.Series> temp = new ArrayList<XYChart.Series>(series.values());
		ObservableList<XYChart.Series> seriesList = FXCollections.observableList(temp);
		if(numFrames>AXIS_WIDTH){
			xAxis.setLowerBound(numFrames-AXIS_WIDTH);
			xAxis.setUpperBound(numFrames);
		}
		xAxis.setTickLabelsVisible(false);
		yAxis.setTickLabelsVisible(false);
		popChart = new LineChart(xAxis,yAxis, seriesList);
		popChart.setPrefSize(GRAPH_WIDTH, GRAPH_HEIGHT);
		return popChart;
	}

	/**
	 * Sets up the initial scene
	 */
	public void initialize() {
		root = new BorderPane();
		root.setBottom(makeButtons());
		myScene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	}
	/**
	 * Returns myScene
	 * 
	 * @return Scene current scene
	 */
	public Scene getScene() {
		return myScene;
	}

	/**
	 * Starts the Timeline with the current value of myInterval
	 */
	private void startAnimation() {
		myAnimation.stop();
		KeyFrame frame = startHandler(myInterval);
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().clear();
		myAnimation.getKeyFrames().add(frame);
		myAnimation.play();
	}
	/**
	 * Creates a new key frame every interval that either steps or does nothing based on the isRunning variable
	 * 
	 * @param interval the interval between new key frames
	 * @return KeyFrame frame of information
	 */
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
	/**
	 * Updates the speed of the animation based on the parameter
	 * Starts the Timeline
	 * 
	 * @param value new interval value
	 */
	public void updateSpeed(double value) {
		myInterval = value;
		startAnimation();
	}
	/**
	 * Performs one tick in the simulation
	 * Displays the grid for the new tick
	 * updates the graph every GRAPH_INTERVAL ticks
	 * 
	 */
	public void step(){
		myModel.update();
		root.setCenter(makeGrid());
		numFrames++;
		if(numFrames%GRAPH_INTERVAL==0)
			root.setTop(makeGraph());
	}
	/**
	 * Makes the graphical display for the grid
	 * Loops through all of the patches and creates a new shape for each one based on its cell
	 * Creates a handler for each of these shapes, so that the user can interact with the simulation
	 * 
	 * We are still having issues with updating individual states based on user input
	 * There is a preliminary implementation in place (commented out) but it runs into issues with scope
	 * Code can still compile and run without handler commented out, but we did so to avoid any errors in the program
	 * 
	 */
	private Node makeGrid() {
		Draw draw = new Draw();
		Group g = new Group();
		myShapeList.clear();
		Patch[][] patches = myModel.getPatches();
		double height = patches.length;
		double width = patches[0].length;
		for(int i=0;i<height;i++){
			for(int j=0; j<width;j++){
				Patch p = patches[i][j];
				Cell c = p.getCurrentCell();
				Shape newDisplay = draw.drawShape(height,width,i,j,gridType);
				newDisplay.setUserData(new Point2D.Double(i,j));
				newDisplay.setFill(getLocationColor(p, c));
				newDisplay.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {/*
						Point2D point = (Point2D) newDisplay.getUserData();
						myModel.changeState(point.getX(), point.getY());
						newDisplay.setFill(getLocationColor(p, c));*/
					}
				});
				myShapeList.add(newDisplay);
				g.getChildren().add(newDisplay);
			}
		}
		return g;
	}
	/**
	 * Gets the grid location's color based on the patch and cell at the location
	 * 
	 * @param Patch p at the location
	 * @param Cell c at the location
	 * @return Paint color of the grid location
	 * 
	 */
	private Paint getLocationColor(Patch p, Cell c) {
		if(c.getColor() != null) return c.getColor();
		else return p.getColor();
	}
}
