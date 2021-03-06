package softwareengineering.rotmilan;

import softwareengineering.*;
import java.util.ArrayList;

public class DefaultStrategy implements Strategy {
	
	int[][] gameBoard = null;
	int boardRows;
	int boardColumns;
	int ownColor;
	Node goalNode = null;
	ArrayList<Node> ownStones = null;
	ArrayList<GameObject> enemyStones = null;
	
	
	
	public DefaultStrategy(int[][] board, int gameRows, int gameColumns, Node goalNode, ArrayList<Node> ownStonesWithoutGoal, ArrayList<GameObject> enemyStones, int ownColor ) {
		
		this.gameBoard = board;
		this.boardRows = gameRows;
		this.boardColumns = gameColumns;
		this.ownColor = ownColor;
		this.goalNode = goalNode;
		this.ownStones = ownStonesWithoutGoal;
		this.enemyStones = enemyStones;
		
	}
	
	public int analyze() {
		
		return 0;
	}
	
	public Move getMove() {
		
		AStarAlgorithm astar = new AStarAlgorithm();
		StoneChoice stonechoice = new StoneChoice();
		//Node goalNode = new Node(goalNode.getRowNumber(), goalNode.getColumnNumber(), false, null);	//goal
		Node nodeToMove = null;	//welcher Stein soll whin bewegt werden
		Node endNode = null;	//wohin soll der Stein bewegt werden
		Path shortestPath = null;	//pfad zu berechnen
		Direction direction = null;	//direction

		//ziel bestimmen
		endNode = goalNode;
		
		Node altEndNode = astar.createNewNode(endNode.getRowPosition(), endNode.getColumnPosition(), gameBoard, ownColor, endNode.getDirection(), boardColumns);
		altEndNode.setfCost(endNode.getfCost());
		altEndNode.setgCost(endNode.getgCost());
		altEndNode.sethCost(endNode.gethCost());
		altEndNode.setBlocked(endNode.isBlocked());
		altEndNode.setParent(endNode.getParent());
		altEndNode.setPathToStart(endNode.getPathToStart());

		
		//Stein bestimmen
		nodeToMove = stonechoice.chooseStoneToMove(endNode, ownStones, gameBoard, boardRows, boardColumns, ownColor);
		
		//2: Pfad von Start zu Ziel bestimmen
		shortestPath = astar.findShortestPath(nodeToMove, altEndNode, gameBoard, ownColor);
		//3. Path der Node zuweisen
		nodeToMove.setPathToStart(shortestPath);
		//5. Direction bekommen
		direction = nodeToMove.getNextDirection();

		Move move = null;

		//Abfrage ob direction 0 ist
		if(direction == null) {
			return move;
		}

		move = new Move(nodeToMove.getRowPosition(), nodeToMove.getColumnPosition(), direction);
		return move;
		
	}
	
	
	public Node chooseGoal(int goalRow, int goalColumn) {
		
		return new Node(goalRow, goalColumn, false, null);
	}
	
//	public Node chooseStoneToMove(Node endNode, ArrayList<Node> listWithoutGoals, int[][] board, int gameRowsNumber, int gameColumnsNumber, int ownColor) {
//		
//		AStarAlgorithm astar = new AStarAlgorithm();
//		
//		int tempH = 100000;
//		Node tempNode = null;
//		Node tempPassNode = null;
//		
//		for(Node node : listWithoutGoals) {
//			node.sethCost(astar.calculateHeuristic(node, endNode, gameRowsNumber, gameColumnsNumber));
//			if(node.gethCost() < tempH && ( ! astar.findAllNeighbours(node, board, gameRowsNumber, gameColumnsNumber, ownColor, 1).isEmpty()) ) {
//				tempH = node.gethCost();
//				tempNode = node;
//			}
//			else if(node.gethCost() < tempH){
//				tempPassNode = node;
//			}
//		}
//		//kein freier Stein gefunden
//		if(tempNode == null) {
//			return tempPassNode;
//		}
//		else {
//			return tempNode;
//		} 
//		
//	}
	
}
