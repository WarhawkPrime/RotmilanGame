package softwareengineering.rotmilan.unitTests;

import static org.junit.Assert.assertEquals;
import org.junit.*;

import softwareengineering.*;
import softwareengineering.rotmilan.*;

import java.util.ArrayList;

public class TestStoneChoice {
	@Test
	public void testTorusSteinAuswahl()
	{
	int goal_row = 4;
	int goal_column = 2;
	int gameRows = 6;
	int gameColumns = 5;
	
/*	
	0	0	1	0	2
	1	0	0	0	2
	0	0	-2	0	0
	0	0	0	0	2
	0	0	-1	0	2
	0	0	0	0	2
*/	
	int[][] board = new int[gameRows][gameColumns];
	
	board[0][2] = 1;
	board[1][0] = 1;
	board[goal_row][goal_column] = -1;
	
	int color = 1;
	//int goal = Objecttype.GOAL;
	ArrayList<Node> ourStones = new ArrayList<Node>();
	Node expected = new Node(0, 2, true);
	ourStones.add(expected);
	ourStones.add(new Node(1,0, true));
	//ourStones.add(new GameObject(0,2,color, 0));
	StoneChoice steinauswahl = new StoneChoice();
	
	Node actual = steinauswahl.chooseStoneToMove(new Node(4,2, false), ourStones, board, gameRows, gameColumns, color);
	assertEquals(expected, actual);
	}
	
	@Test
	public void testChooseStonesToBlock() {
		int goal_row = 4;
		int goal_column = 2;
		int enemy_goal_row = 2, enemy_goal_column =  2;
		int gameRows = 6;
		int gameColumns = 5;
		
		int color = 1;
		
	/*	
		1	0	1	0	2
		1	1	0	0	2
		1	0	-2	0	0
		0	0	0	0	2
		1	0	-1	0	2
		0	1	0	0	2
	*/	
		int[][] board = new int[gameRows][gameColumns];
		
		board[0][2] = color;
		board[1][0] = color;
		board[2][0] = color;
		board[1][1] = color;
		board[0][0] = color;
		board[4][0] = color;
		board[5][1] = color;
		
		board[0][4] = 2;
		board[1][4] = 2;
		board[3][4] = 2;
		board[4][4] = 2;
		board[5][4] = 2;
		
		board[goal_row][goal_column] = -color;
		board[enemy_goal_row][enemy_goal_column] = -2;
		
		ArrayList<Node> ourStones = new ArrayList<Node>();
		ArrayList<Node> actualNodes = new ArrayList<Node>();
		ArrayList<Node> expectedNodes = new ArrayList<Node>();
		Node expectedtop = new Node(0, 2, true);
		Node expectedbottom = new Node(4, 0, true);
		Node expectedleft = new Node(1, 1, true);
		Node expectedright = new Node(2, 0, true);
		
		expectedNodes.add(expectedtop);
		expectedNodes.add(expectedbottom);
		expectedNodes.add(expectedleft);
		expectedNodes.add(expectedright);
		
		ourStones.add(expectedtop);
		ourStones.add(expectedbottom);
		ourStones.add(expectedleft);
		ourStones.add(expectedright);
		
		ourStones.add(new Node(0,0, true));
		ourStones.add(new Node(4,0, true));
		ourStones.add(new Node(5,1, true));
		
		
		StoneChoice steinauswahl = new StoneChoice();
		
		for(Pair<Node, Node> nodepair : steinauswahl.chooseStonesToBlock(new Node(enemy_goal_row, enemy_goal_column, false),
				ourStones, board, gameRows, gameColumns, color)) {
			actualNodes.add(nodepair.getLeft());
		}
		
		assertEquals(expectedNodes, actualNodes);
	}
}