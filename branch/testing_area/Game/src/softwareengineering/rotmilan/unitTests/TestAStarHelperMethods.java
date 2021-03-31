package softwareengineering.rotmilan.unitTests;

import softwareengineering.Direction;
import softwareengineering.rotmilan.*;

import org.junit.*;
//import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;



public class TestAStarHelperMethods {

	@Test
	public void testNeighbours() {
		
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[5][6];
		
		
		
		for(int i = 0; i < testArray.length; i++) {
			for(int j = 0; j < testArray[0].length; j++) {
				testArray[i][j] = 0;
			}
		}
		
		/*
		 * 0	0	0	0	0	0
		 * 0	0	0	0	0	0
		 * 0	0	0	0	0	0
		 * 0	0	0	0	0	0
		 * 0	0	0	0	0	0
		 * 
		 */
		
		
		testArray[1][1] = 1;	//start
		testArray[4][4] = -1;	//ziel
		
		testArray[0][0] = 2;
		testArray[1][0] = 2;
		testArray[2][0] = 2;
		testArray[0][1] = 2;
		testArray[2][1] = 2;
		testArray[0][2] = 2;
		testArray[1][2] = 2;
		testArray[2][2] = 2;
		
		/*
		 * 2	2	2	0	0	0
		 * 2	1	2	0	0	0
		 * 2	2	2	0	0	0
		 * 0	0	0	0	0	0
		 * 0	0	0	0	-4	0
		 * 
		 */

		
		Node start = astar.createNewNode(1, 1, testArray, 1, null, 6);
		Node ziel = astar.createNewNode(4, 4, testArray, 1, null, 6);

		int gameRows = 5;
		int gameColumns = 6;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, 1);
		
		//Nachbarn werden berechnet
		int i =  astar.findAllNeighbours(start, testArray, gameRows, gameColumns, 1, 1).size();
		assertEquals(0, i );
		
		//Der Startknoten liegt im Pfad, dh der Stein kann sich nicht bewegen wenn die Pfadgr��e 1 ist oder an Stelle 0
		//der Startknoten liegt
		assertEquals(1, path.getPathInNodes().size());
		
	}
	
	
	
}
