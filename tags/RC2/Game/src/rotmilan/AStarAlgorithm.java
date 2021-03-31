// Softwareengineering WS 19/20
// Team Rotmilan


/*Aufgabe dieser Klasse:
 * mithilfe des A* Algorithmus den k�rzsten Weg durch ein 2D Torus Array finden, ausgehend von einem Startpunkt, mit 
 * Zielpunkt
 * 
 */


/*Vorgehen:
 * Zuerst mit Pseudocode den Algorithmus und dessen vorgehen kurz erkl�ren, dabei �bergabeParameter und r�ckgabeWerte 
 * benennen
 * Danach den Algorithmus schreiben, auf dynamisches Programming achten
 */

/*Pseudocode (sehr grob, wird mit der Zeit genauer)
 * 
 * Die Aufgabe wird am Ende von einer Methode aus aufgerufen.
 * 		Diese Methode bekommt:
 * 		Das Board[][] in seiner aktuellen Form
 * 		Die Position von der aus der Algorithmus laufen soll int rowPos, int ColumnPos (Alternativ das StartObject???)
 * 		Die Position des gew�nschten Zeils (rowPos, int ColumnPos, Alternativ das ZielObject???)
 *  
 * 		Diese Methode gibt zur�ck:
 * 		entweder eine ArrayListe mit allen besuchten Punkten in der Reihenfolge Start->Ziel oder
 * 		Ein Objekt Path, welches als Attribut diese ArrayList beinhaltet, k�nnte dann als Methode haben den Weg einzeln
 * 		zur�ckzugeben, also einen getter f�r einen Wegpunkt f�r Schritt x. Der Pfad w�rde dann aus Nodes bestehen, welche
 * 		die exakten x und y kordinaten enth�lt.
 * 
 * 		Die Methode hei�t:
 *  
 * 		public Path findShortestPath(GameObject startObjekt, GameObject targetObjekt, int[][] currentGameBoard)
 * 
 * 
 * 		zu beginn: 2D array in torus array schreiben:
 */


package rotmilan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import softwareengineering.Direction;

//========== ========== Pseudocode ========== ==========
/*
 * Erstelle die offene Liste <Node>
 * Erstelle die geschlossene Liste <Node>
 * F�ge die eigene Position zu der geschlossenen Liste hinzu (f = 0) 
 * 
 * while(offene Liste != empty)
 * 	finde in der offenen Liste den Knoten mt dem geringsten f, dieser Knoten heisst "q"
 *  
 *  entferne q aus der offenen Liste
 *  
 *  generiere q's 4 Nachbarn (Successors) und setze deren Parent zu q
 *  
 *  fuer jeden successor: 
 *  
 *  	if(successor == goal) stoppe die suche, successor.g = q.g + distanz zwischen successor und q.sucessor.h = distanze vom ziel zum successor
 *  
 *  		successor f = successor.g + successor.h	
 * 
 *		if( eine Node an der selben pos wie successor in der offenen liste existiert und ein geringeres f als der aktuelle successor hat, ueberspringe
 *			den successor
 * 
 *		if(eine Node an der selben pos wie der successor in der geschlossenen liste existiert und ein geringeres f als der aktuelle successor hat, 
 *			ueberspringe den successor,
 *		ansonsten fuege den knoten zum ende dazu (for schleife)
 *
 *		
 *		push q an das ende der geschlossenen Liste (while)
 *
 *
 *
 *	
 */





public class AStarAlgorithm {
	//========== ========== Constructor ========== ==========


	//========== ========== Attributes ========== ==========

	public static final int moveCost = 1;

	//========== ========== Anfang Methoden ========== ==========

	//Methode die aufgerufen wird um den Pfad zum Ziel zu bekommen GameObject hat die Position im Sinne von Board[x][y]
	/*
	 * (board_int[row][column] == color) {
					ourStones.add(new GameObject(row, column, color, Objecttype.STONE));
	 */
	public Path findShortestPath(GameObject startObject, GameObject endObject, int [][] board, int gameRows, int gameColumns ) {
		
		
		int ownColor = startObject.getColor();
		
		Comparator<Node> comparator = new NodeComparator();
		PriorityQueue<Node> openQueue = new PriorityQueue<Node>(comparator);
		PriorityQueue<Node> closedQueue = new PriorityQueue<Node>(comparator);
		
		Node startNode = createNewNode(startObject.getRow(), startObject.getColumn(), board, ownColor, null);
		Node endNode = createNewNode( endObject.getRow(), endObject.getColumn(),board, ownColor, null);
		startNode.setParentNode(null);	//start hat keinen Parent
		
		startNode.setFinalCost(0);
		
		openQueue.add(startNode);
		
		while( ! openQueue.isEmpty()) {
			
			Node currentNode = openQueue.poll();
			
			if(checkForGoal(currentNode, endNode) == true) {
				endNode.setParentNode(currentNode.getParentNode());
				return calculatePathToGoal(endNode);
			}
			
			closedQueue.add(currentNode);
			
			for(Node neighbour : getNeighbours(currentNode, board, ownColor, gameRows, gameColumns)) {
				
				if( ! closedQueue.isEmpty()) {
					neighbour.setFinalCost(neighbour.getGCost() + calculateHeuristic(neighbour, endNode, gameRows, gameColumns));
					
					if( ! openQueue.contains(neighbour)) {
						openQueue.add(neighbour);
					}
					else {
						Node tempNeighbour = openQueue.peek() ;
						if(neighbour.getGCost() < tempNeighbour.getGCost()) {
							tempNeighbour = openQueue.poll();
							tempNeighbour.setGCost(neighbour.getGCost());
							tempNeighbour.setParentNode(neighbour.getParentNode());
							openQueue.add(tempNeighbour);
						}
					}
				}
			}
			
		}
		Path p1 = calculatePathToGoal(closedQueue.poll());
		return p1;
	}		
		
						


