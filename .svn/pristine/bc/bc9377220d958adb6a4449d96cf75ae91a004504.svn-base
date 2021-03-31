// Softwareengineering WS 19/20
//// Team Rotmilan
//
//
///*Aufgabe dieser Klasse:
// * mithilfe des A* Algorithmus den k�rzsten Weg durch ein 2D Torus Array finden, ausgehend von einem Startpunkt, mit 
// * Zielpunkt
// * 
// */
//
//
///*Vorgehen:
// * Zuerst mit Pseudocode den Algorithmus und dessen vorgehen kurz erkl�ren, dabei �bergabeParameter und r�ckgabeWerte 
// * benennen
// * Danach den Algorithmus schreiben, auf dynamisches Programming achten
// */
//
///*Pseudocode (sehr grob, wird mit der Zeit genauer)
// * 
// * Die Aufgabe wird am Ende von einer Methode aus aufgerufen.
// * 		Diese Methode bekommt:
// * 		Das Board[][] in seiner aktuellen Form
// * 		Die Position von der aus der Algorithmus laufen soll int rowPos, int ColumnPos (Alternativ das StartObject???)
// * 		Die Position des gew�nschten Zeils (rowPos, int ColumnPos, Alternativ das ZielObject???)
// *  
// * 		Diese Methode gibt zur�ck:
// * 		entweder eine ArrayListe mit allen besuchten Punkten in der Reihenfolge Start->Ziel oder
// * 		Ein Objekt Path, welches als Attribut diese ArrayList beinhaltet, k�nnte dann als Methode haben den Weg einzeln
// * 		zur�ckzugeben, also einen getter f�r einen Wegpunkt f�r Schritt x. Der Pfad w�rde dann aus Nodes bestehen, welche
// * 		die exakten x und y kordinaten enth�lt.
// * 
// * 		Die Methode hei�t:
// *  
// * 		public Path findShortestPath(GameObject startObjekt, GameObject targetObjekt, int[][] currentGameBoard)
// * 
// * 
// * 		zu beginn: 2D array in torus array schreiben:
// */
//
//
package rotmilan;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.PriorityQueue;
//
//import softwareengineering.Direction;
//
////========== ========== Pseudocode ========== ==========
///*
// * Erstelle die offene Liste <Node>
// * Erstelle die geschlossene Liste <Node>
// * F�ge die eigene Position zu der geschlossenen Liste hinzu (f = 0) 
// * 
// * while(offene Liste != empty)
// * 	finde in der offenen Liste den Knoten mt dem geringsten f, dieser Knoten heisst "q"
// *  
// *  entferne q aus der offenen Liste
// *  
// *  generiere q's 4 Nachbarn (Successors) und setze deren Parent zu q
// *  
// *  fuer jeden successor: 
// *  
// *  	if(successor == goal) stoppe die suche, successor.g = q.g + distanz zwischen successor und q.sucessor.h = distanze vom ziel zum successor
// *  
// *  		successor f = successor.g + successor.h	
// * 
// *		if( eine Node an der selben pos wie successor in der offenen liste existiert und ein geringeres f als der aktuelle successor hat, ueberspringe
// *			den successor
// * 
// *		if(eine Node an der selben pos wie der successor in der geschlossenen liste existiert und ein geringeres f als der aktuelle successor hat, 
// *			ueberspringe den successor,
// *		ansonsten fuege den knoten zum ende dazu (for schleife)
// *
// *		
// *		push q an das ende der geschlossenen Liste (while)
// *
// *
// *
// *	heuristik : manhatten: 
// *
// *		h = abs(current_cell.x - goal.x) + abs(current_cell.y  goal.y)
// *
// *		hier ansetzten fuer den Torus!!!!!-> wenn der Abstand c.x - g.x groesser ist als die haelfte der gesamtlaenge von der x-Achse, dann kann die 
// *		moeglichkeit des Torus genutzt werden und die rechnung aendert sich zu (das selbe gilt fuer die y achse): 
// *
// *	
// *
// *
// *		h = abs[ ( (current_cell.x - board.size() ) + goal.x ) + 1 ] + abs[ ( (current_cell.y - board.size() ) + goal.y ) + 1 ]
// *	
// *		Dabei aendert sich dann natraeglich die Richtung, da der Nachbar mit dem geringsten f genommen wird.
// *		 Nur beim bestimmen des Nachbars muss evtl die Richtung ueber  i % array.size() genutzt werden
// *		(fuer zb den Sprung 0 -> 10)
// *		keine andere for schleife wenn man den Index hat, rueckwaerts for macht nur sinn wenn man einzelne elemente rueckwaerts durchgehen will
// *
// */
//public class AStarAlgorithm {
//	//========== ========== Constructor ========== ==========
//
//
//	//========== ========== Attributes ========== ==========
//
//	public static final int moveCost = 1;
//
//
//	//========== ========== Anfang Methoden ========== ==========
//
//	//Methode die aufgerufen wird um den Pfad zum Ziel zu bekommen / pfad muss eine Methode beinhalten um immer den n�chsten Schritt zu geben
//	public Path findShortestPath(GameObject startObject, GameObject zielObject, int [][] board, int gameRows, int gameColumns) {
//
//		//int boardSize = board.length; wir brauchen 2 verschiedene Gr��en, Board kann rechteckig sein
//		//Boardsize von x und von y berechnen, beides �bergeben
//		
//		int ownColor = startObject.getColor();
//
//		Comparator<Node> comparator = new NodeComparator();
//		PriorityQueue<Node> openQueue = new PriorityQueue<Node>(comparator);
//		PriorityQueue<Node> closedQueue = new PriorityQueue<Node>(comparator);
//
//
//		//erstellen der start Node und der Ziel node
//		Node startNode = createNewNode(startObject.getRow(), startObject.getColumn(), board, ownColor);
//		startNode.setParentNode(null);
//		Node endNode = createNewNode(zielObject.getRow(), zielObject.getColumn(), board, ownColor);
//
//		//kosten von Startknoten berechnen
//		//startNode.setFinalCost(0 + calculateHeuristic(startNode, endNode, boardSize));
//		startNode.setFinalCost(0);
//
//		//start Node zu der offenen Liste hinzufuegen
//		openQueue.add(startNode);
//
//		//solange die offene Liste nicht leer ist:
//		while( ! openQueue.isEmpty() ) {
//
//			//node mit den niedrigsten kosten aus der openQueue rausholen und curent node nennen
//			Node currentNode = openQueue.poll();
//
//			//wenn die aktuelle node das Ziel ist, dann sind wir hier fertig und geben den Pfad zurueck
//			if(checkForGoal(currentNode, endNode) == true) {
//
//				//dann ist das der Pfad zum goal der selbe wie zum currentNode
//				endNode.setParentNode(currentNode.getParentNodeNode());
//
//				
//				
//				
//				//der gelaufene Pfad wird zur�ckgegeben, ausgehend vom Goal aus
//				return calculatePathToGoal(endNode);
//				//return 1;
//			}
//
//			//currentNode aus der offenen Liste entfernen und zur geshlossenen Hinzufuegen
//			openQueue.remove(currentNode);
//			closedQueue.add(currentNode);
//
//			//fuer jeden Nachbar von der aktuellen Node:
//			for (Node neighbour: getNeighbours(currentNode, board, ownColor, gameRows, gameColumns) ) {
//
//				//falls der neighbor nicht in der geschlossenen Liste ist:
//				if( ! closedQueue.contains(neighbour)) {
//					//berechne die final costs des Neighbours
//					neighbour.setFinalCost(neighbour.getGCost() + calculateHeuristic(neighbour, endNode, gameRows, gameColumns));
//
//					//wenn der Nachbar nicht in der offenen liste steht
//					if( ! openQueue.contains(neighbour)) {
//						//dann f�ge den Nachbar zur offenen Liste hinzu
//						openQueue.add(neighbour);
//					}
//					//Neighbour ist nicht in der geschlossenen aber schon in der offenen: passiert eig beden �berlappenden nodes
//					else {
//						Node tempNeighbour = openQueue.poll();
//						if(neighbour.getGCost() < tempNeighbour.getGCost() ) {
//							tempNeighbour.setGCost(neighbour.getGCost());
//							tempNeighbour.setParentNode(neighbour.getParentNodeNode());
//							openQueue.add(tempNeighbour);
//						}
//					}
//				}
//			}
//		}
//		return null; //rueckgabe wenn kein pfad gefunden wurde, richtige stelle?
//		//return 0;
//	}		
//					
//						
//
//
//	/**
//	 * 
//	 * @param start
//	 * @param end
//	 * @param boardSize
//	 * @return
//	 */
//	public int calculateHeuristic(Node start, Node end, int gameRows, int gameColumns) {
//
//		int heuristicValue = 0;
//		int absoluteRowValue, absoluteColumnValue;
//
//		absoluteRowValue = Math.abs(start.getRow() - end.getRow());
//		absoluteColumnValue = Math.abs(start.getColumn() - end.getColumn());
//
//		//f�r x achse
//		if(absoluteRowValue > (Math.floor(gameRows / 2 )) ) {
//			if(start.getRow() < end.getRow()) {
//				absoluteRowValue = (start.getRow() - 0) + (gameRows - (end.getRow()))  + 1;
//			}
//			else if(start.getRow() > end.getRow()) {
//				absoluteRowValue = (end.getRow() - 0) + (gameRows - (start.getRow()))  + 1;
//			}
//		}
//		//f�r y achse
//		if(absoluteColumnValue > (Math.floor(gameColumns / 2 )) ) {
//			if(start.getColumn() < end.getColumn()) {
//				absoluteColumnValue = (start.getColumn() - 0) + (gameColumns - (end.getColumn()))  + 1;
//			}
//			else if(start.getColumn() > end.getColumn()) {
//				absoluteColumnValue = (end.getColumn() - 0) + (gameColumns - (start.getColumn()))  + 1;
//			}
//		}
//		
//		heuristicValue = absoluteRowValue + absoluteColumnValue;
//		return heuristicValue;
//	}
//		
//
//	// Return false if field is blocked
//	private boolean checkForBlockedField(int ownColor, Node node) {
//
//		if(node.isBlocked()) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}	
//
//	// Return false if field is blocked
//	private boolean checkForBlockedBoardField(int row, int column, int[][] board, int ownColor) {
//
//		if(board[row][column] != 0 && board[row][column] != -ownColor   ) { //was wenn diese Stelle das Tor ist?
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
//
//
//	//testet ob die aktuelle Node das Tor ist
//	private boolean checkForGoal(Node currentNode, Node endNode) {
//
//		if(currentNode.getRow() == endNode.getColumn() && currentNode.getRow() == endNode.getColumn()) {
//			return true;
//		}
//		else {
//			return false;
//		}
//
//	}
//
//	// Set isBlocked Attribute for each new node upon creation for use in A* algorithm
//	private Node createNewNode(int row, int column, int [][] board, int ownColor) {
//
//		boolean blocked = false;
//
//		if(  (board[row][column] != 0) && (board[row][column] != -ownColor) && (board[row][column] != ownColor) ) {
//			blocked = true;
//		}
//
//		Node node = new Node(row,column, blocked);
//		return node;
//
//	}
//
//
//	//berechnet for die ausgew�hlte Node, f = g + h
//	private void calculateFinalCostsForNode(Node selectedNode, Node targetNode, int gameRows, int gameColumns) {
//
//		selectedNode.setFinalCost( selectedNode.getFinalCost() + calculateHeuristic(selectedNode, targetNode, gameRows, gameColumns) );
//	}
//
//
//	//errechnet den gelaufenen Pfad, indem die Nodes r�ckw�rts gegangen wird (m�glich durch die Eltern)
//	private Path calculatePathToGoal(Node connectedEndNode) {
//
//		ArrayList<Node> shortestPathSteps = new ArrayList<Node>(); 
//		Node tmp = connectedEndNode;
//
//		while(tmp.getParentNodeNode() != null) {
//
//			// Adds Node to end of ArrayList
//			if(!shortestPathSteps.add(tmp)) 
//				throw new RuntimeException("Couldn't add Node to path in calculatePathToGoal()");
//
//			tmp = tmp.getParentNodeNode();
//		}
//
//		if(!shortestPathSteps.add(tmp))
//			throw new RuntimeException("Couldn't add Node to path in calculatePathToGoal()");
//
//		// Create Path Object from ArrayList
//		Path calculatedPath = new Path(shortestPathSteps);
//
//		return calculatedPath;
//	}
//
//	//erstellt die Nachbarn der aktuellen node
//	private ArrayList<Node> getNeighbours(Node currentNode, int[][] board, int ownColor, int gameRows, int gameColumns){
//
//		ArrayList<Node> neighbours = new ArrayList<Node>();
//
//		int column, row;
//
//
//		// Create left neighbour
//		row = currentNode.getRow();
//		column = ((currentNode.getColumn() % gameRows + gameRows)%gameRows) -1;
//		//(i % M + M) % M
//
//		if(column == -1)
//			column = gameColumns -1;
//
//		if( ! checkForBlockedBoardField(row, column, board, ownColor)) {
//
//			Node upperNeighbour = createNewNode(row, column, board, ownColor);
//			upperNeighbour.setDirection(Direction.LEFT);
//			upperNeighbour.setGCost(currentNode.getGCost() + moveCost);
//			upperNeighbour.setParentNode(currentNode);
//			neighbours.add(upperNeighbour);
//		}
//
//		// Create lower neighbour
//		row = ((currentNode.getRow() % gameColumns + gameColumns)%gameColumns) +1;
//		column = currentNode.getColumn(); 
//
//		if(row == gameRows)
//			row = 0;
//
//		if( ! checkForBlockedBoardField(row, column, board, ownColor)) {
//
//			Node rightNeighbour = createNewNode(row, column, board, ownColor);
//			rightNeighbour.setDirection(Direction.DOWN);
//			rightNeighbour.setGCost(currentNode.getGCost() + moveCost);
//			rightNeighbour.setParentNode(currentNode);
//			neighbours.add(rightNeighbour);
//		}
//
//		// Create right neighbour
//		row = currentNode.getRow();
//		column = ((currentNode.getColumn() % gameRows + gameRows) %gameRows) +1;
//
//		if(column == gameColumns)
//			column = 0;
//
//		if( ! checkForBlockedBoardField(row, column, board, ownColor)) {
//
//			Node lowerNeighbour = createNewNode(row, column, board, ownColor);
//			lowerNeighbour.setDirection(Direction.RIGHT);
//			lowerNeighbour.setGCost(currentNode.getGCost() + moveCost);
//			lowerNeighbour.setParentNode(currentNode);
//			neighbours.add(lowerNeighbour);
//		}
//
//		// Create upper neighbour
//		row = ((currentNode.getRow() % gameColumns + gameColumns)%gameColumns) -1;
//		column = currentNode.getColumn(); 
//
//		if(row == -1)
//			row = gameRows -1;
//
//		if( ! checkForBlockedBoardField(row, column, board, ownColor)) {
//
//			Node leftNeighbour = createNewNode(row, column, board, ownColor);
//			leftNeighbour.setDirection(Direction.UP);
//			leftNeighbour.setGCost(currentNode.getGCost() + moveCost);
//			leftNeighbour.setParentNode(currentNode);
//			neighbours.add(leftNeighbour);
//		}
//
//		return neighbours;
//	}
//}
import java.util.ArrayList;

