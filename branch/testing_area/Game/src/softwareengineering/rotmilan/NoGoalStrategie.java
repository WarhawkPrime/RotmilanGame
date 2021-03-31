package softwareengineering.rotmilan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import softwareengineering.rotmilan.*;
import softwareengineering.Direction;
import softwareengineering.Game;
import softwareengineering.Move;

import java.util.Random;


public class NoGoalStrategie implements Strategy {

	Game game = null;

	int[][] gameBoard = null;
	int boardRows;
	int boardColumns;
	int ownColor;
	//Node goalNode = null;
	ArrayList<Node> ownStones = null;
	ArrayList<Node> ownGoals = null;
	ArrayList<GameObject> enemyStones = null;
	AStarAlgorithm astar = new AStarAlgorithm();
	ArrayList<Node> enemyGoals = new ArrayList<>();

	Random random;

	int[] stratI = null;
	int maxBoards = 10;
	ArrayList<int[][]> boards = new ArrayList<>();

	NoGoalStrategie(int[][] board, int gameRows, int gameColumns, ArrayList<Node> ownGoals , ArrayList<Node> ownStonesWithoutGoal, ArrayList<GameObject> enemyStones, int ownColor, Game game, ArrayList<int[][]> boards) {
		this.gameBoard = board;
		this.boardRows = gameRows;
		this.boardColumns = gameColumns;
		this.ownColor = ownColor;
		//this.goalNode = goalNode;
		this.ownStones = ownStonesWithoutGoal;
		this.ownGoals = ownGoals;
		this.enemyStones = enemyStones;

		this.game = game;

		random = new Random();
	}


	@Override
	public int analyze() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public Move getMove() {

		
		int numRows = game.getNumRows();
		int numColumns = game.getNumColumns();

		for (int i=0; i<10000; i++) {

			int row = random.nextInt(numRows);
			int column = random.nextInt(numColumns);

			int value = game.getField(row, column);			
			if (value != ownColor) continue;

			Direction direction = Direction.values()[random.nextInt(4)];
			Move move = new Move(row, column, direction);

			if (direction == Direction.UP && (game.getField((row+numRows-1)%numRows, column) == 0 || game.getField((row+numRows-1)%numRows, column) == -ownColor)) return move;
			if (direction == Direction.DOWN && (game.getField((row+1)%numRows, column) == 0 || game.getField((row+1)%numRows, column) == -ownColor)) return move;
			if (direction == Direction.LEFT && (game.getField(row, (column+numColumns-1)%numColumns) == 0 || game.getField(row, (column+numColumns-1)%numColumns) == -ownColor)) return move;
			if (direction == Direction.RIGHT && (game.getField(row, (column+1)%numColumns) == 0 || game.getField(row, (column+1)%numColumns) == -ownColor)) return move;
		}

		return null;
	}
		

