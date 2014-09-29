// This entire file is part of my masterpiece.
// Chase Malik
package edges;

import java.util.ArrayList;
import java.util.List;

import patch.Patch;
/**
 * Toroidal Edge Strategy
 * Implements the getNeighbors method based on a toroidal grid
 * 
 * @author Chase Malik
 */
public class ToroidalEdge implements EdgeStrategy {

	@Override
	public List<Patch> getNeighbors(Patch[][] patches, int x, int y, int[] xDelta, int[] yDelta) {
		List<Patch> neighborsList = new ArrayList<Patch>();
		for(int k=0; k<xDelta.length;k++){
			int neighborX = (x+xDelta[k])%patches.length;
			int neighborY = (y+yDelta[k])%patches[0].length;
			if (neighborX<0) neighborX += patches.length;  //corrects for negative result
			if (neighborY<0) neighborY += patches[0].length;  //corrects for negative result
			neighborsList.add(patches[neighborX][neighborY]);
		}
		return neighborsList;
	}

}
