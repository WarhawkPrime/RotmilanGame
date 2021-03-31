package rotmilan.unitTests;



import static org.junit.Assert.assertEquals;
import org.junit.*;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

import  rotmilan.*;

public class TestAStarAlgorithm {
	
	//test um die neighbors zu ermiteln
	
	

	//A* test. l�uft f�r jede Kombination von start und ziel durch
	@Test
	public void testAStar() {

		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[50][50];
		testArray[4][0] = 1;
		testArray[30][24] = -1;

		GameObject start = new GameObject( 4, 0, 1);
		GameObject ziel = new GameObject(30, 24, 1);

		int gameRows = 50;
		int gameColumns = 50;
		
		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, gameRows, gameColumns);
		//int i = astar.findShortestPath(start, ziel, testArray);
		ArrayList<Node> testArrayList = new ArrayList<Node>();
		Path testPath = new Path(testArrayList);


	}


	//Test um zu pr�fen wie sich der Algorithmus bei hindernissen verh�lt
	@Test
	public void testAStarWithBlockedFields() {

		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[10][10];
		testArray[4][7] = 1;	//start
		testArray[6][3] = -1;	//ziel

		//Blockade
		testArray[2][0] = 2;
		testArray[2][1] = 2;
		testArray[4][2] = 2;
		testArray[3][2] = 2;
		testArray[2][2] = 2;

		testArray[5][2] = 2;
		testArray[5][3] = 2;
		testArray[5][4] = 2;
		testArray[5][5] = 2;

		testArray[6][5] = 2;

		testArray[7][5] = 2;
		testArray[7][4] = 2;
		testArray[7][3] = 2;
		testArray[7][2] = 2;
		testArray[7][1] = 2;
		testArray[7][0] = 2;

		//0//	0	2	0	0	0	0	2	0	0	0
		//1//	0	2	0	0	0	0	2	0	0	0
		//2//	0	2	2	2	2	0	2	0	0	0
		//3//	0	0	0	0	2	-1	2	0	0	0
		//4//	0	0	0	0	2	0	2	0	0	0
		//5//	0	0	0	0	2	2	2	0	0	0
		//6//	0	0	0	0	0	0	0	0	0	0
		//7//	0	0	0	1	0	0	0	0	0	0
		//8//	0	0	0	0	0	0	0	0	0	0
		//9//	0	0	0	0	0	0	0	0	0	0

		GameObject start = new GameObject( 4, 7, 1);
		GameObject ziel = new GameObject(6, 3, 1);

		int gameRows = 10;
		int gameColumns = 10;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, gameRows, gameColumns);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(9 , path.getCompletePathInNodes().size());

	}
	
	
	@Test
	public void testNeighboursRechteck() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[6][3];
		testArray[1][1] = 1;	//start
		testArray[4][2] = -1;	//ziel

		//Blockade
		

		/*
		 * 0	0	0	0	0	0
		 * 0	1	0	0	0	0
		 * 0	0	0	0	-1	0
		 * 
		 * 
		 */

		GameObject start = new GameObject( 1, 1, 1);
		GameObject ziel = new GameObject(4, 2, 1);
		
		int gameRows = 3;
		int gameColumns = 6;


		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, gameRows, gameColumns);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(5 , path.getCompletePathInNodes().size());
	}
	
	@Test
	public void testNeighboursRechteck2() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[3][6];
		testArray[1][1] = 1;	//start
		testArray[2][5] = -1;	//ziel

		//Blockade
		

		/*
		 * 0	0	0
		 * 0	1	0	
		 * 0	0	0	
		 * 0	0	0
		 * 0	0	0
		 * 0	0	-1
		 */

		GameObject start = new GameObject( 1, 1, 1);
		GameObject ziel = new GameObject(2, 5, 1);

		int gameRows = 6;
		int gameColumns = 3;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, gameRows, gameColumns);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(4 , path.getCompletePathInNodes().size());
	}
	

	
	@Test
	public void testForBlockedStart() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[5][5];
		
		/*
		 * 2	2	2	0	0
		 * 2	1	2	0	0
		 * 2	2	2	0	0
		 * 0	0	0	0	0
		 * 0	0	0	0	-1
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
		

		GameObject start = new GameObject( 1, 1, 1);
		GameObject ziel = new GameObject(4, 4, 1);

		int gameRows = 5;
		int gameColumns = 5;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, gameRows, gameColumns);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		if(path != null) {
			assertEquals(null , path.getCompletePathInNodes().size());
		}
		
	}
	

	@Test
	public void testForBlockedStartWithSpace() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[5][5];
		
		testArray[1][1] = 1;	//start
		testArray[4][4] = -1;	//ziel
		
		testArray[0][0] = 2;
		testArray[1][0] = 2;
		testArray[2][0] = 2;
		testArray[3][0] = 2;
		
		testArray[0][1] = 2;
		testArray[2][2] = 2;
		testArray[0][2] = 2;
		testArray[0][3] = 2;
		
		testArray[1][3] = 2;
		testArray[2][3] = 2;
		testArray[3][3] = 2;
		testArray[3][4] = 2;
		

		GameObject start = new GameObject( 1, 1, 1);
		GameObject ziel = new GameObject(4, 4, 1);

		int gameRows = 5;
		int gameColumns = 5;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, gameRows, gameColumns);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(6 , path.getCompletePathInNodes().size());
	}
	
	
	
	@Test
	public void testHeuristik() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[10][10];
		testArray[1][1] = 1;
		testArray[1][5] = -1;

		int testArrayLenght = testArray.length;
		int testArrayYLenght = testArray[0].length;

		Node start = new Node(1, 1, false);
		Node end = new Node(1, 8, false);

		assertEquals(4, astar.calculateHeuristic(start, end, testArrayLenght, testArrayYLenght ));

	}

	//test ob der Pfad auch der k�rzeste ist
	@Test
	public void testPathForOptimun() {

		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[50][50];
		testArray[4][0] = 1;
		testArray[30][24] = -1;

		GameObject start = new GameObject( 4, 0, 1);
		GameObject ziel = new GameObject(30, 24, 1);

		int gameRows = 50;
		int gameColumns = 50;
		
		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, gameRows, gameColumns);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(49 , path.getCompletePathInNodes().size());


	}

	@Test
	public void testArrayLenght() {
		int[][] testArray = new int[5][8];
		testArray[1][1] = 1;
		testArray[4][4] = -1;

		int testArrayX = testArray.length;
		int testArrayY = testArray[0].length;
		assertEquals(5, (testArrayX ));
	}

	@Test
	public void testArrayLenght1D() {
		int[] D1testArray = new int[5];
		int D1testArrayLenght = D1testArray.length;

		assertEquals(5, D1testArrayLenght);
	}


}


