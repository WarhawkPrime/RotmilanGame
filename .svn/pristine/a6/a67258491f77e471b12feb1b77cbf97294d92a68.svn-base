package rotmilan;

import java.util.ArrayList;

import softwareengineering.Direction;

public class PathFinder {

	private AStarAlgorithm astar;
	private Node Node_stoneToMove, node_goal;
	
	public PathFinder() {
		astar = new AStarAlgorithm();
	}
	
	public Node giveNextNodeInPath(GameObject stoneToMove, GameObject ourGoal, int[][] board_int,
			int gameRows, int gameColumns, ArrayList<GameObject> ourStones, int owncolor) {
		
		Node node_stoneToMove = new Node(stoneToMove.getRow(), stoneToMove.getColumn(), true);
		
		Node node_goal = new Node(ourGoal.getRow(), ourGoal.getColumn(), false);
		
		Path path = astar.findShortestPath(node_stoneToMove, node_goal, board_int, gameRows, gameColumns, owncolor);
		if(path == null) {
			ourStones.remove(stoneToMove);
			return null;
		}
		
		stoneToMove.setGameObjectPath(path);
		
		ArrayList<Node> pathinnodes = path.getCompletePathInNodes();
		//Feld, dass als naechstes betreten werden soll
		Node naechstesFeldNode = path.getNextNode(stoneToMove.getRow(), stoneToMove.getColumn());
		return naechstesFeldNode;
	}
	
	
	
	
}
