package softwareengineering.rotmilan;

import java.util.ArrayList;

public class Pathfinder {
	
	public Node besteNodeAuswahl(ArrayList<Node> ownNodes, Node endNode, int gameRows, int gameColumns) {
		
		AStarAlgorithm astar = new AStarAlgorithm();
		
		int distanceToTarget = -1;
		int tempDistance;
		int currentMinAbstandStone = 0;
		int stoneRowNumber;
		int stoneColumnNumber;
		Node nodeToMove = null;
		
		
		if(ownNodes.size() == 0) {
			return null;
		}
		
		for(Node node : ownNodes) {
			nodeToMove = node;
			stoneRowNumber = node.getRowPosition();
			stoneColumnNumber = node.getColumnPosition();
			
			if(node.getColumnPosition() == endNode.getColumnPosition() && node.getRowPosition() == endNode.getRowPosition()  ) {
				continue;
			}
			tempDistance = astar.calculateHeuristic(node, endNode, gameRows, gameColumns);
			
			if(tempDistance < distanceToTarget || distanceToTarget == -1) {
				distanceToTarget = tempDistance;
				nodeToMove = node;
			}
		}
		return nodeToMove;
	}
	
	
	
	
}