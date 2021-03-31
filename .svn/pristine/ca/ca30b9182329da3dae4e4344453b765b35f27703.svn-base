package softwareengineering.rotmilan.unitTests;
import softwareengineering.*;
import softwareengineering.rotmilan.*;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class TestBlockTorStrategy {

	@Test
	public void testBlockTorAnalyse1() {
		int goal_row = 4;
		int goal_column = 2;
		int enemy_goal_row = 2, enemy_goal_column =  2;
		int gameRows = 6;
		int gameColumns = 5;
		
		int color = 1;
		
	/*	
		1	1	1	0	2
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
		board[0][1] = color;
		
		board[0][4] = 2;
		board[1][4] = 2;
		board[3][4] = 2;
		board[4][4] = 2;
		board[5][4] = 2;
		
		board[goal_row][goal_column] = -color;
		board[enemy_goal_row][enemy_goal_column] = -2;
		
		ArrayList<Node> ourStones = new ArrayList<Node>();
		
		Node enemyGoalNode = new Node(enemy_goal_row, enemy_goal_column, false);
		
		ourStones.add(new Node(0, 2, true));
		ourStones.add(new Node(1, 1, true));
		ourStones.add(new Node(4, 0, true));
		ourStones.add(new Node(2, 0, true));
		
		ourStones.add(new Node(0,0, true));
		ourStones.add(new Node(4,0, true));
		ourStones.add(new Node(5,1, true));
		ourStones.add(new Node(0,1, true));
		
		Strategy blocktorStrategy = new BlockTorStrategy(board, gameRows, gameColumns, color, enemyGoalNode, ourStones);
		assertEquals(1, blocktorStrategy.analyze());		
	}
	
	@Test
	public void testBlockTorAnalyse2() {
		int goal_row = 2;
		int goal_column = 2;
		int enemy_goal_row = 5, enemy_goal_column =  4;
		int gameRows = 6;
		int gameColumns = 5;
		
		int color = 1;
		
	/*	
		1	0	1	0	2
		1	1	0	0	2
		1	0	-1	0	0
		0	0	0	0	2
		1	0	0	0	2
		0	1	0	0	-2
	*/	
		int[][] board = new int[gameRows][gameColumns];
		
		board[0][2] = color;
		board[1][0] = color;
		board[2][0] = color;
		board[1][1] = color;
		board[0][0] = color;
		board[4][0] = color;
		board[5][1] = color;
		board[0][1] = color;
		
		board[0][4] = 2;
		board[1][4] = 2;
		board[3][4] = 2;
		board[4][4] = 2;
		
		board[goal_row][goal_column] = -color;
		board[enemy_goal_row][enemy_goal_column] = -2;
		
		ArrayList<Node> ourStones = new ArrayList<Node>();
		
		Node enemyGoalNode = new Node(enemy_goal_row, enemy_goal_column, false);
		
		ourStones.add(new Node(0, 2, true));
		ourStones.add(new Node(1, 1, true));
		ourStones.add(new Node(4, 0, true));
		ourStones.add(new Node(2, 0, true));
		
		ourStones.add(new Node(0,0, true));
		ourStones.add(new Node(4,0, true));
		ourStones.add(new Node(5,1, true));
		ourStones.add(new Node(0,1, true));
		
		Strategy blocktorStrategy = new BlockTorStrategy(board, gameRows, gameColumns, color, enemyGoalNode, ourStones);
		assertEquals(-1, blocktorStrategy.analyze());
	}
	
	//Tor ist schon blockiert
	@Test
	public void testBlockTorAnalyse3() {
		int goal_row = 4;
		int goal_column = 2;
		int enemy_goal_row = 2, enemy_goal_column =  2;
		int gameRows = 6;
		int gameColumns = 5;
		
		int color = 1;
		
	/*	
		0	0	0	0	2
		0	0	1	0	2
		0	1	-2	1	0
		0	0	1	0	2
		0	0	-1	0	2
		0	0	0	0	2
	*/	
		int[][] board = new int[gameRows][gameColumns];
		
		board[enemy_goal_row-1][enemy_goal_column] = color;
		board[enemy_goal_row+1][enemy_goal_column] = color;
		board[enemy_goal_row][enemy_goal_column+1] = color;
		board[enemy_goal_row][enemy_goal_column-1] = color;
		
		board[0][4] = 2;
		board[1][4] = 2;
		board[3][4] = 2;
		board[4][4] = 2;
		board[5][4] = 2;
		
		board[goal_row][goal_column] = -color;
		board[enemy_goal_row][enemy_goal_column] = -2;
		
		ArrayList<Node> ourStones = new ArrayList<Node>();
		
		Node enemyGoalNode = new Node(enemy_goal_row, enemy_goal_column, false);
		
		ourStones.add(new Node(enemy_goal_row+1, enemy_goal_column, true));
		ourStones.add(new Node(enemy_goal_row-1, enemy_goal_column, true));
		ourStones.add(new Node(enemy_goal_row, enemy_goal_column+1, true));
		ourStones.add(new Node(enemy_goal_row, enemy_goal_column-1, true));
		
		Strategy blocktorStrategy = new BlockTorStrategy(board, gameRows, gameColumns, color, enemyGoalNode, ourStones);
		assertEquals(-1, blocktorStrategy.analyze());
	}
	
	@Test
	public void testBlockStrategyGetMove() {
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
		board[0][1] = color;
		
		board[0][4] = 2;
		board[1][4] = 2;
		board[3][4] = 2;
		board[4][4] = 2;
		board[5][4] = 2;
		
		board[goal_row][goal_column] = -color;
		board[enemy_goal_row][enemy_goal_column] = -2;
		
		ArrayList<Node> ourStones = new ArrayList<Node>();
		
		Node enemyGoalNode = new Node(enemy_goal_row, enemy_goal_column, false);
		
		ourStones.add(new Node(0, 2, true));
		ourStones.add(new Node(1, 1, true));
		ourStones.add(new Node(4, 0, true));
		ourStones.add(new Node(2, 0, true));
		
		ourStones.add(new Node(0,0, true));
		ourStones.add(new Node(4,0, true));
		ourStones.add(new Node(5,1, true));
		ourStones.add(new Node(0,1, true));
		
		Strategy blocktorStrategy = new BlockTorStrategy(board, gameRows, gameColumns, color, enemyGoalNode, ourStones);
		Move actual = blocktorStrategy.getMove();
		
		assertEquals(1, actual.column);
		assertEquals(1, actual.row);
		assertEquals(Direction.RIGHT, actual.direction);
	}
}
