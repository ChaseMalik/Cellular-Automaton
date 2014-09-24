package cellsociety_team02;

import java.util.Map;

public class CellFactory {

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
}