		/*
		AStarAlgorithm astar = new AStarAlgorithm();
		//Node goalNode = new Node(goalNode.getRowNumber(), goalNode.getColumnNumber(), false, null);    //goal
		Node nodeToMove = null;    //welcher Stein soll whin bewegt werden
		Node endNode = null;    //wohin soll der Stein bewegt werden
		
		Direction direction = null;    //direction
		
		boolean fieldFound = false;
		boolean prepared = false;
		int middleNodeID = -1;
		Node middleNode = null;
		//ziel bestimmen
		
		
		endNode = zeroOwnGoals(ownStones, fieldFound, prepared, middleNodeID, middleNode);


		//Stein bestimmen
		if(fieldFound == false) {
			nodeToMove = chooseStoneToMove(endNode, ownStones, gameBoard, boardRows, boardColumns, ownColor, astar);
		}
		else {
			//die node mit der gemerkten id ist danach immer der stein der bewegt wird
		}

		nodeToMove.setPathToStart(astar.findShortestPath(nodeToMove, endNode, gameBoard, ownColor));


		direction = nodeToMove.getNextDirection();
		Move move = null;
		if(direction == direction.UP) {
			int row = nodeToMove.getRowPosition() - 1;
			int column = nodeToMove.getColumnPosition();
			if(row == -1) {
				row = boardRows - 1;
			}
			if(gameBoard[row][column] != 0 && gameBoard[row][column] != -ownColor) {
				direction = null;
			}
		}
		else if(direction == direction.DOWN) {
			int row = nodeToMove.getRowPosition() +1;
			int column = nodeToMove.getColumnPosition();
			if(row > boardRows -1) {
				row = 0;
			}
			if(gameBoard[row][column] != 0 && gameBoard[row][column] != -ownColor) {
				direction = null;
			}
		}
		else if(direction == direction.LEFT) {
			int row = nodeToMove.getRowPosition();
			int column = nodeToMove.getColumnPosition() -1;
			if(column == -1) {
				column = boardColumns -1;
			}
			if(gameBoard[row][column] != 0 && gameBoard[row][column] != -ownColor) {
				direction = null;
			}
		}
		else if(direction == direction.RIGHT) {
			int row = nodeToMove.getRowPosition();
			int column = nodeToMove.getColumnPosition() +1;
			if(column > boardColumns -1) {
				column = 0;
			}
			if(gameBoard[row][column] != 0 && gameBoard[row][column] != -ownColor) {
				direction = null;
			}
		}
		//Abfrage ob direction 0 ist
		if(direction == null) {
			return move;
		}

		move = new Move(nodeToMove.getRowPosition(), nodeToMove.getColumnPosition(), direction);
		return move;
		
	}





	//was soll gemacht werden wenn es keine eigenen Knoten gibt?
	//Ziel: bis zur unendlichkeit überleben, egal was passiert.
	//kleinste sicherste Einheit: Wenn die hergestellt werden kann, dann geht es unendlich weiter
	//1    1 1
	//1 1 1
	//1 0 1
	//0 1 0
	//ein 9x9 Feld mit eigenen Steinen erstellen
	//wie? nach einem Stein mit 8 Nachbarn suchen: Diagonale!
	//wenn keiner gefunden wurde, den Stein mit den meisten suchen und den Rest der Steine in die fehlenden Positionen bringen
	//wenn ein 9x9 Feld entsteht, bewege einen Stein der Platz hat nach draußen
	//nun bewege nurnoch den Mittleren Stein dahin wo er Platz hat
	//Diese Methode wählt Ziele aus
	// nach dem 3x3 Feld darf nur einmal gesucht werden. Danach darf nicht mehr danach gesucht werden!!!

	public Node zeroOwnGoals(ArrayList<Node> ownStones, boolean fieldFound, boolean prepared, int middleid, Node middleNode) {
		int nodeID = 0;
		int maxNeighbours = 0;
		Node goalNode = null;

		if(fieldFound==false && middleid == -1) {
			for(Node n: ownStones) {    
				if(checkForAllEightNeighbours(n) == -1) {        //Hat nicht alle 8 Nachbarn, muss sich erst dahin bewegt werden
					fieldFound = true;
					middleid = n.getNodeID();
				}
			}
		}


		//wenn es immer noch nicht gefunden wurde, dann suche den besten Kandidtaten und wähle als Ziel eines der freien Felder aus
		if(fieldFound==false && middleid == -1) {

			for(Node n: ownStones) {    //suche mir die beste Node heraus, merke mir die id
				int size = findAllNeighbours(n).size();
				if(size > maxNeighbours) {
					nodeID = n.getNodeID();
					maxNeighbours = size;
				}
			}
			middleid = nodeID;
			middleNode = findNodePerID(middleid);
		}

		//den mit den meisten Nachbarn in ein 3x3 Feld ändern
		if(middleid != -1) {
			//find a empty space around it
			int number = checkForAllEightNeighbours(findNodePerID(middleid));
			if(number == -1) {     //keine freien Plätze mehr, 3x3 Feld gefunden
				fieldFound = true;
			}
			else {
				//switch case welcher NachbarNode jetzt ein Ziel ist

				int rowPos = 0;
				int colPos = 0;

				switch(number) {
				case 1:	//upper Neighbour
					rowPos = ((middleNode.getRowPosition() -1));
					if(rowPos == -1)
						rowPos = boardRows-1;
					goalNode = astar.createNewNode(rowPos, colPos, gameBoard, ownColor, null, boardColumns);
					return goalNode;
				case 2: //right Neighbour
					colPos = ((middleNode.getColumnPosition()  + 1));
					rowPos = middleNode.getRowPosition(); 
					if(colPos > boardColumns-1)
						colPos = 0;
					goalNode = astar.createNewNode(rowPos, colPos, gameBoard, ownColor, null, boardColumns);
					return goalNode;
				case 3: //lower Neighbour
					colPos = middleNode.getColumnPosition();
					rowPos = ((middleNode.getRowPosition() + 1));
					if(rowPos > boardRows-1)
						rowPos = 0;
					goalNode = astar.createNewNode(rowPos, colPos, gameBoard, ownColor, null, boardColumns);
					return goalNode;
				case 4: //left Neighbour
					colPos = ((middleNode.getColumnPosition()  -1));
					rowPos = middleNode.getRowPosition(); 
					if(colPos == -1)
						colPos = boardColumns-1;
					goalNode = astar.createNewNode(rowPos, colPos, gameBoard, ownColor, null, boardColumns);
					return goalNode;
				case 5: //Upper Right neighbour
					colPos = ((middleNode.getColumnPosition()  + 1));
					rowPos = ((middleNode.getRowPosition() -1));
					if(colPos > boardColumns-1)
						colPos = 0;
					if(rowPos == -1)
						rowPos = boardRows-1;
					goalNode = astar.createNewNode(rowPos, colPos, gameBoard, ownColor, null, boardColumns);
					return goalNode;
				case 6: //Lower Right neighbour
					colPos = ((middleNode.getColumnPosition()  + 1));
					rowPos = ((middleNode.getRowPosition() + 1));
					if(colPos > boardColumns-1)
						colPos = 0;
					if(rowPos > boardRows-1)
						rowPos = 0;
					goalNode = astar.createNewNode(rowPos, colPos, gameBoard, ownColor, null, boardColumns);
					return goalNode;
				case 7: //Lower Left neighbour
					colPos = ((middleNode.getColumnPosition()  -1));
					rowPos = ((middleNode.getRowPosition() + 1));
					if(colPos == -1)
						colPos = boardColumns-1;
					if(rowPos > boardRows-1)
						rowPos = 0;
					goalNode = astar.createNewNode(rowPos, colPos, gameBoard, ownColor, null, boardColumns);
					return goalNode;
				case 8: //Upper Right neighbour
					colPos = ((middleNode.getColumnPosition()  -1));
					rowPos = ((middleNode.getRowPosition() -1));
					if(colPos == -1)
						colPos = boardColumns-1;
					if(rowPos == -1)
						rowPos = boardRows-1;
					goalNode = astar.createNewNode(rowPos, colPos, gameBoard, ownColor, null, boardColumns);
					return goalNode;
				}
			}
		}



		//3x3 Feld wurde gefunden, jetzt suche einmalig nach einem freien Feld von einen der Nachbarn. Wenn ein Nachbar ein feies 
		//Feld hat, bewege ihn dahin
		//Dies auch nur einmalig machen
		if(fieldFound == true && prepared == false) {
			//nach einen freien PLatz schauen, horizontal und vertikal

		}

		//hier wird der mittlere Stein ausgewählt, dieser soll hin und her springen. ID MERKEN!!!!!
		if(prepared == true) {
			//freier Platz neben dem Knoten mit der ID ist nun das Ziel
		}

		return null;
	}



	public int checkForAllEightNeighbours(Node node) {
		int columnNumber = 0;
		int rowNumber = 0;
		//========== Upper Neighbour ==========
		columnNumber = node.getColumnPosition();
		rowNumber = ((node.getRowPosition() -1));
		if(rowNumber == -1)
			rowNumber = boardRows-1;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			return 1;
		}
		//========== Right neighbour ==========
		columnNumber = ((node.getColumnPosition()  + 1));
		rowNumber = node.getRowPosition(); 
		if(columnNumber > boardColumns-1)
			columnNumber = 0;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			return 2;
		}
		//========== Lower neighbour ==========
		columnNumber = node.getColumnPosition();
		rowNumber = ((node.getRowPosition() + 1));
		if(rowNumber > boardRows-1)
			rowNumber = 0;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			return 3;
		}
		//========== Left neighbour ==========
		columnNumber = ((node.getColumnPosition()  -1));
		rowNumber = node.getRowPosition(); 
		if(columnNumber == -1)
			columnNumber = boardColumns-1;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			return 4;
		}
		//========== Upper Right neighbour ==========
		columnNumber = ((node.getColumnPosition()  + 1));
		rowNumber = ((node.getRowPosition() -1));
		if(columnNumber > boardColumns-1)
			columnNumber = 0;
		if(rowNumber == -1)
			rowNumber = boardRows-1;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			return 5;
		}
		//========== Lower Right neighbour ==========
		columnNumber = ((node.getColumnPosition()  + 1));
		rowNumber = ((node.getRowPosition() + 1));
		if(columnNumber > boardColumns-1)
			columnNumber = 0;
		if(rowNumber > boardRows-1)
			rowNumber = 0;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			return 6;
		}
		//========== Lower Left neighbour ==========
		columnNumber = ((node.getColumnPosition()  -1));
		rowNumber = ((node.getRowPosition() + 1));
		if(columnNumber == -1)
			columnNumber = boardColumns-1;
		if(rowNumber > boardRows-1)
			rowNumber = 0;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			return 7;
		}
		//========== Upper Left neighbour ==========
		columnNumber = ((node.getColumnPosition()  -1));
		rowNumber = ((node.getRowPosition() -1));
		if(columnNumber == -1)
			columnNumber = boardColumns-1;
		if(rowNumber == -1)
			rowNumber = boardRows-1;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			return 8;
		}
		return -1;
	}


	public ArrayList<Node> findAllNeighbours(Node node ) {

		int columnNumber = 0;
		int rowNumber = 0;
		int id = 0;
		ArrayList<Node> neighbours = new ArrayList<Node>();
		//========== Upper Neighbour ==========
		columnNumber = node.getColumnPosition();
		rowNumber = ((node.getRowPosition() -1));
		if(rowNumber == -1)
			rowNumber = boardRows-1;
		if(isOwnStone(rowNumber, columnNumber) == true) {
			id = calculateNodeID(rowNumber, columnNumber);
			neighbours.add(findNodePerID(id));
		}
		//========== Right neighbour ==========
		columnNumber = ((node.getColumnPosition()  + 1));
		rowNumber = node.getRowPosition(); 
		if(columnNumber > boardColumns-1)
			columnNumber = 0;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			id = calculateNodeID(rowNumber, columnNumber);
			neighbours.add(findNodePerID(id));
		}
		//========== Lower neighbour ==========
		columnNumber = node.getColumnPosition();
		rowNumber = ((node.getRowPosition() + 1));
		if(rowNumber > boardRows-1)
			rowNumber = 0;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			id = calculateNodeID(rowNumber, columnNumber);
			neighbours.add(findNodePerID(id));
		}
		//========== Left neighbour ==========
		columnNumber = ((node.getColumnPosition()  -1));
		rowNumber = node.getRowPosition(); 
		if(columnNumber == -1)
			columnNumber = boardColumns-1;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			id = calculateNodeID(rowNumber, columnNumber);
			neighbours.add(findNodePerID(id));
		}
		//========== Upper Right neighbour ==========
		columnNumber = ((node.getColumnPosition()  + 1));
		rowNumber = ((node.getRowPosition() -1));
		if(columnNumber > boardColumns-1)
			columnNumber = 0;
		if(rowNumber == -1)
			rowNumber = boardRows-1;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			id = calculateNodeID(rowNumber, columnNumber);
			neighbours.add(findNodePerID(id));
		}
		//========== Lower Right neighbour ==========
		columnNumber = ((node.getColumnPosition()  + 1));
		rowNumber = ((node.getRowPosition() + 1));
		if(columnNumber > boardColumns-1)
			columnNumber = 0;
		if(rowNumber > boardRows-1)
			rowNumber = 0;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			id = calculateNodeID(rowNumber, columnNumber);
			neighbours.add(findNodePerID(id));
		}
		//========== Lower Left neighbour ==========
		columnNumber = ((node.getColumnPosition()  -1));
		rowNumber = ((node.getRowPosition() + 1));
		if(columnNumber == -1)
			columnNumber = boardColumns-1;
		if(rowNumber > boardRows-1)
			rowNumber = 0;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			id = calculateNodeID(rowNumber, columnNumber);
			neighbours.add(findNodePerID(id));
		}
		//========== Upper Left neighbour ==========
		columnNumber = ((node.getColumnPosition()  -1));
		rowNumber = ((node.getRowPosition() -1));
		if(columnNumber == -1)
			columnNumber = boardColumns-1;
		if(rowNumber == -1)
			rowNumber = boardRows-1;
		if(isOwnStone(rowNumber, columnNumber) == false) {
			id = calculateNodeID(rowNumber, columnNumber);
			neighbours.add(findNodePerID(id));
		}
		return neighbours;
	}


	public boolean isOwnStone(int rowNumber, int colNumber) {
		if(gameBoard[rowNumber][colNumber] == ownColor) {
			return true;
		}
		else {
			return false;
		}
	}



	public boolean isFree(int rowNumber, int colNumber) {
		if(gameBoard[rowNumber][colNumber] == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public int calculateNodeID(int rowN, int colN) {
		int id = colN + 1 + (rowN * boardColumns);
		return id;
	}

	public Node findNodePerID(int id) {
		for(Node n: ownStones) {
			if(n.getNodeID() == id) {
				return n;
			}
		}
		return null;
	}

	/*
	public Node findNodePerPosition(int rowPos, int colPos) {
		for(Node n: ownStones) {
			if( (n.getRowPosition() == rowPos) && (n.getColumnPosition() == colPos) ) {

			}
		}
		return null;
	}
	 */