import java.util.Comparator;

import java.util.PriorityQueue;



public class AStarAlgorithm {

	public AStarAlgorithm() {

	}

	//main method to find the shortest Path in a 2D array

	public Path findShortestPath(Node startNode, Node endNode, int[/*maxRows*/][/*maxCols*/] gameBoard, int gameRowNumber, int gameColumnNumber, int ownColor) {

		//kosten für einen Zug

		int moveCost = 1;

		//Vorbereitung für den Pfad

		ArrayList<Node> neighbours = new ArrayList<Node>();

		Path foundPath;

		//Eine offene und eine geschlossene Liste

		Comparator<Node> comparator = new NodeComparator();

		PriorityQueue<Node> openQueue = new PriorityQueue<Node>(comparator);

		PriorityQueue<Node> closedQueue = new PriorityQueue<Node>(comparator);

		openQueue.clear();

		closedQueue.clear();

		//Start == Ziel? wenn ja wäre der stein in das Ziel gekommen

		if(isGoal(startNode, endNode) == true) {

			return null;

		}

		//start in die offene Liste, f = 0

		startNode.setFinalCost(0);

		openQueue.add(startNode);

		//solange die offene Liste nicht leer ist

		while( !openQueue.isEmpty()) {

			//currentNpode mit dem geringsten f aus der open Liste

			Node currentNode = openQueue.poll();

			//ist currentNode unser Ziel?

			if(currentNode.getParentNode() != null) {

				if(isGoal(currentNode, endNode)) {

					return foundPath = createPathToGoal(endNode);

				}

			}

			closedQueue.add(currentNode);

			//alle möglichen Nachbarn für curentNode finden

			neighbours = findAllNeighbours(currentNode, gameBoard, gameRowNumber, gameColumnNumber, ownColor, moveCost);

			//Alle Nachbarn von current durchgehen

			for(Node neighbour : neighbours) {

				//wenn der Neighbour in der closed List ist, ignorieren. Wenn nicht dann:

				if( isInQueue(neighbour, closedQueue) == false && closedQueue.contains(neighbour) == false ) {

					//g, h und f berechnen

					neighbour.setGCost(currentNode.getGCost() + moveCost);

					neighbour.setHeuristicCost(calculateHeuristic(neighbour, endNode, gameRowNumber, gameColumnNumber));

					neighbour.setFinalCost(neighbour.getGCost() + neighbour.getHeuristicCost());

					//wenn der Neighbour nicht in der openQueue ist

					if( isInQueue(neighbour, openQueue) == false && openQueue.contains(neighbour) == false) {

						neighbour.setParentNode(currentNode);

						openQueue.add(neighbour);

						//Wenn der Nachbar das Ziel ist, erst hier da er erst jetzt einen parent hat

						if(isGoal(neighbour, endNode)) {

							return foundPath = createPathToGoal(neighbour);

						}

					}

					//wenn die Node schon in der offenen Queue ist, testetn ob dieser Pfad zu der Node besser ist

					else {

						Node oldNode = takeCopyFromQueue(neighbour, openQueue);

						if( neighbour.getFinalCost() < oldNode.getFinalCost() ) {

							oldNode.setFinalCost(neighbour.getFinalCost());

							oldNode.setHeuristicCost(neighbour.getHeuristicCost());

							oldNode.setGCost(neighbour.getGCost());

							oldNode.setParentNode(neighbour.getParentNode());

							openQueue.add(oldNode);	//notwendig?

						}

					}

				}

			}

		}

		return null;	//kein Pfad gefunden

	}

