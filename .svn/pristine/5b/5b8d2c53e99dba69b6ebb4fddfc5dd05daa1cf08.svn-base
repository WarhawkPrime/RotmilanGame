package rotmilan;

import java.util.ArrayList;

import softwareengineering.Direction;

public class Pathfinder {

	
	//findet pfad ausgehend von einem Stein
	public Direction getNextDirection(int[][] GameBoard, int ownColor, Node startNode, Node endNode) {

		int gameRows = GameBoard.length;
		int gameColumns = GameBoard[0].length;
		
		int tempHeuristic = 100000;
		Node tempNode = new Node();
		ArrayList<Node> neighbours;
		
		//nachbarn finden die begehbar sind
		try {
		neighbours = getNeighbours(startNode, GameBoard, ownColor, gameRows, gameColumns);
		}
		catch (Exception e) {
			return null;
		}
		
		//wenn es mindestens einen Neighbour gibt
		if(neighbours.size() != 0) {
			//f�r jeden Neighbour
			for(Node neighbour : neighbours ) {
				//heuristik berechnen
				neighbour.setHeuristicCost(calculateHeuristic(neighbour, endNode, gameRows, gameColumns));
				//g�nstigsten ausw�hlen
				if(neighbour.getHeuristicCost() < tempHeuristic) {
					tempHeuristic = neighbour.getHeuristicCost();
					tempNode = neighbour;
				}
			}
			//g�nstigste Richtung wiedergeben
			return tempNode.getDirection();
		}
		//keine Nachbarn gefunden, kein Zug mit diesem Stein m�glich
		else {
			return null;
		}
	}


	
	
	public Node chooseStoneToMove(ArrayList<Node> allOurStones, Node endNode, int[][] GameBoard, int ownColor) {

		int gameRows = GameBoard.length;
		int gameColumns = GameBoard[0].length;
		
		int tempHeuristic = 100000;
		Node tempNode = null;
		
		
		
		//f�r alle Steine auf dem Feld
		for(Node ownStone : allOurStones) {
			
			
			
			//wenn es eine m�glichkeit gibt sich zu bewegen
			if( getNextDirection(GameBoard, ownColor, ownStone, endNode) != null) {
				
				//wenn die aktuelle heuristik besser ist als die alte von einem anderen Stein wird ausgetauscht
				if(calculateHeuristic(ownStone, endNode, gameRows, gameColumns) < tempHeuristic) {
					tempHeuristic = calculateHeuristic(ownStone, endNode, gameRows, gameColumns);
					tempNode = ownStone;
				}
			}
		}
		return tempNode;
	}



	public int calculateHeuristicGameObject(GameObject start, GameObject end, int gameRows, int gameColumns) {
		int heuristicValue = 0;
		int absoluteHorizontalValue;
		int absoluteVerticalValue;

		absoluteHorizontalValue = Math.abs(start.getColumn() - end.getColumn() );
		absoluteVerticalValue = Math.abs(start.getRow() - end.getRow() );

		//kleiner gleich f�r den Fall das es gleich ist, boardSize -1 beim Vergleich
		if(absoluteHorizontalValue > (Math.floor( (gameColumns) / 2))) {
			if(start.getColumn() <= end.getColumn()) {
				absoluteHorizontalValue = (start.getColumn() - 0) + ((gameRows) - end.getColumn()) +1;
			}
			else if(start.getColumn() >= end.getColumn()) {
				absoluteHorizontalValue = (end.getColumn() - 0) + ((gameRows) - start.getColumn()) + 1;
			}
		}
		if(absoluteVerticalValue > (Math.floor(gameRows / 2)) ) {
			if(start.getRow() <= end.getRow()) {
				absoluteVerticalValue = (start.getRow() - 0) + ((gameColumns) - end.getRow()) +1;
			}
			else if(start.getRow() >= end.getRow()) {
				absoluteVerticalValue = (end.getRow() - 0) + ((gameColumns) - start.getRow()) +1;
			}
		}
		heuristicValue = absoluteHorizontalValue + absoluteVerticalValue;
		return heuristicValue;
	}

	
	public int calculateHeuristic(Node start, Node end, int gameRows, int gameColumns) {
		int heuristicValue = 0;
		int absoluteXValue;
		int absoluteYValue;

		absoluteXValue = Math.abs(start.getPosX() - end.getPosX());
		absoluteYValue = Math.abs(start.getPosY() - end.getPosY());

		//kleiner gleich f�r den Fall das es gleich ist, boardSize -1 beim Vergleich
		if(absoluteXValue > (Math.floor( (gameColumns) / 2))) {
			if(start.getPosX() <= end.getPosX()) {
				absoluteXValue = (start.getPosX() - 0) + ((gameRows) - end.getPosX()) +1;
			}
			else if(start.getPosX() >= end.getPosX()) {
				absoluteXValue = (end.getPosX() - 0) + ((gameRows) - start.getPosX()) + 1;
			}
		}
		if(absoluteYValue > (Math.floor(gameRows / 2)) ) {
			if(start.getPosY() <= end.getPosY()) {
				absoluteYValue = (start.getPosY() - 0) + ((gameColumns) - end.getPosY()) +1;
			}
			else if(start.getPosX() >= end.getPosX()) {
				absoluteYValue = (end.getPosY() - 0) + ((gameColumns) - start.getPosY()) +1;
			}
		}
		heuristicValue = absoluteXValue + absoluteYValue;
		return heuristicValue;
	}
	
	
	
