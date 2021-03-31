package softwareengineering.rotmilan.unitTests;



import static org.junit.Assert.assertEquals;
import org.junit.*;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

import softwareengineering.Move;
import softwareengineering.rotmilan.*;

public class TestStrategies {
	
	@Test
	public void testStoneChoice() {
		
		int [/*row*/][/*column*/] testarray = new int[10][15];
		
		for(int i = 0; i < testarray.length; i++) {
			for(int j = 0; j < testarray[0].length; j++) {
				testarray[i][j] = 0;
			}
		}
		
		testarray[0][0] = 1;
		testarray[0][1] = 1;
		testarray[0][2] = 1;
		testarray[0][3] = 1;
		testarray[0][4] = -1;
		
		Node start = new Node(0, 0, false);
		Node end = new Node(0, 4, false);
		
		DWStrategy dwStrategy = new DWStrategy();
		Node nodeToMove = null;
		
		
		
	}
	
	
}