// Softwareengineering WS 19/20
// Team Rotmilan


/*Aufgabe dieser Klasse:
 * mithilfe des A* Algorithmus den kï¿½rzsten Weg durch ein 2D Torus Array finden, ausgehend von einem Startpunkt, mit 
 * Zielpunkt
 * 
 */


/*Vorgehen:
 * Zuerst mit Pseudocode den Algorithmus und dessen vorgehen kurz erklï¿½ren, dabei ï¿½bergabeParameter und rï¿½ckgabeWerte 
 * benennen
 * Danach den Algorithmus schreiben, auf dynamisches Programming achten
 */

/*Pseudocode (sehr grob, wird mit der Zeit genauer)
 * 
 * Die Aufgabe wird am Ende von einer Methode aus aufgerufen.
 * 		Diese Methode bekommt:
 * 		Das Board[][] in seiner aktuellen Form
 * 		Die Position von der aus der Algorithmus laufen soll int rowPos, int ColumnPos (Alternativ das StartObject???)
 * 		Die Position des gewï¿½nschten Zeils (rowPos, int ColumnPos, Alternativ das ZielObject???)
 *  
 * 		Diese Methode gibt zurï¿½ck:
 * 		entweder eine ArrayListe mit allen besuchten Punkten in der Reihenfolge Start->Ziel oder
 * 		Ein Objekt Path, welches als Attribut diese ArrayList beinhaltet, kï¿½nnte dann als Methode haben den Weg einzeln
 * 		zurï¿½ckzugeben, also einen getter fï¿½r einen Wegpunkt fï¿½r Schritt x. Der Pfad wï¿½rde dann aus Nodes bestehen, welche
 * 		die exakten x und y kordinaten enthï¿½lt.
 * 
 * 		Die Methode heiï¿½t:
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

//========== ========== Pseudocode ========== ==========
/*
 * Erstelle die offene Liste <Node>
 * Erstelle die geschlossene Liste <Node>
 * Fï¿½ge die eigene Position zu der geschlossenen Liste hinzu (f = 0) 
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
 *	heuristik : manhatten: 
 *
 *		h = abs(current_cell.x - goal.x) + abs(current_cell.y  goal.y)
 *
 *		hier ansetzten fuer den Torus!!!!!-> wenn der Abstand c.x - g.x groesser ist als die haelfte der gesamtlaenge von der x-Achse, dann kann die 
 *		moeglichkeit des Torus genutzt werden und die rechnung aendert sich zu (das selbe gilt fuer die y achse): 
 *
 *	
 *
 *
 *		h = abs[ ( (current_cell.x - board.size() ) + goal.x ) + 1 ] + abs[ ( (current_cell.y - board.size() ) + goal.y ) + 1 ]
 *	
 *		Dabei aendert sich dann natraeglich die Richtung, da der Nachbar mit dem geringsten f genommen wird.
 *		 Nur beim bestimmen des Nachbars muss evtl die Richtung ueber  i % array.size() genutzt werden
 *		(fuer zb den Sprung 0 -> 10)
 *		keine andere for schleife wenn man den Index hat, rueckwaerts for macht nur sinn wenn man einzelne elemente rueckwaerts durchgehen will
 *
 */
public class AStarAlgorithm {
	//========== ========== Constructor ========== ==========


	//========== ========== Attributes ========== ==========

	public static final int moveCost = 1;


	//========== ========== Anfang Methoden ========== ==========

