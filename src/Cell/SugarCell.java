package cell;

import java.util.List;
import java.util.Map;

import patch.GenericPatch;
import patch.Patch;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
	}

	public SugarCell(SugarCell c, int sugar) {
		super(c);
	}

	@Override
	public void updateStateandMove(Patch[][] patches) {
		if(myCurrentState == dead) return;
		List<Patch> neighborPatches = getNeighbors(patches);
		Patch newLocation = findHighestSugar(neighborPatches);
		move(newLocation,patches);
	}
	/**
	 * Finds the patch with the highest sugar that it can move to
	 * Loops through the list of patches it can see and finds the one with the highest sugar value
	 * that does not have a cell in the future
	 * If multiple patches have the same high sugar, it choose the closest one
	 * 
	 * @param List<Patch> list of neighboring patches of the cell
	 * @return Patch patch with the highest sugar that the cell can move to
	 */
	private Patch findHighestSugar(List<Patch> neighborPatches) {
		Patch newLocation = new GenericPatch(this,0,0,0,null);
		double high = 0;
		int dist = Integer.MAX_VALUE;
		for(Patch p :neighborPatches){
			int pX = p.getCurrentX();
			int pY = p.getCurrentY();
			if(p.getFutureCell().getCurrentState() == dead){
				if(p.getCurrentState()>high){
					high = p.getCurrentState();
					newLocation = p;
					dist = Math.abs(pX-myCurrentX)+Math.abs(pY-myCurrentY);
				}
				else if(p.getCurrentState() == high && Math.abs(pX-myCurrentX)+Math.abs(pY-myCurrentY)<dist){
					high = p.getCurrentState();
					newLocation = p;
					dist = Math.abs(pX-myCurrentX)+Math.abs(pY-myCurrentY);
				}
			}
		}
		myFutureX = newLocation.getCurrentX();
		myFutureY = newLocation.getCurrentY();
		return newLocation;
	}
	/**
	 * Moves the current cell to the patch it is given
	 * The cell takes the sugar from the new patch and updates its own sugar
	 * After subtracting its own metabolism, if the cell still has sugar it sets the new location's future cell
	 * to this cell and sets the current patch's future cell to a dead cell
	 * If it ran out of sugar then it instead sets the new location's future cell to a dead cell
	 * 
	 * @param patch newLocation destination for the current cell
	 * @param Patch[][] patches array of all patches on the grid
	 */
	private void move(Patch newLocation, Patch[][] patches) {
		mySugar += newLocation.getCurrentState();
		newLocation.setFutureState(0);
		mySugar -= myMetabolism;
		if(mySugar > 0)	newLocation.setFutureCell(new SugarCell(this, mySugar));
		else newLocation.setFutureCell(new SugarCell(dead,myFutureX,myFutureY,myParameters));
		patches[myCurrentX][myCurrentY].setFutureCell(new SugarCell(dead,myCurrentX,myCurrentY,myParameters));
	}
	
	@Override
	protected void setDeltas() {
		myXDelta = new int[myVision*4];
		myYDelta = new int[myVision*4];
		assignDeltas(0,-1,0);
		assignDeltas(myVision,0,-1);
		assignDeltas(2*myVision,0,1);
		assignDeltas(3*myVision,1,0);
	}

	private void assignDeltas(int start,int x, int y) {
		for(int i=0; i<myVision;i++){
			myXDelta[i+start] = x*(i+1);
			myYDelta[i+start] = y*(i+1);
		}
	}
	@Override
	public Paint getColor() {
		if(myCurrentState == alive) return Color.RED;
		else return null;
	}

	@Override
	protected void initialize() {
		mySugar = (int) errorCheck("sugar", DEFAULT_SUGAR);
		myVision = (int)errorCheck("vision", DEFAULT_VISION);
		myMetabolism = (int)errorCheck("metabolism", DEFAULT_METABOLISM);
	}

}