	/**
	 * 
	 * @param start
	 * @param end
	 * @param boardSize
	 * @return
	 */
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
		

	// Return false if field is blocked
	public boolean checkForBlockedField(int ownColor, Node node) {
		if(node.isBlocked()) {
			return true;
		}
		else {
			return false;
		}
		
	}	

	// Return false if field is blocked
	public boolean checkForBlockedBoardField(int y, int x, int[][] board, int ownColor) {
		if( board[y][x] != 0 && board[y][x] != -ownColor) {
			return true;
		}
		
		
		return false;
	}


	//testet ob die aktuelle Node das Tor ist
	public boolean checkForGoal(Node currentNode, Node endNode) {
		if( currentNode.getPosX() == endNode.getPosX() && currentNode.getPosY() == endNode.getPosY() ) {
			return true;
		}
		else {
			return false;
		}
	}

	// Set isBlocked Attribute for each new node upon creation for use in A* algorithm
	public Node createNewNode(int y, int x, int [][] board, int ownColor, Direction direction) throws RuntimeException {
		boolean blocked = false;
		
		if( (board[y][x] != 0) && (board[y][x] != -ownColor) ) {	//ist blockiert von anderen Toren, anderen Steinen, eigenen Steinen
			blocked = true;
		}
		if(board[y][x] == -ownColor) {
			blocked = false;
		}
		Node node = new Node(x, y, blocked, direction);
		return node;
	}


	//berechnet for die ausgew�hlte Node, f = g + h
	private void calculateFinalCostsForNode(Node selectedNode, Node targetNode, int boardSizeX, int boardSizeY) {
		selectedNode.setFinalCost( selectedNode.getFinalCost() + calculateHeuristic(selectedNode, targetNode, boardSizeX, boardSizeY) );
	}


	//errechnet den gelaufenen Pfad, indem die Nodes r�ckw�rts gegangen wird (m�glich durch die Eltern)
	private Path calculatePathToGoal(Node connectedEndNode) {
		ArrayList<Node> shortestPathSteps = new ArrayList<Node>();
		Node tmp = connectedEndNode;
		
		while (tmp.getParentNode() != null) {
			if( ! shortestPathSteps.add(tmp))
				throw new RuntimeException("Couldn't add Node to path in calculatePathToGoal()");
			
			tmp = tmp.getParentNode();
		}
		if( ! shortestPathSteps.add(tmp))
			throw new RuntimeException("Couldn't add Node to path in calculatePathToGoal()");
		
		
		Path calculatedPath = new Path(shortestPathSteps);
		return calculatedPath;
	}

	
	//erstellt die Nachbarn der aktuellen node
	private ArrayList<Node> getNeighbours(Node currentNode, int[][] board, int ownColor, int gameRows, int gameColumns){
		ArrayList<Node> neighbours = new ArrayList<Node>();
		int yPos;
		int xPos;
		
		//Upper Neighbour
		xPos = currentNode.getPosX();
		yPos = ((currentNode.getPosY() % gameRows) -1);
		
		if(yPos == -1)
			yPos = gameRows -1;
		
		if( ! checkForBlockedBoardField(yPos, xPos, board, ownColor)) {
			Node upperNeighbour = createNewNode(yPos, xPos, board, ownColor, Direction.UP);
			upperNeighbour.setGCost(currentNode.getGCost() + moveCost);
			upperNeighbour.setParentNode(currentNode);
			neighbours.add(upperNeighbour);
		}
		
		//right neighbour
		xPos = ((currentNode.getPosX() % gameColumns) + 1);
		yPos = currentNode.getPosY(); 

				if(xPos == gameColumns)
					xPos = 0;

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

				if( ! checkForBlockedBoardField(yPos, xPos, board, ownColor)) {
					Node lowerNeighbour = createNewNode(yPos, xPos, board, ownColor, Direction.DOWN);
					lowerNeighbour.setGCost(currentNode.getGCost() + moveCost);
					lowerNeighbour.setParentNode(currentNode);
					neighbours.add(lowerNeighbour);
				}

				// Create left neighbour
				xPos = ((currentNode.getPosX() % gameColumns) - 1);
				yPos = currentNode.getPosY(); 

				if(xPos == -1)
					xPos = gameColumns -1;

				if( ! checkForBlockedBoardField(yPos, xPos, board, ownColor)) {
					Node leftNeighbour = createNewNode(yPos, xPos, board, ownColor, Direction.LEFT);
					leftNeighbour.setGCost(currentNode.getGCost() + moveCost);
					leftNeighbour.setParentNode(currentNode);
					neighbours.add(leftNeighbour);
				}
				return neighbours;
			}
	
}
