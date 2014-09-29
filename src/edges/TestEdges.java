package edges;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import cell.Cell;
import cell.LifeCell;
import patch.GenericPatch;
import patch.Patch;
/**
 * Test methods for different edge types
 * 
 * @author Chase Malik
 */
public class TestEdges {
	private Patch[][] myPatches = new Patch[5][4];

	private int[] xDelta4 = {-1, 0, 0, 1};
	private int[] yDelta4 = {0, -1, 1, 0};

	private int[] xDelta8 = {-1, -1, -1, 0, 0, 1, 1, 1};
	private int[] yDelta8 = {-1, 0, 1, -1, 1, -1, 0, 1};

	private EdgeType myType;

	private Map<String, String> myParameters;

	private void initialize(){
		for(int i =0; i<myPatches.length;i++){
			for(int j=0;j<myPatches[0].length;j++){
				Cell cell = new LifeCell(0, i, j, myParameters);
				myPatches[i][j] = new GenericPatch(cell,0,i,j , myParameters);
			}
		}
	}


	@Test
	public void TestToroidalNumber(){
		initialize();
		myType = new EdgeType(new ToroidalEdge());
		for(int i =0; i<myPatches.length;i++){
			for(int j=0;j<myPatches[0].length;j++){
				List<Patch> list8 = myType.executeStrategy(myPatches, i, j, xDelta8, yDelta8);
				assertEquals(8,list8.size()); // make sure always has 8 neighbors
				List<Patch> list4 = myType.executeStrategy(myPatches, i, j, xDelta4, yDelta4);
				assertEquals(4,list4.size()); // make sure always has 4 neighbors
			}
		}
	}

	@Test
	public void TestFiniteCornerNumber(){
		initialize();
		myType = new EdgeType(new FiniteEdge());

		int[] cornerX = {0,0,myPatches.length-1,myPatches.length-1};
		int[] cornerY = {0,myPatches[0].length-1,0, myPatches[0].length-1};


		for(int i =0; i<cornerX.length;i++){
			List<Patch> list8 = myType.executeStrategy(myPatches, cornerX[i], cornerY[i], xDelta8, yDelta8);
			assertEquals(3,list8.size()); // make sure always has 3 neighbors in the corner
			List<Patch> list4 = myType.executeStrategy(myPatches, cornerX[i], cornerY[i], xDelta4, yDelta4);
			assertEquals(2,list4.size()); // make sure always has 2 neighbors in the corner
		}
	}

	@Test
	public void TestFiniteBorderNumber(){
		initialize();
		myType = new EdgeType(new FiniteEdge());
		for(int i=1; i<myPatches.length-1;i++){
			List<Patch> list8 = myType.executeStrategy(myPatches, i, 0, xDelta8, yDelta8);
			assertEquals(5,list8.size()); // make sure always has 5 neighbors on the border, but not the corner
			List<Patch> list4 = myType.executeStrategy(myPatches, i, 0, xDelta4, yDelta4);
			assertEquals(3,list4.size()); // make sure always has 3 neighbors on the border, but not the corner
			List<Patch> list8_2 = myType.executeStrategy(myPatches, i, myPatches[0].length-1, xDelta8, yDelta8);
			assertEquals(5,list8_2.size()); // make sure always has 5 neighbors on the border, but not the corner
			List<Patch> list4_2 = myType.executeStrategy(myPatches, i, myPatches[0].length-1, xDelta4, yDelta4);
			assertEquals(3,list4_2.size()); // make sure always has 3 neighbors on the border, but not the corner
		}
		for(int j=1; j<myPatches[0].length-1;j++){
			List<Patch> list8 = myType.executeStrategy(myPatches, 0, j, xDelta8, yDelta8);
			assertEquals(5,list8.size()); // make sure always has 5 neighbors on the border, but not the corner
			List<Patch> list4 = myType.executeStrategy(myPatches, 0, j, xDelta4, yDelta4);
			assertEquals(3,list4.size()); // make sure always has 3 neighbors on the border, but not the corner
			List<Patch> list8_2 = myType.executeStrategy(myPatches, myPatches.length-1, j, xDelta8, yDelta8);
			assertEquals(5,list8_2.size()); // make sure always has 5 neighbors on the border, but not the corner
			List<Patch> list4_2 = myType.executeStrategy(myPatches, myPatches.length-1, j, xDelta4, yDelta4);
			assertEquals(3,list4_2.size()); // make sure always has 3 neighbors on the border, but not the corner
		}
	}
	@Test
	public void TestToroidalComplete(){
		initialize();
		myType = new EdgeType(new ToroidalEdge());
		for(int k = 0; k<myPatches.length;k++){
			for(int j = 0; j<myPatches[0].length;j++){
				List<Patch> list4 = myType.executeStrategy(myPatches, k, j, xDelta4, yDelta4);
				int ans4 = 0;
				for(Patch p: list4){
					for(int i = 0; i<xDelta4.length;i++){

						int x = (k + xDelta4[i]) % myPatches.length;
						if (x<0) x += myPatches.length;

						int y = (j + yDelta4[i]) % myPatches[0].length;
						if (y<0) y += myPatches[0].length;

						if(p.getCurrentX() == x && p.getCurrentY() == y) ans4++;
					}
				}
				assertEquals(4,ans4);
				
				List<Patch> list8 = myType.executeStrategy(myPatches, k, j, xDelta8, yDelta8);
				int ans8 = 0;
				for(Patch p: list8){
					for(int i = 0; i<xDelta8.length;i++){

						int x = (k + xDelta8[i]) % myPatches.length;
						if (x<0) x += myPatches.length;

						int y = (j + yDelta8[i]) % myPatches[0].length;
						if (y<0) y += myPatches[0].length;

						if(p.getCurrentX() == x && p.getCurrentY() == y) ans8++;
					}
				}
				assertEquals(8,ans8);
			}
		}

	}

}