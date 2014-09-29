// This entire file is part of my masterpiece.
// Chase Malik
package edges;

import java.util.List;

import patch.Patch;
/**
 * Interface that allows you to define different strategies for the edges of the grid
 * 
 * @author Chase Malik
 */
public interface EdgeStrategy {
	List<Patch> getNeighbors(Patch[][] p, int x, int y, int xDelta[], int yDelta[]);
}


