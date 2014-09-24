package cellsociety_team02;

import java.util.Map;
import java.util.Random;

public class CellFactory {
	Random rand = new Random();
	public Cell makeCell(String s, int r, int c, double state, Map<String, String> params){
		switch(s){
		case "Fire": 
			//			if (s.equals("cell")) newCell = new FireCell(state, r, c); 
			//			else newPatch = new FirePatch(state, r, c); 
			break;
		case "PredPrey":
			//				if (s.equals("cell")) newCell = new PredPreyCell(state, r, c); 
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