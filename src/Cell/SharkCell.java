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
		starve = Integer.parseInt(parameters.get("sharkStarve"));
		myBreed = Integer.parseInt(parameters.get("sharkBreed"));
		myHunger = hunger;
	}

	public SharkCell(SharkCell sharkCell, int chronons, int hunger) {
		super(sharkCell);
		myChronons = chronons;
		myHunger = hunger;
		starve = Integer.parseInt(myParameters.get("sharkStarve"));
		myBreed = Integer.parseInt(myParameters.get("sharkBreed"));

	}

	@Override
	public Paint getColor(){
		return Color.RED;
	}

	public void updateStateandMove(Patch[][] patches) {
		if (myHunger >= starve) {
			patches[currentX][currentY].setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
			return;
		}
		List<Patch> neighbors = getNeighbors(patches);
		List<Patch> fishMoves = new ArrayList<Patch>();
		List<Patch> waterMoves = new ArrayList<Patch>();
		for (Patch p: neighbors) {
			if (p.getCurrentCell().getCurrentState() == WATER && p.getFutureCell().getCurrentState() == WATER)
				waterMoves.add(p);
			if (p.getCurrentCell().getCurrentState() == FISH && p.getFutureCell().getCurrentState() != SHARK)
				fishMoves.add(p);
		}
		if (fishMoves.size()>0){
			int random = (int)(Math.random()*fishMoves.size());
			Patch nextMove = fishMoves.get(random);
			futureX = (int)nextMove.getCurrentX();
			futureY = (int)nextMove.getCurrentY();
			FishCell escapingFish = (FishCell)nextMove.getCurrentCell();
			if (escapingFish.getNewMove()!= null) {
				Patch water = escapingFish.getNewMove();
				water.setFutureCell(new PredPreyCell(WATER,(int)water.getCurrentX(), (int)water.getCurrentY(), myParameters));
			}
			escapingFish.food();
			if (myChronons == myBreed){
				patches[currentX][currentY].setFutureCell(new SharkCell(this, INITIAL_CHRONONS, INITIAL_HUNGER));
				myChronons = INITIAL_CHRONONS;
			}
			else {
				patches[currentX][currentY].setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
				myChronons++;
			}
			nextMove.setFutureCell(new SharkCell(this, myChronons, myHunger+1));
		}
		else if (waterMoves.size()>0) {
			boolean test = (currentX == 17) && (currentY == 3);
			int random = (int)(Math.random()*waterMoves.size());
			Patch nextMove = waterMoves.get(random);
			futureX = (int)nextMove.getCurrentX();
			futureY = (int)nextMove.getCurrentY();
			if(test) System.out.println(futureX + " " + futureY);
			if (myChronons == myBreed){
				patches[currentX][currentY].setFutureCell(new SharkCell(this, INITIAL_CHRONONS, INITIAL_HUNGER));
				myChronons = INITIAL_CHRONONS;
			}
			else {
				patches[currentX][currentY].setFutureCell(new PredPreyCell(WATER, currentX, currentY, myParameters));
				myChronons++;
			}
			nextMove.setFutureCell(new SharkCell(this, myChronons, myHunger+1));
			
		}
		else{
			myChronons++;
			myHunger++;
		}
	}


}