	//erstellt die Nachbarn der aktuellen node
	private ArrayList<Node> getNeighbours(Node currentNode, int[][] board, int ownColor, int gameRows, int gameColumns){

		int moveCost = 1;

		ArrayList<Node> neighbours = new ArrayList<Node>();
		int yPos;
		int xPos;

		
		try {
		//Upper Neighbour
		xPos = currentNode.getPosX();
		yPos = ((currentNode.getPosY() % gameRows) -1);
		
		if(currentNode == null)
			System.out.println("null1");

		if(yPos == -1)
			yPos = gameRows -1;

		if( ! checkForBlockedBoardField(yPos, xPos, board, ownColor)) {
			Node upperNeighbour = createNewNode(yPos, xPos, board, ownColor, Direction.UP);
			upperNeighbour.setGCost(currentNode.getGCost() + moveCost);
			upperNeighbour.setParentNode(currentNode);
			neighbours.add(upperNeighbour);
		}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			//index falsch
		}
		catch(NullPointerException e) {
			//nullpointer
		}
		
		
		try {
		//right neighbour
		xPos = ((currentNode.getPosX() % gameColumns) + 1);
		yPos = currentNode.getPosY(); 

		if(xPos == gameColumns)
			xPos = 0;
		if(currentNode == null)
			System.out.println("null12");
		
		if( ! checkForBlockedBoardField(yPos, xPos, board, ownColor)) {
			Node rightNeighbour = createNewNode(yPos, xPos, board, ownColor, Direction.RIGHT);
			rightNeighbour.setGCost(currentNode.getGCost() + moveCost);
			rightNeighbour.setParentNode(currentNode);
			neighbours.add(rightNeighbour);
		}

		// Create lower neighbour
		xPos = currentNode.getPosX();
		yPos = ((currentNode.getPosY() % gameRows) + 1);

		if(yPos == gameRows)
			yPos = 0;

		if(currentNode == null)
			System.out.println("null3");
		
		if( ! checkForBlockedBoardField(yPos, xPos, board, ownColor)) {
			Node lowerNeighbour = createNewNode(yPos, xPos, board, ownColor, Direction.DOWN);
			lowerNeighbour.setGCost(currentNode.getGCost() + moveCost);
			lowerNeighbour.setParentNode(currentNode);
			neighbours.add(lowerNeighbour);
		}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			//index falsch
		}
		catch(NullPointerException e) {
			//nullpointer
		}
		
		
		try {
		// Create left neighbour
		xPos = ((currentNode.getPosX() % gameColumns) - 1);
		yPos = currentNode.getPosY(); 

		if(xPos == -1)
			xPos = gameColumns -1;

		if(currentNode == null)
			System.out.println("null4");
		
		if( ! checkForBlockedBoardField(yPos, xPos, board, ownColor)) {
			Node leftNeighbour = createNewNode(yPos, xPos, board, ownColor, Direction.LEFT);
			leftNeighbour.setGCost(currentNode.getGCost() + moveCost);
			leftNeighbour.setParentNode(currentNode);
			neighbours.add(leftNeighbour);
		}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			//index falsch
		}
		catch(NullPointerException e) {
			//nullpointer
		}
		
		return neighbours;
	}

	
	
	

	public boolean checkForBlockedBoardField(int y, int x, int[][] board, int ownColor) {
		try {
		if( board[y][x] != 0 && board[y][x] != -ownColor) {
			return true;
		}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}

		return false;
	}

	

	// Set isBlocked Attribute for each new node upon creation for use in A* algorithm
	public Node createNewNode(int y, int x, int [][] board, int ownColor, Direction direction) throws RuntimeException {
		boolean blocked = false;

		try {
		if( (board[y][x] != 0) && (board[y][x] != -ownColor) ) {	//ist blockiert von anderen Toren, anderen Steinen, eigenen Steinen
			blocked = true;
		}
		if(board[y][x] == -ownColor) {
			blocked = false;
		}
		Node node = new Node(x, y, blocked, direction);
		return node;
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}


}