	//Calculates the manhatten heuristic distance between two points

	public int calculateHeuristic(Node start, Node end, int gameRowsNumber, int gameColumnsNumber) {

		int heuristicValue = 0;

		int absoluteHorizontalValue;

		int absoluteVerticalValue;

		absoluteHorizontalValue = Math.abs(start.getColumn() - end.getColumn());

		absoluteVerticalValue = Math.abs(start.getRow() - end.getRow() );

		if(absoluteHorizontalValue > (Math.floor( (gameColumnsNumber) / 2))) {

			if(start.getColumn() <= end.getColumn() ) {

				absoluteHorizontalValue = (start.getColumn() - 0) + ((gameColumnsNumber) - end.getColumn() ) ;

			}

			else if(start.getColumn() >= end.getColumn() ) {

				absoluteHorizontalValue = (end.getColumn() - 0) + ((gameColumnsNumber) - start.getColumn() ) ;

			}

		}

		if(absoluteVerticalValue > (Math.floor(gameRowsNumber / 2)) ) {

			if(start.getRow() <= end.getRow()) {

				absoluteVerticalValue = (start.getRow() - 0) + ((gameRowsNumber) - end.getRow()) ;

			}

			else if(start.getRow() >= end.getRow()) {

				absoluteVerticalValue = (end.getRow() - 0) + ((gameRowsNumber) - start.getRow()) ;

			}

		}

		heuristicValue = absoluteHorizontalValue + absoluteVerticalValue;

		return heuristicValue;

	}

