package cellsociety_team02;

import java.util.Map;
import java.util.Random;

import Cell.Cell;
import Cell.FireCell;
import Cell.LifeCell;
import Cell.SegregationCell;
import Patch.FirePatch;
import Patch.NullPatch;
import Patch.Patch;

public class CellFactory {
	Random rand = new Random();
	public Cell makeCell(String s, int r, int c, double state, Map<String, String> params){
		switch(s){
		case "Fire": 
			return new FireCell(state, r, c, params); 
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
			Map<String, String> params) {
		switch(myType){
		case "Fire": return new FirePatch(state,r,c);
		}
		return new NullPatch(0,r,c);
	}

	public Cell makeRandomCell(String myType, int i, int j,Map<String, String> params) {
		switch(myType){
		case "Fire": return makeCell(myType,i,j,rand.nextInt()%2,params);
		case "PredPrey":
			//				if (s.equals("cell")) newCell = new PredPreyCell(state, r, c); 
			break;
		case "Segregation":
			return makeCell(myType,i,j,rand.nextInt()%3,params);
		case "Life":
			return makeCell(myType,i,j,rand.nextInt()%2,params);
		}
		return null;
	}
}