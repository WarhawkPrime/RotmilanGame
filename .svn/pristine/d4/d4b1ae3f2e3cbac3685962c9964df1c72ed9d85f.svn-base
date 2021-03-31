// Softwareengineering WS 19/20
// Team Rotmilan

/*Node dient als SpeicherKlasse f�r ein Feld mit den Attributen int rowPos und int columnPos
 * Wird verwendet von AStarAlgorithm als Nodes f�r die Wegfindung, liegen aber auch als Angaben f�r
 * den n�chsten Zug in Path
 * 
 */


package rotmilan;

import softwareengineering.Direction;;

public class Node {
	
	//========== Constructor ==========
	public Node(int row, int column, boolean blocked) {
		this.row = row;
		this.column = column;
		this.isBlocked = blocked;
	}
	//========== Attributes ==========
	private int row; 							//rowPos
	private int column; 							//columnPos
	
	private int finalCost; 						// f(n) = g(n) + h   gesamtkosten der Node
	private int heuristicCost; 					// h  			kosten der Heuristik
	private int gCost;							// kosten bis hierhin
	
	private Direction direction;
	
	private Node parentNode;					//Parent node wird mit gespeichert um den Weg zur�ck verfolgen zu k�nnen 
	private boolean isBlocked;					//attribut ob das Feld geblockt ist
	
	//========== ========== Anfang Methoden ========== ==========
	//========== ========== Getter und Setter ========== ==========
	
	
	public int getGCost() {return gCost;}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setGCost(int gCost) {this.gCost = gCost;} 
	
	public int getHeuristicCost() {return heuristicCost;}
	public void setHeuristicCost(int heuristicCost) {this.heuristicCost = heuristicCost;}
	
	public int getFinalCost() {return finalCost;}
	public void setFinalCost(int finalCost) {this.finalCost = finalCost;}
	
	public Node getParentNode() {return parentNode;}
	public void setParentNode(Node parentNode) {this.parentNode = parentNode;}
	
	public boolean isBlocked() {return this.isBlocked;}
	public void setIsBlocked(boolean blocked) {this.isBlocked = blocked;}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	//========== ========== Ende methoden ========== ==========
	
}
