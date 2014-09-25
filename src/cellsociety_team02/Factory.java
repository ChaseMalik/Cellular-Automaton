package cellsociety_team02;

import java.util.Map;
import java.util.Random;

import Cell.Cell;
import Cell.FireCell;
import Cell.LifeCell;
import Cell.PredPreyCell;
import Cell.SegregationCell;
import Cell.SugarCell;
import Patch.FirePatch;
import Patch.NullPatch;
import Patch.Patch;
import Patch.SugarPatch;

public class Factory {
	Random rand = new Random();
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

	public Patch makePatch(String myType, int r, int c, double state,
			Map<String, String> params) {
		switch(myType){
		case "Fire": return new FirePatch(state,r,c, params);
		case "Sugar": return new SugarPatch(state,r,c,params);
		}
		return new NullPatch(0,r,c , params);
	}

	public Cell makeRandomCell(String myType, int i, int j,Map<String, String> params, int numStates) {
		return makeCell(myType,i,j,rand.nextInt(numStates),params);
	}

	public Cell makeProbCell(String myType, int i, int j, Map<String, String> paramMap, int myNumStates,
			Map<String, String> cellProb) {
		double value = rand.nextDouble();
		double current = 0;
		for(String s: cellProb.keySet()){
			current += Double.parseDouble(cellProb.get(s));
			if(value<current) return makeCell(myType,i,j,Integer.parseInt(s),paramMap);
		}
		return null;
	}

	public Patch makeProbPatch(String myType, int i, int j,
			Map<String, String> paramMap, int myNumStates,
			Map<String, String> patchProb) {
		// TODO Auto-generated method stub
		return null;
	}
	public Patch makeRandomPatch(String myType, int i, int j, Map<String, String> params, int maxPatch){
		return makePatch(myType,i,j,rand.nextInt(maxPatch),params);
	}
}