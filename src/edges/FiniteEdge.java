// This entire file is part of my masterpiece.
// Chase Malik
package edges;

import java.util.ArrayList;
import java.util.List;

import patch.Patch;
/**
 * Finite Edge Strategy
 * Implements the getNeighbors method based on a finite grid
 * 
 * @author Chase Malik
 */
public class FiniteEdge implements EdgeStrategy {

	@Override
	public List<Patch> getNeighbors(Patch[][] patches, int x, int y, int[] xDelta, int[] yDelta) {
		List<Patch> neighborsList = new ArrayList<Patch>();
		for(int k=0; k<xDelta.length;k++){
			if(x+xDelta[k]>=0 && x+xDelta[k] <patches.length
					&& y+yDelta[k] >= 0 && y+yDelta[k] <patches[0].length){
				neighborsList.add(patches[x+xDelta[k]][y+yDelta[k]]);
			}
		}
		return neighborsList;
	}

}
