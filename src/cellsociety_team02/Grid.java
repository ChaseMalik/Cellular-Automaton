package cellsociety_team02;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class Grid {
	
	protected Scene scene;
	protected Group group;
	protected int[][] current;
	protected int[][] future;
	
	public Scene init(int width, int height) {
		group = new Group();
		scene = new Scene(group, width, height, Color.WHITE);

		
		return scene;
	}
	
	public KeyFrame startHandlers() {
		group.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent ke) {
				keyPressed(ke);
			}			
		});
		
		KeyFrame kf = new KeyFrame(Duration.seconds(1.0), new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
	    	updateStates();
	    	updateDisplay();
	       }
	    });
		return kf;
	}
	
	protected void updateStates(){
		for (int i=0; i<current.length; i++) {
			for (int j=0; j<current[0].length; j++) {
				updateCell(i, j);
			}
		}
	}
	
	protected void updateDisplay(){
		for (int i=0; i<future.length; i++) {
			for (int j=0; j<future[0].length; j++) {
				Cell c = new Cell(i, j, future[i][j]);
				group.getChildren().add(c);
			}
		}
	}
	
	
	protected abstract void updateCell(int i, int j);
	
	protected void pause() {
		
	}
	
	protected void keyPressed(KeyEvent ke) {
		if (ke.getCode() == KeyCode.SPACE){
			pause();
		}
		if (ke.getCode() == KeyCode.ENTER){
			
		}
	}
}
