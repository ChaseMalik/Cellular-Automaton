// This entire file is part of my masterpiece.
// Chase Malik
package edges;

import java.util.List;

import patch.Patch;

/**
 * Class that stores a specific strategy for dealing with different edge cases
 * Executes the strategy by calling the getNeighbors method on the specific strategy
 * 
 * @author Chase Malik
 */
public class EdgeType {
	private EdgeStrategy myStrategy;
	 
    public EdgeType(EdgeStrategy strategy) {
        myStrategy = strategy;
    }
    public List<Patch> executeStrategy(Patch[][] p, int x, int y, int xDelta[], int yDelta[]) {
        return myStrategy.getNeighbors(p, x, y, xDelta, yDelta);
    }
}