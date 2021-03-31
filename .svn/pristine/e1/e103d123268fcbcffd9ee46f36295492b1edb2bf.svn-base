package rotmilan;

import softwareengineering.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import softwareengineering.Game;
import softwareengineering.Player;

//Enumeration um GameObjects zu unterscheiden
enum Objecttype {
	STONE, GOAL;
}

public class Rotmilan implements Player {
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
	AStarAlgorithm astar;

	public Rotmilan(int color, String name, Game game) {
		this.color = color;
		this.name = name;
		this.game = game;
		gameRows = game.getNumRows();
		gameColumns = game.getNumColumns();
		board_int = new int[gameRows][gameColumns];
		movesSkipped = 0;
		// ourStones = new ArrayList<GameObject>();
		// enemyStones = new ArrayList<GameObject>();
		astar = new AStarAlgorithm();
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
		ourStones = new ArrayList<GameObject>();
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

	// Muss de n�chst besten Stein durchgehen bis alle steine durchgegangen sind
	public GameObject steinAuswaehlen(ArrayList<GameObject> ourStones) {
		int stone_x, stone_y;
		int checkedStones = 0;
		int totalpossiblemoves = 0;
		AStarAlgorithm as = new AStarAlgorithm();

		// PriorityQueue um unsere elemente zu sortieren und eins nach dem anderen
		// durchzugehen. Damit ist es auch egal ob das Array jedes mal neu erstellt wird
		// oder aktualisiert wird (wie es besser w�re)
		Comparator<GameObject> comparator = new GameObjectComparator();
		PriorityQueue<GameObject> orderedOurStones = new PriorityQueue<GameObject>(comparator);
		// queue leeren um nicht alte Werte zu halten
		orderedOurStones.clear();

		// queue bef�llen mit der berechnung vom abstand
		for (GameObject ownStone : ourStones) {
			stone_x = ownStone.getColumn();
			stone_y = ownStone.getRow();
			if (ownStone.getType() == Objecttype.STONE) {
				Node currentStone = as.createNewNode(stone_y, stone_x, board_int, color, null);
				Node goal = as.createNewNode(goal_row, goal_column, board_int, color, null);
				ownStone.setTempCalculatedHDistanceToTarget(as.calculateHeuristic(currentStone, goal, gameRows, gameColumns));
				orderedOurStones.add(ownStone);
			}

			//Für alle eigenen Steine den pfad berechnen um zu kalkulieren Wie viele mögliche Züge wir haben
			//Sehr unperformant hilft aber beim abwägen ob man den Zug skippen sollte
			Path path = astar.findShortestPath(ownStone, ourGoal, board_int, gameRows, gameColumns); // pfad f�r diesen Kandidaten wird
			
			if(path == null)
				throw new RuntimeException("Path was null in steinAuswahlen");
			
			ownStone.setGameObjectPath(path);
			if(path != null)
				totalpossiblemoves ++;
		}
		
		// queue wurde bef�llt, nun f�r jeweils das n�chstbeste den Pfad berechnen und
		// wiederholen wenn der Pfad null ist (wenn kein Pfad existieren konnte
		// solange die queue nicht leer ist (wir noch steine haben die wir nicht
		// getestet haben ob sie sich bewegt haben
		while (!orderedOurStones.isEmpty()) {
			checkedStones++;
			GameObject stoneToMove;
			System.out.println("Ordered stones size: " + orderedOurStones.size());
			stoneToMove = orderedOurStones.poll(); // bester kandidat wird aus der queue geholt
			/*
			Path path = astar.findShortestPath(stoneToMove, ourGoal, board_int); // pfad f�r diesen Kandidaten wird
																					// berechnet
			*/
			//neu 
			Path path = stoneToMove.getGameObjectPath();
			
			if (path != null) { // wenn der Pfad nicht null ist, kann der stein zur�ckgegeben werden
				
				stoneToMove.setGameObjectPath(path);
				
				//movesskipped wird auf 0 gesetzt da nur die anzahl der direkt hintereinander geskippten Züge wichtig ist
				movesSkipped = 0;
				return stoneToMove;
			} else {
				// Prüfen ob Skippen an dieser Stelle sinn macht oder ob weiter nach einem
				// möglichen zug gesucht werden soll
				
				if (skipMove(checkedStones,ourStones.size(),totalpossiblemoves)) {
					return null;
				}
				
			}
		}
		// geht hierhin wenn die Liste leer ist. Kein Zug m�glich
		return null;
	}

	
	// Methode zum Prüfen ob das Skippen des Zuges Sinnvoll ist 
	// false = nicht skippen wenn Möglich true = skippen
	public boolean skipMove(int numcheckedStones, int totalStones,int numpossibleMoves) {
		
		//Ansatz:	Wenn noch nicht geskipped wurde wird geskippt sobald der optimalste stein keinen Weg hat
		//			Wenn schon 1 mal geskipped wurde werden noch die hälfte der verbleibenden Steine geprüft und dann geskipped
		//			Falls die anzahl der insgesamt möglichen Züge nicht geringer ist als die anzahl der Gegenspieler 
		//			da wir sonst zugestellt werden können
		//			Wenn schon 2 mal geskipped wurde werden alle verbleibenden Steine nach möglichen Zügen geprüft
		switch (movesSkipped) {
		case 0:
			return true;
		case 1:
			if(numpossibleMoves < game.getNumPlayers()-1) {
				return false;
			}else {
				if(numcheckedStones <= (totalStones % 2))
					return false;
				else
					return true;	
			}
			
		case 2:
			return false;
		default:
			break;
		}
		return false;
	}

	public Move nextMove() {
		// Vor jedem Zug das neue Board durchgehen
		this.boardIntEinlesen();
		this.parseBoardToObjectBoard();

		Direction direction = null;
		// Stein, der bewegt wird
		/*
		 * GameObject stoneToMove = steinAuswaehlen(ourStones); Path path =
		 * astar.findShortestPath(stoneToMove, ourGoal, board_int);
		 * stoneToMove.setGameObjectPath(path);
		 */
		GameObject stoneToMove = steinAuswaehlen(ourStones);

		// Wenn die Methode steinAuswaehlen Null liefert ist kein Zug möglich und es
		// wird null zurückgegeben (Skip)
		// Es macht an dieser Stelle keinen Sinn zu prüfen wie oft schon geskippt wurde
		// da so oder so kein zug möglich wäre
		if (stoneToMove == null) {
			movesSkipped ++;
			return null;
		}
		/*
		 * ArrayList<Node> pathinnodes = path.getCompletePathInNodes(); //Feld, dass als
		 * naechstes betreten werden soll Node naechstesFeldNode =
		 * path.getNextNode(stoneToMove.getRow(), stoneToMove.getColumn());
		 */
		// neu:
		Node naechstesFeldNode = stoneToMove.getGameObjectPath().getNextNode(stoneToMove.getColumn(), stoneToMove.getRow());
		direction = naechstesFeldNode.getDirection();
		
		if(direction == null)
			System.out.println("Direction is null");

		// Feld, dass als naechstes betreten werden soll
		// Node currentNode = pathinnodes.get(pathinnodes.size()-2);
		// Node naechstesFeldNode = pathinnodes.get(pathinnodes.size() -2);
/*
		if (naechstesFeldNode.getPosX() == stoneToMove.getRow() && naechstesFeldNode
				.getPosY() == ((((stoneToMove.getColumn() + 1) % gameColumns) + gameColumns) % gameColumns)) {
			direction = Direction.RIGHT;
		}
		if (naechstesFeldNode.getPosX() == stoneToMove.getRow() && naechstesFeldNode
				.getPosY() == ((((stoneToMove.getColumn() - 1) % gameColumns) + gameColumns) % gameColumns)) {
			direction = Direction.LEFT;
		}
		if (naechstesFeldNode.getPosX() == ((((stoneToMove.getRow() + 1) % gameRows) + gameRows) % gameRows)
				&& naechstesFeldNode.getPosY() == stoneToMove.getColumn()) {
			direction = Direction.DOWN;
		}
		if (naechstesFeldNode.getPosX() == ((((stoneToMove.getRow() - 1) % gameRows) + gameRows) % gameRows)
				&& naechstesFeldNode.getPosY() == stoneToMove.getColumn()) {
			direction = Direction.UP;
		}
*/
		Move move = new Move(stoneToMove.getRow(), stoneToMove.getColumn(), direction);
		return move;

	}

}
