// Softwareengineering WS 19/20
// Team Rotmilan

/*Node dient als SpeicherKlasse f�r ein Feld mit den Attributen int rowPos und int columnPos
 * Wird verwendet von AStarAlgorithm als Nodes f�r die Wegfindung, liegen aber auch als Angaben f�r
 * den n�chsten Zug in Path
 * 
 */


package rotmilan;

import softwareengineering.Direction;

public class Node {
	
	//========== Constructor ==========
	public Node(int posX, int posY, boolean blocked, Direction direction) {
		this.posX = posX;
		this.posY = posY;
		this.isBlocked = blocked;
		this.direction = direction;
	}
	
	public Node(int posX, int posY, boolean blocked) {
		this.posX = posX;
		this.posY = posY;
		this.isBlocked = blocked;
	}
	
	public Node() {
		
	}
	
	//========== Attributes ==========
	private int posX; 							//rowPos
	private int posY; 							//columnPos
	
	private int finalCost; 						// f(n) = g(n) + h   gesamtkosten der Node
	private int heuristicCost; 					// h  			kosten der Heuristik
	private int gCost;							// kosten bis hierhin
	
	private Node parentNode;					//Parent node wird mit gespeichert um den Weg zur�ck verfolgen zu k�nnen 
	private boolean isBlocked;
	private Direction direction;
	//attribut ob das Feld geblockt ist
	
	//========== ========== Anfang Methoden ========== ==========
	//========== ========== Getter und Setter ========== ==========
	public int getPosX() {return posX;}
	public void setPosX(int posX) {this.posX = posX;}
	
	public int getPosY() {return posY;}
	public void setPosY(int posY) {this.posY = posY;}
	
	public int getGCost() {return gCost;}
	public void setGCost(int gCost) {this.gCost = gCost;} 
	
	public int getHeuristicCost() {return heuristicCost;}
	public void setHeuristicCost(int heuristicCost) {this.heuristicCost = heuristicCost;}
	
	public int getFinalCost() {return finalCost;}
	public void setFinalCost(int finalCost) {this.finalCost = finalCost;}
	
	public Node getParentNode() {return parentNode;}
	public void setParentNode(Node parentNode) {this.parentNode = parentNode;}
	
	public boolean isBlocked() {return this.isBlocked;}
	public void setIsBlocked(boolean blocked) {this.isBlocked = blocked;}
	
	public Direction getDirection() {return this.direction;}
	public void setDirection(Direction d) {this.direction = d;}
	//========== ========== Ende methoden ========== ==========
}
