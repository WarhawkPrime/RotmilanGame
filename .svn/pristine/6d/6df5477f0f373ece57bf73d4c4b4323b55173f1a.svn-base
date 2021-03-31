// Softwareengineering WS 19/20
// Team Rotmilan

/*Path dient als Klasse um von AStarAlgorithm mit den einzelnen Schritten als kï¿½rzesten Pfad
 * zum Ziel zurï¿½ckgegeben zu werden
 * Dabei wird ein Path in ein GameObject gespeichert, wenn fï¿½r dieses GameObject der kï¿½rzestete Pfad
 * erfragt wurde
 * Innerhalb von Path gibt es getter-Methoden, die zb den nï¿½chsten geplanten Zug zurï¿½ckgeben, 
 * abhngig von der aktuellen Position des Steins
 * 
 * 
 */


package rotmilan;

import java.util.ArrayList;

public class Path {

	//========== Attributes ==========
	ArrayList<Node> shortestPath;

	//========== Constructor ==========
	public Path(ArrayList<Node> shortestPathSteps) {
		this.shortestPath = shortestPathSteps;
	}

	//========== Anfang Methoden ==========
	public Node getNodeByIndex(int index) {return shortestPath.get(index);}

	public ArrayList<Node> getCompletePathInNodes() {
		return shortestPath;
	}

	//gibt den nächsten Schritt zurück 
	public Node getNextNode(int ownXPos, int ownYPos) {
		
		for(int j = 0; j < shortestPath.size(); j++) {
			if(shortestPath.get(j).getParentNode().getPosX() == ownXPos && shortestPath.get(j).getParentNode().getPosY() == ownYPos ) {
				return shortestPath.get(j);
			}
		}
		return null;
	}

//==========Ende methoden
}