	//Methode um die Nachbarn einer Node zu bekommen

	public ArrayList<Node> findAllNeighbours(Node currentNode, int[/*maxRows*/][/*maxCols*/] board, int gameRowNumber, int gameColumnNumber, int ownColor, int moveCost){

		int rowNumber;

		int columnNumber;

		int rowIndex = gameRowNumber - 1;

		int columnIndex = gameColumnNumber -1;

		ArrayList<Node> neighbours = new ArrayList<Node>();



		//========== Upper Neighbour ==========

		columnNumber = currentNode.getColumn();

		rowNumber = ((currentNode.getRow() -1));

		if(rowNumber == -1)

			rowNumber = rowIndex;



		if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {

			Node upperNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor);

			//upperNeighbour.setgCost(currentNode.getgCost() + moveCost);

			//upperNeighbour.setParent(currentNode);

			neighbours.add(upperNeighbour);

		}



		//========== Right neighbour ==========

		columnNumber = ((currentNode.getColumn() + 1));

		rowNumber = currentNode.getRow();

		if(columnNumber > columnIndex)

			columnNumber = 0;

		if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {

			Node rightNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor);

			//rightNeighbour.setgCost(currentNode.getgCost() + moveCost);

			//rightNeighbour.setParent(currentNode);

			neighbours.add(rightNeighbour);

		}

		//========== Lower neighbour ==========

		columnNumber = currentNode.getColumn();

		rowNumber = ((currentNode.getRow() + 1));



		if(rowNumber > rowIndex)

			rowNumber = 0;



		if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {

			Node lowerNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor);

			//lowerNeighbour.setgCost(currentNode.getgCost() + moveCost);

			//lowerNeighbour.setParent(currentNode);

			neighbours.add(lowerNeighbour);

		}

		//========== Left neighbour ==========

		columnNumber = ((currentNode.getColumn() -1));

		rowNumber = currentNode.getRow();



		if(columnNumber == -1)

			columnNumber = columnIndex;



		if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {

			Node leftNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor);

			//leftNeighbour.setgCost(currentNode.getgCost() + moveCost);

			//leftNeighbour.setParent(currentNode);

			neighbours.add(leftNeighbour);

		}

		return neighbours;

	}

	//Methode um ein Feld zu überprüfen, ob es geblockt ist

	public boolean fieldIsBlocked(int rowNumber, int columnNumber, int[/*maxRows*/][/*maxCols*/] board, int ownColor) {

		//alles außer 0 und -ownColor ist eine Blockade

		if( board[rowNumber][columnNumber] != 0 && board[rowNumber][columnNumber] != -ownColor ) {

			return true;

		}

		else {

			return false;

		}

	}

	//Methode um eine neue Node zu erstellen

	public Node createNewNode(int rowNumber, int columnNumber, int [/*maxRows*/][/*maxCols*/] board, int ownColor ) {

		boolean isBlocked;

		//wenn es nicht 0 ist und nicht -ownColor ist, dann ist es eine Blockade

		if( board[rowNumber][columnNumber] != 0 && board[rowNumber][columnNumber] != -ownColor ) {

			isBlocked = true;

		}

		else {

			isBlocked = false;

		}

		Node node = new Node(rowNumber, columnNumber, isBlocked);

		return node;

	}

	//Methode um zu testen on die Node das Ziel ist

	public boolean isGoal(Node currentNode, Node goal ) {

		//Ziel gefunden

		if( currentNode.getRow() == goal.getRow() && currentNode.getColumn() == goal.getColumn() ) {

			return true;

		}

		else {

			return false;

		}

	}

	//Methode um den Pfad von Start zu Ziel zu bekommen

	public Path createPathToGoal(Node endNode) {

		ArrayList<Node> neighbours = new ArrayList<Node>();

		Node tmpNode = endNode;

		while(tmpNode.getParentNode() != null) {

			neighbours.add(tmpNode);

			tmpNode = tmpNode.getParentNode();

		}

		neighbours.add(tmpNode);

		Path foundPath = new Path(neighbours);

		return foundPath;

	}

	//Methode die Prüft ob eine Node schon in einer Queue enthalten ist

	public boolean isInQueue(Node nodeToTest, PriorityQueue<Node> toSearchedQueue) {

		PriorityQueue<Node> tempQueue = new PriorityQueue<Node>(toSearchedQueue);

		for(int i = 0; i < tempQueue.size(); i++) {

			Node tempNode = tempQueue.poll();

			if( ( nodeToTest.getRow() == tempNode.getRow() ) && ( nodeToTest.getColumn() == tempNode.getColumn() ) ) {

				return true;

			}

		}

		return false;

	}

	//Methode um die Kopie eines Elements aus einer Liste zu bekommen

	public Node takeCopyFromQueue(Node nodeToTake, PriorityQueue<Node> toBeTakenFromQueue) {

		PriorityQueue<Node> tempQueue = new PriorityQueue<Node>(toBeTakenFromQueue);

		for(int i = 0; i < tempQueue.size(); i++) {

			Node tempNode = tempQueue.poll();

			if( ( nodeToTake.getRow() == tempNode.getRow() ) && ( nodeToTake.getColumn() == tempNode.getColumn() ) ) {	
				return tempNode;	

			}

		}

		return null;

	}



}
