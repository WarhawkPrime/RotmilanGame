package rotmilan;

import softwareengineering.*;

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
		board_int = new int[game.getNumRows()][game.getNumColumns()];
		astar = new AStarAlgorithm();
		boardIntEinlesen();
		parseBoardToObjectBoard();	
	}

	public void boardIntEinlesen() {
		for(int row = 0; row<game.getNumRows();row++) {
			for(int column = 0; column < game.getNumColumns();column++) {
				board_int[row][column] = game.getField(row, column);
			}
		}
	}

	//integer board auf Object boards mit Gegner und eigene Steine
	public void parseBoardToObjectBoard() {
		ourStones = new ArrayList<GameObject>();
		enemyStones = new ArrayList<GameObject>();
		for(int row = 0; row<game.getNumRows();row++) {
			for(int column = 0; column < game.getNumColumns();column++) {

				//unser Ziel
				if(board_int[row][column] == -color) {
					ourGoal = new GameObject(row, column,color,Objecttype.GOAL);
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

	/*heuristik : manhatten: 
	 *
	 *		h = abs(current_cell.x - goal.x) + abs(current_cell.y  goal.y)
	 *
	 *		hier ansetzten fuer den Torus!!!!!-> wenn der Abstand c.x - g.x groesser ist als die haelfte der gesamtlaenge von der x-Achse, dann kann die 
	 *		moeglichkeit des Torus genutzt werden und die rechnung aendert sich zu (das selbe gilt fuer die y achse): 
	 *
	 *	
	 *
	 *
	 *		h = abs[ ( (current_cell.x - board.size() ) + goal.x ) + 1 ] + abs[ ( (current_cell.y - board.size() ) + goal.y ) + 1 ]
	 *	
	 *		Dabei aendert sich dann natraeglich die Richtung, da der Nachbar mit dem geringsten f genommen wird.
	 *		 Nur beim bestimmen des Nachbars muss evtl die Richtung ueber  i % array.size() genutzt werden
	 *		(fuer zb den Sprung 0 -> 10)
	 *		keine andere for schleife wenn man den Index hat, rueckwaerts for macht nur sinn wenn man einzelne elemente rueckwaerts durchgehen will
	 *
	 */

	public double abstandBerechnen(int stone_x, int stone_y) {
		if((stone_x - goal_row) > ((game.getNumRows()-1) / 2) || (stone_y - goal_column) > ((game.getNumColumns()-1) / 2)) {
			return Math.abs(((stone_x - game.getNumRows()) + ourGoal.getRow()) + 1) + Math.abs(( (stone_y - game.getNumColumns()) +  ourGoal.getColumn()) + 1);
		}
		else {
			return Math.abs(stone_x  -  ourGoal.getRow()) + Math.abs(stone_y - ourGoal.getColumn());
		}
	}
	
	public GameObject steinAuswaehlen() {
		double abstandzuziel = -1;
		double tempAbstand;
		int currentMinAbstandStone = 0;
		int stone_x, stone_y;
		
		for (int i = 0; i<ourStones.size(); i++) {
			GameObject stone = ourStones.get(i);
			stone_x = stone.getRow();
			stone_y = stone.getColumn();
			if(stone.getType() == Objecttype.GOAL) {
				continue;
			}
			tempAbstand = abstandBerechnen(stone_x, stone_y);
			
			if(tempAbstand < abstandzuziel || abstandzuziel == -1) {
				abstandzuziel = tempAbstand;
				currentMinAbstandStone = i;
			}
		}
		return ourStones.get(currentMinAbstandStone);
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
		//Vor jedem Zug das neue Board durchgehen
		this.boardIntEinlesen();
		this.parseBoardToObjectBoard();
		
		
		Direction direction = null;
		GameObject stoneToMove = steinAuswaehlen();
		Path path = astar.findShortestPath(stoneToMove, ourGoal, board_int);
		stoneToMove.setGameObjectPath(path);
		
		ArrayList<Node> pathinnodes = path.getCompletePathInNodes();
		Node currentNode = pathinnodes.get(pathinnodes.size()-2);
		
		
		//Toruseigenschaft muss hier noch eingebaut werden!
		if(currentNode.getPosX() == stoneToMove.getRow() && currentNode.getPosY() == stoneToMove.getColumn()+1) {
			 direction = Direction.RIGHT;
		}
		if(currentNode.getPosX() == stoneToMove.getRow() && currentNode.getPosY() == stoneToMove.getColumn()-1) {
			 direction = Direction.LEFT;
		}
		if(currentNode.getPosX() == stoneToMove.getRow()+1 && currentNode.getPosY() == stoneToMove.getColumn()) {
			 direction = Direction.DOWN;
		}
		if(currentNode.getPosX() == stoneToMove.getRow()-1 && currentNode.getPosY() == stoneToMove.getColumn()) {
			 direction = Direction.UP;
		}
		
		Move move = new Move(stoneToMove.getRow(), stoneToMove.getColumn(), direction);
		return move;
		
	}



}
