package Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SharkCell extends PredPreyCell {
	
	private int starve;
	private int myHunger;

	public SharkCell(double state, int x, int y, Map<String, String> parameters, int chronons, int hunger) {
		super(state, x, y, parameters);
		myChronons = chronons;
		starve = Integer.parseInt(myParameters.get("sharkStarve"));
		System.out.println(starve);
		myBreed = Integer.parseInt(myParameters.get("sharkBreed"));
		myHunger = hunger;
	}
	
	@Override
	public Paint getColor(Patch p){
		return Color.RED;
	}
	
	public void updateStateandMove(Cell[][] cells, Patch[][] patches) {
		if (myHunger >= starve) {
			this.setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
			return;
		}
		List<Cell> neighbors = getNeighbors(cells);
		List<Cell> fishMoves = new ArrayList<Cell>();
		List<Cell> waterMoves = new ArrayList<Cell>();
		for (Cell c: neighbors) {
			if (c.getCurrentState() == WATER && ((PredPreyCell)c).getFutureCell().getCurrentState() == WATER)
				waterMoves.add(c);
			if (c.getCurrentState() == FISH)
				fishMoves.add(c);
		}
		if (fishMoves.size()>0){
			int random = (int)(Math.random()*fishMoves.size());
			PredPreyCell nextMove = (PredPreyCell)fishMoves.get(random);
			((FishCell)nextMove).getNewMove().setFutureCell(
					new PredPreyCell(WATER, ((FishCell)nextMove).getNewMove().getCurrentX(),
											((FishCell)nextMove).getNewMove().getCurrentY(), myParameters));
			((FishCell)nextMove).setFutureCell(
					new SharkCell(SHARK, nextMove.getCurrentX(), nextMove.getCurrentY(), myParameters, myChronons+1, INITIAL_HUNGER));
			((FishCell)nextMove).food();
			if (myChronons == myBreed)
				this.setFutureCell(new SharkCell(SHARK, currentX, currentY, myParameters, INITIAL_CHRONONS, INITIAL_HUNGER));
			else 
				this.setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
		}
		else if (waterMoves.size()>0) {
			int random = (int)(Math.random()*waterMoves.size());
			Cell nextMove = waterMoves.get(random);
			((PredPreyCell)nextMove).setFutureCell(new SharkCell(SHARK, nextMove.getCurrentX(), nextMove.getCurrentY(), myParameters, myChronons+1, myHunger+1));
			if (myChronons == myBreed)
				this.setFutureCell(new SharkCell(SHARK, currentX, currentY, myParameters, INITIAL_CHRONONS, INITIAL_HUNGER));
		
			else
				this.setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
		}
		else
			myChronons++;
			myHunger++;
	}
	
	
}