	/*
    public boolean testForOptimalNextNode(Node currentNode) {
        Node nextPlannedNode = currentNode.getPathToStart().getNextNode(currentNode.getNodeID());
        if(nextPlannedNode == null) {
            return false;
        }
        nextPlannedNode.setPathToStart(astar.findShortestPath(nextPlannedNode, goalNode, gameBoard, ownColor));
        if(currentNode.getPathToStart().getPathInNodes().size() > nextPlannedNode.getPathToStart().getPathInNodes().size() ) {
            return true;
        }
        return false;
    }
	 */
	//==========================================================================================
		
	/*
	public Node chooseStoneToMove(Node endNode, ArrayList<Node> listWithoutGoals, int[][] board, int gameRowsNumber, int gameColumnsNumber, int ownColor, AStarAlgorithm astar) {


		//Prioritï¿½ten setzten:
		//1: Stein hat freie Bahn und den kï¿½rzesten Weg zum Ziel
		//2: Stein hat zwar keine freie Bahn aber kann den nï¿½chsten Sinnvollen Schritt zum Ziel machen
		//3: Stein hat keine freie Bahn und kann auch keinen sinnvollen Schritt machen und ist weiter vom Ziel Weg
		//4: Stein kann sich nicht bewegen, dann ist es egal

		Comparator<Node> comparator = new NodePathComparator();
		Comparator<Node> turnedComparator = new NodeTurnedPathSizeComparator();

		PriorityQueue<Node> stoneQueueToGoal = new PriorityQueue<Node>(comparator);
		PriorityQueue<Node> stoneQueueBlockedGoal = new PriorityQueue<Node>(turnedComparator);

		//berechnen von h fï¿½r jeden eigenen Stein zum Ziel und sortierung in die Queue
		for(Node stone : ownStones) {
			stone.setPathToStart(astar.findShortestPath(stone, endNode, board, ownColor));

			if(stone.getPathToStart().getPathInNodes().get(0).getRowPosition() == endNode.getRowPosition() && 
					stone.getPathToStart().getPathInNodes().get(0).getColumnPosition() == endNode.getColumnPosition() ) 
			{

				stoneQueueToGoal.add(stone);

			}
			else {
				stoneQueueBlockedGoal.add(stone);
			}
		}

		Node tempNode = null;
		if(!stoneQueueToGoal.isEmpty()) {
			tempNode = stoneQueueToGoal.poll();
		}
		else {
			tempNode = stoneQueueBlockedGoal.poll();
		}

		return tempNode;
	}
	*/
	



}