	//Methode die aufgerufen wird um den Pfad zum Ziel zu bekommen / pfad muss eine Methode beinhalten um immer den nï¿½chsten Schritt zu geben
	public Path findShortestPath(GameObject startObject, GameObject zielObject, int [][] board ) {

		//int boardSize = board.length; wir brauchen 2 verschiedene Größen, Board kann rechteckig sein
		//Boardsize von x und von y berechnen, beides übergeben
		int boardSizeX = board.length;	//sizeX
		int boardSizeY = board[0].length;	//sizeY
		
		int ownColor = startObject.getColor();

		Comparator<Node> comparator = new NodeComparator();
		PriorityQueue<Node> openQueue = new PriorityQueue<Node>(comparator);
		PriorityQueue<Node> closedQueue = new PriorityQueue<Node>(comparator);


		//erstellen der start Node und der Ziel node
		Node startNode = createNewNode(startObject.getRow(), startObject.getColumn(), board, ownColor);
		startNode.setParentNode(null);
		Node endNode = createNewNode(zielObject.getRow(), zielObject.getColumn(), board, ownColor);

		//kosten von Startknoten berechnen
		//startNode.setFinalCost(0 + calculateHeuristic(startNode, endNode, boardSize));
		startNode.setFinalCost(0);

		//start Node zu der offenen Liste hinzufuegen
		openQueue.add(startNode);

		//solange die offene Liste nicht leer ist:
		while( ! openQueue.isEmpty() ) {

			//node mit den niedrigsten kosten aus der openQueue rausholen und curent node nennen
			Node currentNode = openQueue.poll();

			//wenn die aktuelle node das Ziel ist, dann sind wir hier fertig und geben den Pfad zurueck
			if(checkForGoal(currentNode, endNode) == true) {

				//dann ist das der Pfad zum goal der selbe wie zum currentNode
				endNode.setParentNode(currentNode.getParentNode());

				//der gelaufene Pfad wird zurï¿½ckgegeben, ausgehend vom Goal aus
				return calculatePathToGoal(endNode);
				//return 1;
			}

			//currentNode aus der offenen Liste entfernen und zur geshlossenen Hinzufuegen
			openQueue.remove(currentNode);
			closedQueue.add(currentNode);

			//fuer jeden Nachbar von der aktuellen Node:
			for (Node neighbour: getNeighbours(currentNode, board, ownColor) ) {

				//falls der neighbor nicht in der geschlossenen Liste ist:
				if( ! closedQueue.contains(neighbour)) {
					//berechne die final costs des Neighbours
					neighbour.setFinalCost(neighbour.getGCost() + calculateHeuristic(neighbour, endNode, boardSizeX, boardSizeY));

					//wenn der Nachbar nicht in der offenen liste steht
					if( ! openQueue.contains(neighbour)) {
						//dann füge den Nachbar zur offenen Liste hinzu
						openQueue.add(neighbour);
					}
					//Neighbour ist nicht in der geschlossenen aber schon in der offenen: passiert eig beden überlappenden nodes
					else {
						Node tempNeighbour = openQueue.poll();
						if(neighbour.getGCost() < tempNeighbour.getGCost() ) {
							tempNeighbour.setGCost(neighbour.getGCost());
							tempNeighbour.setParentNode(neighbour.getParentNode());
							openQueue.add(tempNeighbour);
						}
					}
				}
			}
		}
		return null; //rueckgabe wenn kein pfad gefunden wurde, richtige stelle?
		//return 0;
	}		
					
						


	/**
	 * 
	 * @param start
	 * @param end
	 * @param boardSize
	 * @return
	 */
	public int calculateHeuristic(Node start, Node end, int boardSizeX, int boardSizeY) {

		int heuristicValue = 0;
		int absoluteXValue, absoluteYValue;

		absoluteXValue = Math.abs(start.getPosX() - end.getPosX());
		absoluteYValue = Math.abs(start.getPosY() - end.getPosY());

		//für x achse
		if(absoluteXValue > (Math.floor(boardSizeX / 2 )) ) {
			if(start.getPosX() < end.getPosX()) {
				absoluteXValue = (start.getPosX() - 0) + (boardSizeX - (end.getPosX()))  + 1;
			}
			else if(start.getPosX() > end.getPosX()) {
				absoluteXValue = (end.getPosX() - 0) + (boardSizeX - (start.getPosX()))  + 1;
			}
		}
		//für y achse
		if(absoluteYValue > (Math.floor(boardSizeY / 2 )) ) {
			if(start.getPosY() < end.getPosY()) {
				absoluteYValue = (start.getPosY() - 0) + (boardSizeY - (end.getPosY()))  + 1;
			}
			else if(start.getPosY() > end.getPosY()) {
				absoluteYValue = (end.getPosY() - 0) + (boardSizeY - (start.getPosY()))  + 1;
			}
		}
		
		heuristicValue = absoluteXValue + absoluteYValue;
		return heuristicValue;
	}
		

	// Return false if field is blocked
	private boolean checkForBlockedField(int ownColor, Node node) {

		if(node.isBlocked()) {
			return true;
		}
		else {
			return false;
		}
	}	

	// Return false if field is blocked
	private boolean checkForBlockedBoardField(int x, int y, int[][] board, int ownColor) {

		if(board[x][y] != 0 && board[x][y] != -ownColor   ) { //was wenn diese Stelle das Tor ist?
			return true;
		}
		else {
			return false;
		}
	}


