package cellsociety_team02;

import java.util.Map;
import java.util.Random;

import patch.FirePatch;
import patch.GenericPatch;
import patch.Patch;
import patch.SugarPatch;
import cell.Cell;
import cell.FireCell;
import cell.LifeCell;
import cell.PredPreyCell;
import cell.SegregationCell;
import cell.SugarCell;

public class Factory {
	Random rand = new Random();
	/**
	 * Creates a Cell with the appropriate information based on the string of its model type
	 * 
	 * @param String s is the simulation model\
	 * @param int r is the row in the array for the current cell
	 * @param int c is the column in the array for the current cell
	 * @param Map<String,String> is a map of all parameters for the simulation
	 * @return Cell is the cell returned based on the given information
	 */
	public Cell makeCell(String s, int r, int c, double state, Map<String, String> params){
		switch(s){
		case "Fire": 
			return new FireCell(state, r, c, params); 
		case "PredPrey":
			return new PredPreyCell(state, r, c,params).makeCell(); 
		case "Segregation": 
			return new SegregationCell(state, r, c, params);
		case "Life":
			return new LifeCell(state, r, c, params);
		case "Sugar":
			return new SugarCell(state,r,c,params);
		}
		return null;
	}
	/**
	 * Creates a Patch with the appropriate information based on the string of its model type
	 * 
	 * @param Cell the cell currently on the patch
	 * @param String s is the simulation model
	 * @param int r is the row in the array for the current patch
	 * @param int c is the column in the array for the current patch
	 * @param Map<String,String> is a map of all parameters for the simulation
	 * @return Patch is the patch returned based on the given information
	 */
	public Patch makePatch(Cell cell, String myType, int r, int c, double state,
			Map<String, String> params) {
		switch(myType){
		case "Fire": return new FirePatch(cell, state,r,c, params);
		case "Sugar": return new SugarPatch(cell, state,r,c,params);
		}
		return new GenericPatch(cell,0,r,c , params);
	}
	/**
	 * Creates a random Cell based on the max number of states the cell can have
	 * 
	 * @param String myType is the simulation model
	 * @param int r is the row in the array for the current element
	 * @param int c is the column in the array for the current element
	 * @param Map<String,String> is a map of all parameters for the simulation
	 * @param int numStates is the maximum number of states a cell of this type can have
	 * @return Cell is the random cell returned
	 */
	public Cell makeRandomCell(String myType, int r, int c, Map<String, String> params, int numStates) {
		return makeCell(myType,r,c,rand.nextInt(numStates),params);
	}
	/**
	 * Creates a random Patch based on the max number of states the patch can have
	 * 
	 * @param Cell the cell currently on the patch
	 * @param String myType is the simulation model
	 * @param int r is the row in the array for the current element
	 * @param int c is the column in the array for the current element
	 * @param Map<String,String> is a map of all parameters for the simulation
	 * @param int maxPatch is the maximum number of states a patch of this type can have
	 * @return Patch is the random patch returned
	 */
	public Patch makeRandomPatch(Cell c, String myType, int i, int j, Map<String, String> params, int maxPatch){
		return makePatch(c, myType,i,j,rand.nextInt(maxPatch),params);
	}
	/**
	 * Creates a Cell with state based on the probabilities stored in the cellProb map
	 * 
	 * @param String myType is the simulation model
	 * @param int r is the row in the array for the current element
	 * @param int c is the column in the array for the current element
	 * @param Map<String,String> is a map of all parameters for the simulation
	 * @param int numStates is the maximum number of states a cell of this type can have
	 * @param Map cellProb stores the probabilities for each type of cell
	 * @return Cell is the cell returned
	 */
	public Cell makeProbCell(String myType, int r, int c, Map<String, String> paramMap, int numStates,
			Map<String, String> cellProb) {
		double value = rand.nextDouble();
		double current = 0;
		for(String s: cellProb.keySet()){
			current += Double.parseDouble(cellProb.get(s));
			if(value<current) return makeCell(myType,r,c,Integer.parseInt(s),paramMap);
		}
		return null;
	}
	/**
	 * Creates a Patch based on the probabilites found in the patchProb map
	 * 
	 * @param Cell the cell currently on the patch
	 * @param String myType is the simulation model
	 * @param int r is the row in the array for the current element
	 * @param int c is the column in the array for the current element
	 * @param Map<String,String> is a map of all parameters for the simulation
	 * @param int maxPatch is the maximum number of states a patch of this type can have
	 * @param Map patchProb stores the probabilities for each type of patch
	 * @return Patch is the patch returned
	 */
	public Patch makeProbPatch(Cell c, String myType, int i, int j, Map<String, String> paramMap, int maxPatch,
			Map<String, String> patchProb) {
		double value = rand.nextDouble();
		double current = 0;
		for(String s: patchProb.keySet()){
			current += Double.parseDouble(patchProb.get(s));
			if(value<current) return makePatch(c, myType,i,j,Integer.parseInt(s),paramMap);
		}
		return null;
	}
}