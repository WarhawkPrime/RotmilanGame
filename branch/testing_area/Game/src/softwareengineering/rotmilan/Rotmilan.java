package softwareengineering.rotmilan;

import java.util.ArrayList;

import softwareengineering.Direction;
import softwareengineering.Game;
import softwareengineering.Move;
import softwareengineering.Player;

//Enumeration um GameObjects zu unterscheiden
enum Objecttype {
	STONE, GOAL;
}

public class Rotmilan implements Player{

	int color;
	int playernumber;
	//int goal_row, goal_column;
	int gameRows, gameColumns;
	int movesSkipped;
	int[][] board_int;
	String name;
	Game game;
	//GameObject ourGoal;
	ArrayList<GameObject> ourStones;
	ArrayList<GameObject> ourGoals;
	ArrayList<GameObject> enemyStones;
	ArrayList<Node> enemyGoals;
	AStarAlgorithm astar = null;
	ArrayList<int[][]> boards = null;
	Strategy strategy = null;

	public Rotmilan(int color, String name, Game game) {

		this.color = color;
		this.name = name;
		this.game = game;
		gameRows = game.getNumRows();
		gameColumns = game.getNumColumns();
		board_int = new int[gameRows][gameColumns];
		movesSkipped = 0;
		ourStones = new ArrayList<GameObject>();
		enemyStones = new ArrayList<GameObject>();
		enemyGoals = new ArrayList<Node>();

		astar = new AStarAlgorithm();
		
		boards = new ArrayList<>();

		//boardIntEinlesen();
		//parseBoardToObjectBoard();
	}

	public String toString() {
		return name;
	}

	public int getColor() {
		return color;
	}


	public void boardIntEinlesen() {
		for (int row = 0; row < gameRows; row++) {
			for (int column = 0; column < gameColumns; column++) {
				board_int[row][column] = game.getField(row, column);
				
				//10 Tore max Einspeichern
				if(boards.size() < 10) {
					boards.add(board_int);
				}
				else {
					boards.remove(0);
					boards.add(board_int);
				}
				
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
					//ourGoal = new GameObject(row, column, color, Objecttype.GOAL);
					//goal_row = ourGoal.getRow();
					//goal_column = ourGoal.getColumn();
					//ourStones.add(ourGoal);
					ourStones.add(new GameObject(row, column, color, Objecttype.GOAL)); 	//Tor wird hinzugef?gt
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
					enemyGoals.add(new Node(row, column, false));
				}
			}
		}
	}


								
	public Move nextMove() {

		//DefaultStrategy defS = new DefaultStrategy();
		StoneChoice stoneChoice = new StoneChoice();
		Pathfinder pathf = new Pathfinder();

		int skippedTurns = 0;

		ArrayList<Node> NodelistWithoutGoals = new ArrayList<>();
		ArrayList<GameObject> listWithoutGoal = new ArrayList<>();
		ArrayList<GameObject> GoalList = new ArrayList<>();
		ArrayList<Node> GoalNodeList = new ArrayList<>();

		this.boardIntEinlesen();
		this.parseBoardToObjectBoard();

		for(int i = 0; i < ourStones.size(); i++) {

			//if(! (ourStones.get(i).getColumn() == ourGoal.getColumn() && ourStones.get(i).getRow() == ourGoal.getRow())) {
				//listWithoutGoal.add(ourStones.get(i));
			//}
			if(! (ourStones.get(i).getType() == Objecttype.GOAL )) {
				listWithoutGoal.add(ourStones.get(i));
			}
			else {
				GoalList.add(ourStones.get(i));
			}
		}
		
		for(int i = 0; i < listWithoutGoal.size(); i++) {
			Node n = astar.createNewNode(listWithoutGoal.get(i).getRow(), listWithoutGoal.get(i).getColumn(), board_int, this.getColor(), null, this.gameColumns);
			NodelistWithoutGoals.add(n);
		}
		for(int i = 0; i < GoalList.size(); i++) {
			Node g = astar.createNewNode(GoalList.get(i).getRow(), GoalList.get(i).getColumn(), board_int, this.getColor(), null, this.gameColumns);
			GoalNodeList.add(g);
		}
		
		Strategy defDW = new DWStrategy(board_int, gameRows, gameColumns, GoalNodeList, NodelistWithoutGoals, enemyStones, color, game, boards);
		Strategy noGoalStrategie = new NoGoalStrategie(board_int, gameRows, gameColumns, GoalNodeList, NodelistWithoutGoals, enemyStones, color, game, boards);
		
		int situation = defDW.analyze();
		//int situation = 0;
		
		switch(situation) {
			case 0: return defDW.getMove();						//Default case, Steine verhalten sich normal
			//case 1: return ; 									// wenn die Tore noch nicht gesch?tzt sind
			//case 2: return 				  					// eigene Tore sind gesch?tzt, eigene Steine k?nnen zu den Toren laufen
			//case 3: return 				  					//mind ein eigner Stein steht vor dem gesch?tzten Tor, austausch
			//case 4: return				 					//wenn die gegnerischen Tore angegriffen werden k?nnen
			case 5: return noGoalStrategie.getMove();			//keine eigenen Tore
			default : return defDW.getMove();
		}
	}



}
