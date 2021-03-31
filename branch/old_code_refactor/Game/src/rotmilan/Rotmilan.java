package rotmilan;

import softwareengineering.*;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Random;

import softwareengineering.Game;
import softwareengineering.Player;

//Enumeration um GameObjects zu unterscheiden
enum Objecttype{
	STONE, GOAL;
}

public class Rotmilan implements Player {
	int color;
	int playernumber;
	int goal_row, goal_column;
	int gameRows, gameColumns;
	int[][] board_int;
	String name;
	Game game;
	GameObject ourGoal;
	ArrayList<GameObject> ourStones;
	ArrayList<GameObject> enemyStones;
	AStarAlgorithm astar;


	public Rotmilan(int color, String name, Game game) {
		this.color = color;
		this.name = name;
		this.game = game;
		gameRows = game.getNumRows();
		gameColumns = game.getNumColumns();
		board_int = new int[gameRows][gameColumns];
		astar = new AStarAlgorithm();
		boardIntEinlesen();
		parseBoardToObjectBoard();	
	}

	public void boardIntEinlesen() {
		for(int row = 0; row<gameRows;row++) {
			for(int column = 0; column < gameColumns;column++) {
				board_int[row][column] = game.getField(row, column);
			}
		}
	}

	//integer board auf Object boards mit Gegner und eigene Steine
	public void parseBoardToObjectBoard() {
		ourStones = new ArrayList<GameObject>();
		enemyStones = new ArrayList<GameObject>();
		for(int row = 0; row<gameRows;row++) {
			for(int column = 0; column < gameColumns;column++) {

				//unser Ziel
				if(board_int[row][column] == -color) {
					ourGoal = new GameObject(row, column,color,Objecttype.GOAL);
					goal_row = ourGoal.getRow();
					goal_column = ourGoal.getColumn();
					ourStones.add(ourGoal);
				}
				//unser Stein
				else if(board_int[row][column] == color) {
					ourStones.add(new GameObject(row, column,color,Objecttype.STONE));
				}
				//Gegner Stein
				else if(board_int[row][column] > 0) {
					enemyStones.add(new GameObject(row, column, board_int[row][column], Objecttype.STONE));
				}
				//Gegner Tor
				else if(board_int[row][column] < 0) {
					enemyStones.add(new GameObject(row, column, -(board_int[row][column]), Objecttype.GOAL));
				}
			}
		}
	}
	
	public String toString() {
		return name;
	}



	public int getColor() {
		return color;
	}


/* old function
	public Move nextMove() {

		int numRows = game.getNumRows();
		int numColumns = game.getNumColumns();

		for (int i=0; i<10000; i++) {

			int row = random.nextInt(numRows);
			int column = random.nextInt(numColumns);

			int value = game.getField(row, column);			
			if (value != color) continue;

			Direction direction = Direction.values()[random.nextInt(4)];
			Move move = new Move(row, column, direction);

			if (direction == Direction.UP && (game.getField((row+numRows-1)%numRows, column) == 0 || game.getField((row+numRows-1)%numRows, column) == -color)) return move;
			if (direction == Direction.DOWN && (game.getField((row+1)%numRows, column) == 0 || game.getField((row+1)%numRows, column) == -color)) return move;
			if (direction == Direction.LEFT && (game.getField(row, (column+numColumns-1)%numColumns) == 0 || game.getField(row, (column+numColumns-1)%numColumns) == -color)) return move;
			if (direction == Direction.RIGHT && (game.getField(row, (column+1)%numColumns) == 0 || game.getField(row, (column+1)%numColumns) == -color)) return move;
		}

		return null;
	}
*/
	
	public Move nextMove() {
		
		Node nextFieldNode = null;
		GameObject stoneToMove = null;
		//Vor jedem Zug das neue Board durchgehen
		this.boardIntEinlesen();
		this.parseBoardToObjectBoard();
		
		SteinAuswahl steinauswahl = new SteinAuswahl(goal_row, goal_column, gameRows, gameColumns);
		PathFinder pathfinder = new PathFinder();
		
		while(nextFieldNode == null) {
			stoneToMove = steinauswahl.bestenSteinAuswaehlen(ourStones);
			
			//returned die nächste Node im Path oder null wenn der Pfad null ist -> Stein kann sich nicht bewegen
			nextFieldNode = pathfinder.giveNextNodeInPath(stoneToMove, ourGoal, board_int, gameRows, gameColumns, ourStones, color);
			
		}
		//stoneToMove ist nur null, wenn kein Stein bewegt werden kann, also skippen!
		if(stoneToMove == null) {
			//null return ist skip
			return null;
		}
		
		Move move = new Move(stoneToMove.getRow(), stoneToMove.getColumn(), nextFieldNode.getDirection());
		return move;
		
	}



}
