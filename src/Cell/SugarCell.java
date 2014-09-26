package Cell;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Patch.GenericPatch;
import Patch.Patch;

public class SugarCell extends Cell {

	private static final double DEFAULT_SUGAR = 5;
	private static final double DEFAULT_VISION = 2;
	private static final double DEFAULT_METABOLISM = 2;
	private static final int alive = 1;
	private static final int dead = 0;
	private int mySugar;
	private int myMetabolism;
	private int myVision;


	public SugarCell(double state, int x, int y, Map<String, String> parameters) {
		super(state, x, y, parameters);
		mySugar = (int) errorCheck("sugar", DEFAULT_SUGAR);
		myVision = (int)errorCheck("vision", DEFAULT_VISION);
		myMetabolism = (int)errorCheck("metabolism", DEFAULT_METABOLISM);
		changeDeltas();
	}

	public SugarCell(SugarCell c, int sugar) {
		super(c);	
		mySugar = sugar;
		myVision = (int)errorCheck("vision", DEFAULT_VISION);
		myMetabolism = (int)errorCheck("metabolism", DEFAULT_METABOLISM);
	}

	private void changeDeltas() {
		xDelta = new int[myVision*4];
		yDelta = new int[myVision*4];
		assignDeltas(0,-1,0);
		assignDeltas(myVision,0,-1);
		assignDeltas(2*myVision,0,1);
		assignDeltas(3*myVision,1,0);
	}

	private void assignDeltas(int start,int x, int y) {
		for(int i=0; i<myVision;i++){
			xDelta[i+start] = x*(i+1);
			yDelta[i+start] = y*(i+1);
		}
	}

	@Override
	public void updateStateandMove(Patch[][] patches) {
		if(currentState == dead) return;
		List<Patch> neighborPatches = getNeighbors(patches);
		Patch newLocation = findHighestSugar(neighborPatches);
		move(newLocation,patches);
	}

	private void move(Patch newLocation, Patch[][] patches) {
		mySugar += newLocation.getCurrentState();
		newLocation.setFutureState(0);
		mySugar -= myMetabolism;
		if(mySugar > 0){
			newLocation.setFutureCell(new SugarCell(this, mySugar));
			patches[currentX][currentY].setFutureCell(new SugarCell(dead,currentX,currentY,myParameters));
		}
		else newLocation.setFutureCell(new SugarCell(dead,futureX,futureY,myParameters));
	}

	private Patch findHighestSugar(List<Patch> neighborPatches) {
		Patch newLocation = new GenericPatch(this,0,0,0,null);
		double high = 0;
		int dist = Integer.MAX_VALUE;
		for(Patch p :neighborPatches){
			int pX = (int) p.getCurrentX();
			int pY = (int) p.getCurrentY();
			if(p.getCurrentCell().getCurrentState() == dead && p.getFutureCell().getCurrentState() == dead){
				if(p.getCurrentState()>high){
					high = p.getCurrentState();
					newLocation = p;
					dist = Math.abs(pX-currentX)+Math.abs(pY-currentY);
				}
				else if(p.getCurrentState() == high && Math.abs(pX-currentX)+Math.abs(pY-currentY)<dist){
					high = p.getCurrentState();
					newLocation = p;
					dist = Math.abs(pX-currentX)+Math.abs(pY-currentY);
				}
			}
		}
		futureX = (int) newLocation.getCurrentX();
		futureY = (int) newLocation.getCurrentY();
		return newLocation;
	}

	@Override
	protected void setDeltas() {
		xDelta = new int[]{-1,0,0,1};
		yDelta = new int[]{0,-1,1,0};
	}

	@Override
	public Paint getColor() {
		if(currentState == alive) return Color.RED;
		else return null;
	}

}
