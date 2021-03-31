package rotmilan.unitTests;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import rotmilan.*;
import java.util.ArrayList;


public class TestPathFinder {
	
	
	@Test
	public void TestPathFinderBlocked() {
		
		int goal_row = 4;
		int goal_column = 2;
		int gameRows = 6;
		int gameColumns = 5;
		int[][] board = new int[gameRows][gameColumns];
		board[0][2] = 1;
		board[1][0] = 1;
		board[0][3] = 2;
		board[0][1] = 2;
		board[1][2] = 2;
		board[5][2] = 2;
		
	/*	
		0	2	1	2	0
		1	0	2	0	0
		0	0	-2	0	0
		0	0	0	0	0
		0	0	-1	0	0
		0	0	2	0	0
	*/	
		
		int color = 1;
		//int goal = Objecttype.GOAL;
		ArrayList<GameObject> ourStones = new ArrayList<GameObject>();
		GameObject best = new GameObject(0, 2, color);
		GameObject goal = new GameObject(goal_row, goal_column, color);
		ourStones.add(best);
		ourStones.add(new GameObject(1, 0, color));
		//ourStones.add(new GameObject(0,2,color, 0));
		SteinAuswahl steinauswahl = new SteinAuswahl(goal_row, goal_column, gameRows, gameColumns);
		
		best = steinauswahl.bestenSteinAuswaehlen(ourStones);
		
		PathFinder finder = new PathFinder();
		
		Node next = finder.giveNextNodeInPath(best, goal, board, gameRows, gameColumns, ourStones, color); //next sollte null sein, da der beste Stein blocked ist
		assertEquals(null, next);
		
	}
	
	@Test
	public void TestPathFinderBlockedNextBestStone() {
		
		int goal_row = 4;
		int goal_column = 2;
		int gameRows = 6;
		int gameColumns = 5;
		int[][] board = new int[gameRows][gameColumns];
		board[0][2] = 1;
		board[1][0] = 1;
		board[0][3] = 2;
		board[0][1] = 2;
		board[1][2] = 2;
		board[5][2] = 2;
		
	/*	
		0	2	1	2	0
		1	0	2	0	0
		0	0	-2	0	0
		0	0	0	0	0
		0	0	-1	0	0
		0	0	2	0	0
	*/	
		
		int color = 1;
		//int goal = Objecttype.GOAL;
		ArrayList<GameObject> ourStones = new ArrayList<GameObject>();
		GameObject best = new GameObject(0, 2, color);
		GameObject goal = new GameObject(goal_row, goal_column, color);
		ourStones.add(best);
		ourStones.add(new GameObject(1, 0, color));
		//ourStones.add(new GameObject(0,2,color, 0));
		SteinAuswahl steinauswahl = new SteinAuswahl(goal_row, goal_column, gameRows, gameColumns);
		
		best = steinauswahl.bestenSteinAuswaehlen(ourStones);
		
		PathFinder finder = new PathFinder();
		
		Node next = finder.giveNextNodeInPath(best, goal, board, gameRows, gameColumns, ourStones, color); //next sollte null sein, da der beste Stein blocked ist
		
		while(next == null) {
			best = steinauswahl.bestenSteinAuswaehlen(ourStones);
			next = finder.giveNextNodeInPath(best, goal, board, gameRows, gameColumns, ourStones, color); //waehle nun den Stein auf row=1, column=0
		}
		
		Node expected = new Node(0, 0, false);
		
		assertEquals(expected.getRow(), next.getRow());
		assertEquals(expected.getColumn(), next.getColumn());
		
	}
}
