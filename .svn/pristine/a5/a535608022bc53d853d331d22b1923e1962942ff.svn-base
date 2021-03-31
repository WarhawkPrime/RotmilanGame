package rotmilan;

import java.util.ArrayList;

import softwareengineering.Direction;
import softwareengineering.Game;
import softwareengineering.Move;
import softwareengineering.Player;

public class RotmilanNeu implements Player{

	int color;
	int playernumber;
	int goal_row, goal_column;
	int gameRows, gameColumns;
	int movesSkipped;
	int[][] board_int;
	String name;
	Game game;
	GameObject ourGoal;
	ArrayList<GameObject> ourStones;
	ArrayList<GameObject> enemyStones;
	
	
	public RotmilanNeu(int color, String name, Game game) {
		
		this.color = color;
		this.name = name;
		this.game = game;
		gameRows = game.getNumRows();
		gameColumns = game.getNumColumns();
		board_int = new int[gameRows][gameColumns];
		movesSkipped = 0;
		ourStones = new ArrayList<GameObject>();
		enemyStones = new ArrayList<GameObject>();
		boardIntEinlesen();
		parseBoardToObjectBoard();
	}
	
	public void boardIntEinlesen() {
		for (int row = 0; row < gameRows; row++) {
			for (int column = 0; column < gameColumns; column++) {
				board_int[row][column] = game.getField(row, column);
			}
		}
	}

	// integer board auf Object boards mit Gegner und eigene Steine
	public void parseBoardToObjectBoard() {
		this.ourStones = new ArrayList<GameObject>();
		enemyStones = new ArrayList<GameObject>();

		for (int row = 0; row < gameRows; row++) {
			for (int column = 0; column < gameColumns; column++) {

				// unser Ziel
				if (board_int[row][column] == -color) {
					ourGoal = new GameObject(row, column, color, Objecttype.GOAL);
					goal_row = ourGoal.getRow();
					goal_column = ourGoal.getColumn();
					ourStones.add(ourGoal);
				}
				// unser Stein
				else if (board_int[row][column] == color) {
					ourStones.add(new GameObject(row, column, color, Objecttype.STONE));
				}
				// Gegner Stein
				else if (board_int[row][column] > 0) {
					enemyStones.add(new GameObject(row, column, board_int[row][column], Objecttype.STONE));
				}
				// Gegner Tor
				else if (board_int[row][column] < 0) {
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
	
	
	public Move nextMove() {
		int skippedTurns = 0;
		Pathfinder pathfinder = new Pathfinder();
		ArrayList<Node> list = new ArrayList<>();
		ArrayList<GameObject> listWithoutGoal = new ArrayList<>();
		this.boardIntEinlesen();
		this.parseBoardToObjectBoard();
		
		
		
		for(int i = 0; i < ourStones.size(); i++) {
			
			if(! (ourStones.get(i).getColumn() == ourGoal.getColumn() && ourStones.get(i).getRow() == ourGoal.getRow())) {
				listWithoutGoal.add(ourStones.get(i));
			}
		}
		for(int i = 0; i < listWithoutGoal.size(); i++) {
			
			Node n1 = new Node(listWithoutGoal.get(i).getColumn(), listWithoutGoal.get(i).getRow(), true, null);
			list.add(n1);
		}
		
		Node endNode = new Node(this.goal_column, this.goal_row, false, null);
		Node nextNode = pathfinder.chooseStoneToMove(list, endNode, board_int, getColor());
		
		Direction direction;
		Move move;
		direction = pathfinder.getNextDirection(board_int, getColor(), nextNode, endNode);
		
		if(nextNode == null)
			return null;
		else {
			
			if(direction == null) {
				nextNode = pathfinder.chooseStoneToMove(list, endNode, board_int, getColor());
			}
			else {
				move = new Move(nextNode.getPosY(),nextNode.getPosX(), direction);
				
				
					System.out.println("null");
				return move;
			}
		}
				
		return null;
	}
	
	
	
}
