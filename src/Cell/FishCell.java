package Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FishCell extends PredPreyCell {

	private static final int DEFAULT_FISHBREED = 3;
	private boolean eaten;
	private Patch newMove;
	
	public FishCell(double state, int x, int y, Map<String, String> parameters, int chronons) {
		super(state, x, y, parameters);
		myChronons = chronons;
		
	}
	
	public FishCell(FishCell c, int chronons){
		super(c);
		myChronons = chronons;
	}

	@Override
	public Paint getColor(){
		return Color.LIMEGREEN;
	}
	
	public void food(){
		eaten = true;
	}
	
	public Patch getNewMove(){
		return newMove;
	}
	
	@Override
	public void updateStateandMove(Patch[][] patches) {
		List<Patch> neighbors = getNeighbors(patches);
		List<Patch> moves = new ArrayList<Patch>();
		for (Patch p: neighbors) {
			if (p.getCurrentCell().getCurrentState() == WATER && p.getFutureCell().getCurrentState() == WATER)
				moves.add(p);
		}
		if (moves.size()>0 && !eaten) {
			Patch nextMove = pickMove(moves);
			checkBreed(patches);
			nextMove.setFutureCell(new FishCell(this, myChronons));
		}
		else 
			myChronons++;
	}
	
	@Override
	protected void breed(Patch[][] patches){
		patches[currentX][currentY].setFutureCell(new FishCell(this, INITIAL_CHRONONS));
	}
	@Override
	protected void initialize() {
		myBreed = (int) errorCheck("fishBreed",DEFAULT_FISHBREED);
		eaten = false;
		newMove = null;
	}
	
	
}
