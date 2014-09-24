package cellsociety_team02;

import java.util.Map;
import java.util.Random;

import Cell.Cell;
import Cell.LifeCell;
import Cell.SegregationCell;
import Patch.Patch;

public class Factory {
	Random rand = new Random();
	public Cell makeCell(String s, int r, int c, double state, Map<String, String> params){
		switch(s){
		case "Fire": 
			//			if (s.equals("cell")) newCell = new FireCell(state, r, c); 
			//			else newPatch = new FirePatch(state, r, c); 
			break;
		case "PredPrey":
			//return new PredPreyCell(state, r, c,params).makeCell(); 
			break;
		case "Segregation": 
			return new SegregationCell(state, r, c, params);
		case "Life":
			return new LifeCell(state, r, c, params);
		}
		return null;
	}

	public Patch makePatch(String myType, int r, int c, double state,
			Map<String, String> makeParameterMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cell makeRandomCell(String myType, int i, int j,Map<String, String> params, int numStates) {
		return makeCell(myType,i,j,rand.nextInt()%numStates,params);
	}
}