	//testet ob die aktuelle Node das Tor ist
	private boolean checkForGoal(Node currentNode, Node endNode) {

		if(currentNode.getPosX() == endNode.getPosX() && currentNode.getPosY() == endNode.getPosY()) {
			return true;
		}
		else {
			return false;
		}

	}

	// Set isBlocked Attribute for each new node upon creation for use in A* algorithm
	private Node createNewNode(int x, int y, int [][] board, int ownColor) {

		boolean blocked = false;

		if(  (board[x][y] != 0) && (board[x][y] != -ownColor) && (board[x][y] != ownColor) ) {
			blocked = true;
		}

		Node node = new Node(x,y, blocked);
		return node;

	}


	//berechnet for die ausgewï¿½hlte Node, f = g + h
	private void calculateFinalCostsForNode(Node selectedNode, Node targetNode, int boardsizeX, int boardSizeY) {

		selectedNode.setFinalCost( selectedNode.getFinalCost() + calculateHeuristic(selectedNode, targetNode, boardsizeX, boardSizeY) );
	}


	//errechnet den gelaufenen Pfad, indem die Nodes rï¿½ckwï¿½rts gegangen wird (mï¿½glich durch die Eltern)
	private Path calculatePathToGoal(Node connectedEndNode) {

		ArrayList<Node> shortestPathSteps = new ArrayList<Node>(); 
		Node tmp = connectedEndNode;

		while(tmp.getParentNode() != null) {

			// Adds Node to end of ArrayList
			if(!shortestPathSteps.add(tmp)) 
				throw new RuntimeException("Couldn't add Node to path in calculatePathToGoal()");

			tmp = tmp.getParentNode();
		}

		if(!shortestPathSteps.add(tmp))
			throw new RuntimeException("Couldn't add Node to path in calculatePathToGoal()");

		// Create Path Object from ArrayList
		Path calculatedPath = new Path(shortestPathSteps);

		return calculatedPath;
	}

	//erstellt die Nachbarn der aktuellen node
	private ArrayList<Node> getNeighbours(Node currentNode, int[][] board, int ownColor){

		ArrayList<Node> neighbours = new ArrayList<Node>();

		int yPos, xPos;
		int boardLengthX = board.length;
		int boardLengthtY = board[0].length;


		// Create upper neighbour
		xPos = currentNode.getPosX();
		yPos = ((currentNode.getPosY() % boardLengthtY) - 1);

		if(yPos == -1)
			yPos = boardLengthtY -1;

		if( ! checkForBlockedBoardField(xPos, yPos, board, ownColor)) {

			Node upperNeighbour = createNewNode(xPos, yPos, board, ownColor);
			upperNeighbour.setGCost(currentNode.getGCost() + moveCost);
			upperNeighbour.setParentNode(currentNode);
			neighbours.add(upperNeighbour);
		}

		// Create right neighbour
		xPos = ((currentNode.getPosX() % boardLengthX) + 1);
		yPos = currentNode.getPosY(); 

		if(xPos == boardLengthX)
			xPos = 0;

		if( ! checkForBlockedBoardField(xPos, yPos, board, ownColor)) {

			Node rightNeighbour = createNewNode(xPos, yPos, board, ownColor);
			rightNeighbour.setGCost(currentNode.getGCost() + moveCost);
			rightNeighbour.setParentNode(currentNode);
			neighbours.add(rightNeighbour);
		}

		// Create lower neighbour
		xPos = currentNode.getPosX();
		yPos = ((currentNode.getPosY() % boardLengthtY) + 1);

		if(yPos == boardLengthtY)
			yPos = 0;

		if( ! checkForBlockedBoardField(xPos, yPos, board, ownColor)) {

			Node lowerNeighbour = createNewNode(xPos, yPos, board, ownColor);
			lowerNeighbour.setGCost(currentNode.getGCost() + moveCost);
			lowerNeighbour.setParentNode(currentNode);
			neighbours.add(lowerNeighbour);
		}

		// Create left neighbour
		xPos = ((currentNode.getPosX() % boardLengthX) - 1);
		yPos = currentNode.getPosY(); 

		if(xPos == -1)
			xPos = boardLengthX -1;

		if( ! checkForBlockedBoardField(xPos, yPos, board, ownColor)) {

			Node leftNeighbour = createNewNode(xPos, yPos, board, ownColor);
			leftNeighbour.setGCost(currentNode.getGCost() + moveCost);
			leftNeighbour.setParentNode(currentNode);
			neighbours.add(leftNeighbour);
		}

		return neighbours;
	}
}
