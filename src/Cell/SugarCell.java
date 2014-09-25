package Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import Patch.NullPatch;
import Patch.Patch;
import Patch.SugarPatch;

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
	public void updateStateandMove(Cell[][] cellList, Patch[][] patches) {
		if(currentState == dead) return;
		List<Patch> neighborPatches = getNeighborPatches(patches);
		Patch newLocation = findHighestSugar(neighborPatches, cellList);
		move(newLocation,cellList);
	}

	private void move(Patch newLocation, Cell[][] cellList) {
		Cell c = cellList[(int) newLocation.getCurrentX()][(int) newLocation.getCurrentY()];
		c.setFutureState(currentState);
		mySugar += newLocation.getCurrentState();
		newLocation.setFutureState(0);
		futureState = 0;
		mySugar -= myMetabolism;
		if(mySugar > 0){
			c.setFutureState(currentState);
			((SugarCell) c).swap(mySugar,myVision,myMetabolism);	
		}
		else c.setFutureState(0);
	}

	private void swap(int sugar, int vision, int metabolism) {
		mySugar = sugar;
		myVision = vision;
		myMetabolism = metabolism;
	}

	private Patch findHighestSugar(List<Patch> neighborPatches, Cell[][] cellList) {
		Patch newLocation = new NullPatch(0,0,0,null);
		double high = 0;
		for(Patch p :neighborPatches){
			Cell c = cellList[(int) p.getCurrentX()][(int) p.getCurrentY()];
			if(p.getCurrentState()>high && c.getCurrentState() == dead && c.getFutureState() == dead){
				high = p.getCurrentState();
				newLocation = p;
			}
		}
		return newLocation;
	}

	private List<Patch> getNeighborPatches(Patch[][] patches) {
		List<Patch> neighborsList = new ArrayList<Patch>();
		for(int k=0; k<xDelta.length;k++){
			if(currentX+xDelta[k]>=0 && currentX+xDelta[k] <patches.length
					&& currentY+yDelta[k] >= 0 && currentY+yDelta[k] <patches[0].length){
				neighborsList.add(patches[currentX+xDelta[k]][currentY+yDelta[k]]);
			}
		}
		return neighborsList;

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
