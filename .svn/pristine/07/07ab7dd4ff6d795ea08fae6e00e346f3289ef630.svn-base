package softwareengineering.rotmilan;

import java.util.ArrayList;

import softwareengineering.*;

//not tested yet
public class BlockTorStrategy implements Strategy {

	//private BlockTorStrategy blocktorstrategy = null;
	int[][] gameBoard = null;
	int boardRows;
	int boardColumns;
	int ownColor;
	Node enemyGoalNode = null;
	ArrayList<Node> ownStones = null;
	StoneChoice stonechoice;
	AStarAlgorithm astar;

	public BlockTorStrategy(int[][] gameBoard, int boardRows, int boardColumns, int ownColor, Node enemyGoalNode, ArrayList<Node> ownStones) {
		super();
		this.gameBoard = gameBoard;
		this.boardRows = boardRows;
		this.boardColumns = boardColumns;
		this.ownColor = ownColor;
		this.enemyGoalNode = enemyGoalNode;
		this.ownStones = ownStones;
		stonechoice = new StoneChoice();
		astar = new AStarAlgorithm();
	}

	public BlockTorStrategy(int[][] gameBoard, int boardRows, int boardColumns, int ownColor, ArrayList<Node> enemyGoals, ArrayList<Node> ownStones) {
		super();
		stonechoice = new StoneChoice();
		astar = new AStarAlgorithm();
		this.gameBoard = gameBoard;
		this.boardRows = boardRows;
		this.boardColumns = boardColumns;
		this.ownColor = ownColor;
		this.ownStones = ownStones;
		enemyGoalNode = lookForNearestEnemyGoal(enemyGoals);
	}


	public void setGameBoard(int[][] board) {
		gameBoard = board;
	}
	
	public void setOwnStones(ArrayList<Node> ownstones) {
		this.ownStones = ownstones;
	}

	private Node lookForNearestEnemyGoal(ArrayList<Node> enemyGoals) {
		int pathsizebest =  0;
		int pathsizecurrent = 0;
		Node bestgoal = null;
		for(Node goal : enemyGoals) {
			ArrayList<Pair<Node,Node>> blockstones = stonechoice.chooseStonesToBlock(goal, ownStones, gameBoard, boardRows, boardColumns, ownColor);
			for(Pair<Node, Node> blockpair : blockstones) {
				Path shortestPath = astar.findShortestPath(blockpair.getLeft(), blockpair.getRight(), gameBoard, ownColor);
				//gesamte Pfadlange von allen 4 Nodes zusammenrechnen
				pathsizecurrent += shortestPath.getPathInNodes().size();
			}
			//pathsizebest ist 0 wenn erste Rotation
			if(pathsizecurrent < pathsizebest || pathsizebest == 0) {
				pathsizebest = pathsizecurrent;
				bestgoal = goal;
			}
		}
		return bestgoal;
	}

	@Override
	public int analyze() {
		int lohnt = 0;
		//prufen, ob es sich lohnt diese Strategie zu spielen
		if(ownStones.size() >= 8) {
			for(Pair<Node,Node> nodepair : stonechoice.chooseStonesToBlock(enemyGoalNode, ownStones, gameBoard, boardRows, boardColumns, ownColor)) {
				Node blocknode = nodepair.getLeft();
				Path shortestPath = astar.findShortestPath(blocknode, nodepair.getRight(), gameBoard, ownColor);
				if(shortestPath.getPathInNodes().size()-1 <= (float)(boardRows)/2 || shortestPath.getPathInNodes().size()-1 <= (float)(boardColumns)/2) {
					if(shortestPath != null) 
						lohnt++;
					else 
						lohnt--;
				}else {
					lohnt--;
				}

			}
		} else {
			lohnt--;
		}

		if(lohnt >= 2) {
			return 0;
		} else 
			return -1;
	}

	@Override
	public Move getMove() {
		Node nodeToMove;
		Path shortestPath;
		int pathlength, indexOfStoneToMove = 0;

		ArrayList<Pair<Node, Node>> blockStones = stonechoice.chooseStonesToBlock(enemyGoalNode, ownStones, gameBoard, boardRows, boardColumns, ownColor);

		for(Pair<Node,Node> blockStone : blockStones) {
			shortestPath = astar.findShortestPath(blockStone.getLeft(), blockStone.getRight(), gameBoard, ownColor);
			blockStone.getLeft().setPathToStart(shortestPath);
		}

		pathlength = blockStones.get(0).getLeft().getPathToStart().getPathInNodes().size();
		for(int i = 0; i<blockStones.size();i++) {
			int currentPathLength = blockStones.get(i).getLeft().getPathToStart().getPathInNodes().size();
			if(currentPathLength < pathlength && currentPathLength != 0) {
				pathlength = currentPathLength;
				indexOfStoneToMove = i;
			}
		}

		nodeToMove = blockStones.get(indexOfStoneToMove).getLeft();
		//Queue sortieren
		//nodeToMove.getPathToStart().sortNodesToQueue(nodeToMove.getRowNumber(), nodeToMove.getColumnNumber() );
		//Direction bekommen
		Direction direction = nodeToMove.getNextDirection();

		return new Move(nodeToMove.getRowPosition(), nodeToMove.getColumnPosition(), direction);

	}

}
