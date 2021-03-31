package rotmilan.unitTests;



import static org.junit.Assert.assertEquals;
import org.junit.*;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

import  rotmilan.*;

public class TestAStarAlgorithm {

	//A* test. l�uft f�r jede Kombination von start und ziel durch
	@Test
	public void testAStar() {

		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[50][50];
		int color = 1;
		int gameRows = 50;
		int gameColumns = 50;
		
		testArray[4][0] = 1;
		testArray[30][24] = -1;

		GameObject start = new GameObject( 4, 0, 1);
		GameObject ziel = new GameObject(30, 24, 1);

		Node startnode = new Node(start.getRow(), start.getColumn(), true);
		Node zielnode = new Node(ziel.getRow(), ziel.getColumn(), false);
		
		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(startnode, zielnode, testArray, gameRows, gameColumns, color);
		//int i = astar.findShortestPath(start, ziel, testArray);
		ArrayList<Node> testArrayList = new ArrayList<Node>();
		Path testPath = new Path(testArrayList);


		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(testPath , path);
	}


	//Test um zu pr�fen wie sich der Algorithmus bei hindernissen verh�lt
	@Test
	public void testAStarWithBlockedFields() {

		AStarAlgorithm astar = new AStarAlgorithm();
		int color = 1;
		int[][] testArray = new int[10][10];
		int gameRows = 10;
		int gameColumns = 10;
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

		Node startnode = new Node(start.getRow(), start.getColumn(), true);
		Node zielnode = new Node(ziel.getRow(), ziel.getColumn(), false);

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(startnode, zielnode, testArray, gameRows, gameColumns, color);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(9 , path.getCompletePathInNodes().size());

	}
	
	
	@Test
	public void testNeighbours() {
		AStarAlgorithm astar = new AStarAlgorithm();

		
		int color = 1;
		int[][] testArray = new int[10][10];
		int gameRows = 10;
		int gameColumns = 10;
		
		testArray[0][0] = 1;	//start
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

		//0//	1	2	0	0	0	0	2	0	0	0
		//1//	0	2	0	0	0	0	2	0	0	0
		//2//	0	2	2	2	2	0	2	0	0	0
		//3//	0	0	0	0	2	-1	2	0	0	0
		//4//	0	0	0	0	2	0	2	0	0	0
		//5//	0	0	0	0	2	2	2	0	0	0
		//6//	0	0	0	0	0	0	0	0	0	0
		//7//	0	0	0	0	0	0	0	0	0	0
		//8//	0	0	0	0	0	0	0	0	0	0
		//9//	0	0	0	0	0	0	0	0	0	0

		GameObject start = new GameObject( 4, 7, 1);
		GameObject ziel = new GameObject(6, 3, 1);
		
		Node startnode = new Node(start.getRow(), start.getColumn(), true);
		Node zielnode = new Node(ziel.getRow(), ziel.getColumn(), false);


		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(startnode, zielnode, testArray, gameRows, gameColumns, color);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(9 , path.getCompletePathInNodes().size());
	}



	@Test
	public void testHeuristik() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[10][10];
		int gameRows = 10;
		int gameColumns = 10;
		testArray[1][1] = 1;
		testArray[1][8] = -1;

		Node start = new Node(9, 8, false);
		Node end = new Node(1, 1, false);

		assertEquals(7, astar.calculateHeuristic(start, end, gameRows, gameColumns ));

	}

	//test ob der Pfad auch der k�rzeste ist
	@Test
	public void testPathForOptimun() {

		AStarAlgorithm astar = new AStarAlgorithm();
		int color = 1;
		int[][] testArray = new int[50][50];
		
		int gameRows = 50;
		int gameColumns = 50;
		
		testArray[4][0] = 1;
		testArray[30][24] = -1;

		GameObject start = new GameObject( 4, 0, 1);
		GameObject ziel = new GameObject(30, 24, 1);

		Node startnode = new Node(start.getRow(), start.getColumn(), true);
		Node zielnode = new Node(ziel.getRow(), ziel.getColumn(), false);
		
		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(startnode, zielnode, testArray, gameRows, gameColumns, color);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(49 , path.getCompletePathInNodes().size());


	}

	//werden hier nicht mehr benötigt
//	@Test
//	public void testArrayLenght() {
//		int[][] testArray = new int[5][8];
//		testArray[1][1] = 1;
//		testArray[4][4] = -1;
//
//		int testArrayLenght = testArray.length;
//		int testArrayY = testArray[0].length;
//		assertEquals(8, (testArrayY ));
//	}
//
//	@Test
//	public void testArrayLenght1D() {
//		int[] D1testArray = new int[5];
//		int D1testArrayLenght = D1testArray.length;
//
//		assertEquals(5, D1testArrayLenght);
//	}


}


