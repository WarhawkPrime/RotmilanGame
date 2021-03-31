package softwareengineering.rotmilan;

import java.util.ArrayList;

//Klasse in der berechnet wird welcher Stein gezogen wird.
public class StoneChoice {
	
AStarAlgorithm astar = new AStarAlgorithm();
	
	public Node chooseStoneToMove(Node endNode, ArrayList<Node> listWithoutGoals, int[][] board, int gameRowsNumber, int gameColumnsNumber, int ownColor) {
		
		int tempH = 100000;
		Node tempNode = null;
		Node tempPassNode = null;
		
		for(Node node : listWithoutGoals) {
			node.sethCost(astar.calculateHeuristic(node, endNode, gameRowsNumber, gameColumnsNumber));
			if(node.gethCost() < tempH && ( ! astar.findAllNeighbours(node, board, gameRowsNumber, gameColumnsNumber, ownColor, 1).isEmpty()) ) {
				tempH = node.gethCost();
				tempNode = node;
			}
			else if(node.gethCost() < tempH){
				tempPassNode = node;
			}
		}
		//kein freier Stein gefunden
		if(tempNode == null) {
			Node altTempPassNode = astar.createNewNode(tempPassNode.getRowPosition(), tempPassNode.getColumnPosition(), board, ownColor, tempPassNode.getDirection(), gameColumnsNumber);
			altTempPassNode.setfCost(tempPassNode.getfCost());
			altTempPassNode.setgCost(tempPassNode.getgCost());
			altTempPassNode.sethCost(tempPassNode.gethCost());
			altTempPassNode.setBlocked(tempPassNode.isBlocked());
			altTempPassNode.setParent(tempPassNode.getParent());
			altTempPassNode.setPathToStart(tempPassNode.getPathToStart());

			return altTempPassNode;
		}
		else {
			Node altTempNode = astar.createNewNode(tempNode.getRowPosition(), tempNode.getColumnPosition(), board, ownColor, tempNode.getDirection(), gameColumnsNumber);
			altTempNode.setfCost(tempNode.getfCost());
			altTempNode.setgCost(tempNode.getgCost());
			altTempNode.sethCost(tempNode.gethCost());
			altTempNode.setBlocked(tempNode.isBlocked());
			altTempNode.setParent(tempNode.getParent());
			altTempNode.setPathToStart(tempNode.getPathToStart());
			
			return altTempNode;
		}
	}
	
	/**returns a List of Nodes which are used to block the enemy goal.
	* Aufbau der ArrayList = (Pair<bestStone for Top of Goal,TopGoalNode>,
	*							Pair<bestStone for Bottom of Goal,BottomGoalNode>,
	*							Pair<bestStone for left of Goal, LeftGoalNode>,
	*							Pair<bestStone for right of goal, RightGoalNode>)
	 * 
	 * @param enemyGoalNode		Node für enemyGoal
	 * @param listWithoutGoals	Node Liste mit unseren Steinen
	 * @param board				Spielbrett in Int
	 * @param gameRowsNumber	Anzahl Spielzeilen
	 * @param gameColumnsNumber Anzahl Spielspalten
	 * @param ownColor			Nummer der eigenen Farbe
	 * @return ArrayList(Pair(Node,Node))
	 **/
	//not tested yet
	public ArrayList<Pair<Node,Node>> chooseStonesToBlock(Node enemyGoalNode, ArrayList<Node> listWithoutGoals, int[][] board, int gameRowsNumber, int gameColumnsNumber, int ownColor) {
		
		ArrayList<Pair<Node,Node>> nodesForBlock = new ArrayList<Pair<Node,Node>>();
		ArrayList<Node> copyListWithoutGoals = (ArrayList<Node>) listWithoutGoals.clone();
		
		//Torus Eigenschaft = (n % SIZE + SIZE) % SIZE
		Node topOfGoal = new Node(((enemyGoalNode.getRowPosition()-1) % gameRowsNumber + gameColumnsNumber)%gameColumnsNumber, enemyGoalNode.getColumnPosition(), false);
		Node bottomOfGoal = new Node(((enemyGoalNode.getRowPosition()+1) % gameRowsNumber + gameRowsNumber)%gameRowsNumber, enemyGoalNode.getColumnPosition(), false);
		Node leftOfGoal = new Node(enemyGoalNode.getRowPosition(), ((enemyGoalNode.getColumnPosition()-1) % gameColumnsNumber + gameColumnsNumber)%gameColumnsNumber, false);
		Node rightOfGoal = new Node(enemyGoalNode.getRowPosition(), ((enemyGoalNode.getColumnPosition()+1) % gameColumnsNumber + gameColumnsNumber)%gameColumnsNumber, false);
		
		//bestStone to move to top of Goal
		Node bestfortop = this.chooseStoneToMove(topOfGoal, copyListWithoutGoals, board, gameRowsNumber, gameColumnsNumber, ownColor);
		nodesForBlock.add(new Pair<Node, Node>(bestfortop, topOfGoal));
		//Ende Top
		
		//bestStone to move to bottom of Goal
		Node bestforbottom = this.chooseStoneToMove(bottomOfGoal, copyListWithoutGoals, board, gameRowsNumber, gameColumnsNumber, ownColor);
		while(bestforbottom == bestfortop) {
			copyListWithoutGoals.remove(bestforbottom);
			bestforbottom = chooseStoneToMove(bottomOfGoal, copyListWithoutGoals, board, gameRowsNumber, gameColumnsNumber, ownColor);
		}
		nodesForBlock.add(new Pair<Node, Node>(bestforbottom, bottomOfGoal));
		//Ende bottom
		
		//bestStone to move to left of Goal
		Node bestforleft = this.chooseStoneToMove(leftOfGoal, copyListWithoutGoals, board, gameRowsNumber, gameColumnsNumber, ownColor);
		while(bestforleft == bestfortop || bestforleft == bestforbottom) {
			copyListWithoutGoals.remove(bestforleft);
			bestforleft = chooseStoneToMove(leftOfGoal, copyListWithoutGoals, board, gameRowsNumber, gameColumnsNumber, ownColor);
		}
		nodesForBlock.add(new Pair<Node, Node>(bestforleft, leftOfGoal));
		//Ende left
		
		//bestStone to move to right of Goal
		Node bestforright = this.chooseStoneToMove(rightOfGoal, copyListWithoutGoals, board, gameRowsNumber, gameColumnsNumber, ownColor);
		while(bestforright == bestfortop || bestforright == bestforbottom || bestforright == bestforleft) {
			copyListWithoutGoals.remove(bestforright);
			bestforright = chooseStoneToMove(rightOfGoal, copyListWithoutGoals, board, gameRowsNumber, gameColumnsNumber, ownColor);
		}
		nodesForBlock.add(new Pair<Node, Node>(bestforright, rightOfGoal));
		//Ende right
		
		return nodesForBlock;
	}
	
	
	
}
