package JUnit_Tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import Cell.FishCell;
import Cell.PredPreyCell;
import Cell.SharkCell;
import Patch.GenericPatch;
import Patch.Patch;

public class codeMasterpiece_Tests {
	
	public static final double WATER = 0;
	public static final double FISH = 1;
	public static final double SHARK = 2;
	public static final int INITIAL_CHRONONS = 0;
	public static final int INITIAL_HUNGER = 0;
	public static final double DEFAULT_PATCH = 0;
	
	private Map<String,String> map;
	private Patch[][] patches;
	
	//Map is empty, FishCell will use default values
	private void init(){
		map = new HashMap<String,String>();
		patches = new Patch[5][5]; //small array for testing
	}

	@Test
	public void testBreeding(){
		init();
		PredPreyCell f = new FishCell(FISH,0,0,map);
		//DEFAULT_BREED IS SET TO 3
		
		patches[0][0] = new GenericPatch(f, 1, 0, 0, map);
		f.updateStateandMove(patches);
		f.updateStateandMove(patches);
		assertFalse(f.checkBreed(patches));  //breed at 2 will fail
		
		
		f.updateStateandMove(patches);
		assertTrue(f.checkBreed(patches));   //breed at 3 will pass
	}
	
	@Test
	public void testSharkEat(){
		init();
		FishCell f = new FishCell(FISH,0,0,map);
		SharkCell s = new SharkCell(SHARK,0,1,map);
		
		patches[0][0] = new GenericPatch(f, DEFAULT_PATCH, 0, 0,map);
		patches[0][1] = new GenericPatch(s, DEFAULT_PATCH, 0, 1, map);
		f.updateStateandMove(patches);
		assertFalse(f.isEaten());
		
		s.updateStateandMove(patches);
		assertTrue(f.isEaten());
	}
	
	@Test
	public void testMovement(){
		init();
		FishCell f = new FishCell(FISH,0,0,map);
		PredPreyCell w = new PredPreyCell(WATER,0,1,map);
		patches[0][0] = new GenericPatch(f, DEFAULT_PATCH, 0, 0,map);
		patches[0][1] = new GenericPatch(w, DEFAULT_PATCH, 0, 1, map);
		f.updateStateandMove(patches);
		assertTrue(f.getFutureX()==0);
		assertTrue(f.getFutureY()==1);
		
		f = new FishCell(FISH,0,0,map);
		w = new PredPreyCell(WATER,0,1,map);
		FishCell f2 = new FishCell(FISH,0,0,map);
		patches[0][0] = new GenericPatch(f, DEFAULT_PATCH, 0, 0,map);
		patches[0][1] = new GenericPatch(w, DEFAULT_PATCH, 0, 1, map);
		patches[1][0] = new GenericPatch(f2, DEFAULT_PATCH, 0, 1, map);
		f.updateStateandMove(patches);
		assertTrue(f.getFutureX()==0);
		assertTrue(f.getFutureY()==1);
	}
	
	
}
