package cellsociety_team02;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Draw {
	public static final double GRID_WIDTH = 500;
	public static final double GRID_HEIGHT = 500;
	
	public Shape drawShape(double height, double width, int i, int j, String type){
		switch(type){
		case "Rectangle": return makeRectangle(height,width,i,j);
		case "Triangle": return makeTriangle(height, width, i, j);
		case "Hexagon": return makeHexagon(height,width,i,j);
		}
		return null;
	}

	private Shape makeRectangle(double height, double width, int i, int j) {
		double cellHeight = GRID_HEIGHT/height;
		double cellWidth = GRID_WIDTH/width;
		return new Rectangle(j*cellWidth, i*cellHeight, cellWidth, cellHeight);
	}

	private Shape makeHexagon(double height, double width, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	private Shape makeTriangle(double height, double width, int i, int j) {
		double cellWidth = GRID_WIDTH*2/width;
		double cellHeight = GRID_HEIGHT/height;
		Polygon polygon = new Polygon();
		if(i%2 ==0)
			polygon = makeTriangle(i, j, cellWidth, cellHeight, 0);
		else polygon = makeTriangle(i,j,cellWidth,cellHeight,1);
		return polygon;
	}

	private Polygon makeTriangle(int i, int j, double cellWidth,
			double cellHeight, double offset) {
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
