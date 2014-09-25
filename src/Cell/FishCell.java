package Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FishCell extends PredPreyCell {

	private boolean eaten;
	private PredPreyCell newMove;
	
	public FishCell(double state, int x, int y, Map<String, String> parameters, int chronons) {
		super(state, x, y, parameters);
		myChronons = chronons;
		myBreed = Integer.parseInt(parameters.get("fishBreed"));
		eaten = false;
		newMove = this;
	}

	@Override
	public Paint getColor(Patch p){
		return Color.LIMEGREEN;
	}
	
	public void food(){
		eaten = true;
	}
	
	public PredPreyCell getNewMove(){
		return newMove;
	}
	
	@Override
	public void updateStateandMove(Cell[][] cells, Patch[][] patches) {
		List<Cell> neighbors = getNeighbors(cells);
		List<Cell> moves = new ArrayList<Cell>();
		for (Cell c: neighbors) {
			if (c.getCurrentState() == WATER && ((PredPreyCell)c).getFutureCell().getCurrentState() == WATER)
				moves.add(c);
		}
		if (moves.size()>0 && !eaten) {
			int random = (int)(Math.random()*moves.size());
			Cell nextMove = moves.get(random);
			newMove = (PredPreyCell)nextMove;
			((PredPreyCell)nextMove).setFutureCell(new FishCell(FISH, nextMove.getCurrentX(), nextMove.getCurrentY(), myParameters, myChronons+1));
			if (myChronons >= myBreed)
				this.setFutureCell(new FishCell(FISH, currentX, currentY, myParameters, INITIAL_CHRONONS));
			else
				this.setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
		}
		else 
			myChronons++;
	}
	
	
	
}
