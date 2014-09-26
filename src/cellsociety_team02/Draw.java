package cellsociety_team02;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Draw {
	public static final double GRID_WIDTH = 500;
	public static final double GRID_HEIGHT = 500;
	/**
	 * Creates a shape with appropriate center, height, and width based on the type of grid and 
	 * the parameters given
	 * 
	 * @param double height is the height of the array of patches on the grid
	 * @param double width is the width of the array of patches on the grid
	 * @param int i is the row in the array for the current element
	 * @param int j is the column in the array for the current element
	 * @param String type is the type of grid being made
	 * @return Shape is the shape for the (i,j) element in the grid
	 */
	public Shape drawShape(double height, double width, int i, int j, String type){
		switch(type){
		case "Rectangle": return makeRectangle(height,width,i,j);
		case "Triangle": return makeTriangle(height, width, i, j);
		case "Hexagon": return makeHexagon(height,width,i,j);
		}
		return null;
	}
	/**
	 * Creates the rectangle with appropriate height, and width based on the
	 * the height and width of the array
	 * 
	 * @param double height is the height of the array of patches on the grid
	 * @param double width is the width of the array of patches on the grid
	 * @param int i is the row in the array for the current element
	 * @param int j is the column in the array for the current element
	 * @return Shape is the rectangle for the (i,j) element in the grid
	 */
	private Shape makeRectangle(double height, double width, int i, int j) {
		double cellHeight = GRID_HEIGHT/height;
		double cellWidth = GRID_WIDTH/width;
		return new Rectangle(j*cellWidth, i*cellHeight, cellWidth, cellHeight);
	}

	private Shape makeHexagon(double height, double width, int i, int j) {
		return null;
	}
	/**
	 * Creates the triangle with appropriate vertices based on the
	 * the height and width of the array
	 * 
	 * @param double height is the height of the array of patches on the grid
	 * @param double width is the width of the array of patches on the grid
	 * @param int i is the row in the array for the current element
	 * @param int j is the column in the array for the current element
	 * @return Shape is the triangle for the (i,j) element in the grid
	 */
	private Shape makeTriangle(double height, double width, int i, int j) {
		double cellWidth = GRID_WIDTH*2/width;
		double cellHeight = GRID_HEIGHT/height;
		Polygon polygon = new Polygon();
		if(i%2 ==0)
			polygon = makeTriangleRow(i, j, cellWidth, cellHeight, 0);
		else polygon = makeTriangleRow(i,j,cellWidth,cellHeight,1);
		return polygon;
	}
	/**
	 * Creates a triangle with the given offset value
	 * 
	 * @param double cellHeight is the height of the cell
	 * @param double cellWidth is the width of the cell
	 * @param int i is the row in the array for the current element
	 * @param int j is the column in the array for the current element
	 * @param double offset value used to line up the even and odd rows properly
	 * @return Shape is the triangle for the (i,j) element in the grid
	 */
	private Polygon makeTriangleRow(int i, int j, double cellWidth, double cellHeight, double offset) {
		Polygon polygon = new Polygon();
		if(j % 2==0){
		polygon.getPoints().addAll(new Double[]{
		    cellWidth*j-cellWidth*j/2, (i+offset)*cellHeight,
		    cellWidth*(j+1)-cellWidth*j/2, (i+offset)*cellHeight,
		    0.5*cellWidth+cellWidth*j-cellWidth*j/2, cellHeight*(i+1-offset) });
		}
		else{
			polygon.getPoints().addAll(new Double[]{
			0.5*cellWidth+cellWidth*(j+1)-cellWidth*(j+1)/2, cellHeight*(i+1-offset),
			cellWidth*(j)-cellWidth*(j-1)/2, (i+offset)*cellHeight,
			0.5*cellWidth+cellWidth*(j-1)-cellWidth*(j-1)/2, cellHeight*(i+1-offset) });
		}
		return polygon;
	}
}
