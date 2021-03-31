package softwareengineering.rotmilan.unitTests;



import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.*;

import softwareengineering.rotmilan.*;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class TestAStarAlgorithm {

	//test um die neighbors zu ermiteln

	@Test
	public void testaS() {
		int numRows;
		int numColumns;
		int[][] board;

		String filename = "C:\\Users\\denni\\git\\AStarAlgorithm\\src\\testFile"; //filename

		if (new File(filename).exists() == false) {
			throw new RuntimeException("Game file " + filename + " not found");
		}



		ArrayList<String> lines = new ArrayList<>();
		try {
			BufferedReader f = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = f.readLine();
				if (line == null) break;
				lines.add(line);
			}
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		numRows = lines.size();
		numColumns = lines.get(0).split("\t").length;
		board = new int[numRows][numColumns];



		for (int row=0; row<numRows; row++) {
			String line = lines.get(row);
			String [] fields = line.split("\t");
			assert fields.length == numColumns;
			for (int column=0; column<numColumns; column++) {
				board[row][column] = Integer.valueOf(fields[column]);
			}
		}



		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}


		AStarAlgorithm astar = new AStarAlgorithm();
		Node start = null;
		Node end = null;

		ArrayList<Node> startL = new ArrayList<Node>();
		ArrayList<Node> endL = new ArrayList<Node>();

		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				if(board[i][j] == 1) {
					//start = astar.createNewNode(i, j, board, 1, null);
				}
				if(board[i][j] == -1) {
					end = astar.createNewNode(i, j, board, 1, null, numColumns);
				}
			}
		}

		start = astar.createNewNode(4, 8, board, 1, null, numColumns);

		Path foundPath = astar.findShortestPath(start, end, board, 1);
		start.setPathToStart(foundPath);
		int nodeCount = foundPath.getPathInNodes().size();

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		for(int k = 0; k < foundPath.getPathInNodes().size(); k++ ) {
			board[foundPath.getPathInNodes().get(k).getRowPosition()][foundPath.getPathInNodes().get(k).getColumnPosition()] = 7;
			System.out.println("Schritt " + k +":   " + " R " + foundPath.getPathInNodes().get(k).getRowPosition() + " C " + foundPath.getPathInNodes().get(k).getColumnPosition());
		}

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		board[start.getRowPosition()][start.getColumnPosition()] = 1;
		board[end.getRowPosition()][end.getColumnPosition()] = -1;

		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}

		assertEquals(1, nodeCount);
	}

	
	//A* test. l�uft f�r jede Kombination von start und ziel durch
	@Test
	public void testAStar() {

		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[50][50];
		testArray[4][0] = 1;
		testArray[30][24] = -1;

		//GameObject start = new GameObject( 4, 0, 1);
		//GameObject ziel = new GameObject(30, 24, 1);

		Node start = astar.createNewNode(4, 0, testArray, 1, null, 50);

		Node ziel = astar.createNewNode(30, 24, testArray, 1, null, 50);

		int gameRows = 50;
		int gameColumns = 50;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, 1);

		//assertEquals(1, path.getPathInNodes().size() );
		assertEquals(1, 2);

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


		Node start = astar.createNewNode(4, 7, testArray, 1, null, 10);
		Node ziel = astar.createNewNode(6, 3, testArray, 1, null, 10);

		int gameRows = 10;
		int gameColumns = 10;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, 1);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(9 , path.getPathInNodes().size());

	}


	@Test
	public void testNeighboursRechteck() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[6][3];
		testArray[1][1] = 1;	//start
		testArray[4][2] = -1;	//ziel


		Node start = astar.createNewNode(1, 1, testArray, 1, null, 3);
		Node ziel = astar.createNewNode(4, 2, testArray, 1, null, 3);
		
		int gameRows = 6;
		int gameColumns = 3;


		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, 1);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(5 , path.getPathInNodes().size());
	}

	@Test
	public void testNeighboursRechteck2() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[3][6];
		testArray[1][1] = 1;	//start
		testArray[2][5] = -1;	//ziel

		//Blockade


		Node start = astar.createNewNode(1, 1, testArray, 1, null, 6);
		Node ziel = astar.createNewNode(2, 5, testArray, 1, null, 6);

		int gameRows = 3;
		int gameColumns = 6;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, 1);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(4 , path.getPathInNodes().size());
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



		Node start = astar.createNewNode(1, 1, testArray, 1, null, 5);
		Node ziel = astar.createNewNode(4, 4, testArray, 1, null, 5);


		int gameRows = 5;
		int gameColumns = 5;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, 1);
		//int i = astar.findShortestPath(start, ziel, testArray);

		assertEquals(0, path.getPathInNodes().size());

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



		Node start = astar.createNewNode(1, 1, testArray, 1, null, 5);
		Node ziel = astar.createNewNode(4, 4, testArray, 1, null, 5);

		int gameRows = 5;
		int gameColumns = 5;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, 1);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(6 , path.getPathInNodes().size());
	}



	@Test
	public void testHeuristik() {
		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[10][10];
		testArray[1][1] = 1;
		testArray[1][5] = -1;

		int testArrayLenght = testArray.length;
		int testArrayYLenght = testArray[0].length;

		Node start = astar.createNewNode(1, 1, testArray, 1, null, 10);
		Node ziel = astar.createNewNode(1, 8, testArray, 1, null, 10);

		assertEquals(3, astar.calculateHeuristic(start, ziel, testArrayLenght, testArrayYLenght) );

	}

	//test ob der Pfad auch der k�rzeste ist
	@Test
	public void testPathForOptimun() {

		AStarAlgorithm astar = new AStarAlgorithm();

		int[][] testArray = new int[50][50];
		testArray[4][0] = 1;
		testArray[30][24] = -1;



		Node start = astar.createNewNode(4, 0, testArray, 1, null, 10);
		Node ziel = astar.createNewNode(30, 24, testArray, 1, null, 10);

		int gameRows = 50;
		int gameColumns = 50;

		//assertEquals(null, findShortestPath(start, ziel, testArray ) );
		Path path = astar.findShortestPath(start, ziel, testArray, 1);
		//int i = astar.findShortestPath(start, ziel, testArray);

		//null wird hier nicht erwartet sondern als path. dient dazu es beim durchlauf zu sehen
		assertEquals(49 , path.getPathInNodes().size());

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


