// Softwareengineering WS 19/20
// Team Rotmilan

/*Path dient als Klasse um von AStarAlgorithm mit den einzelnen Schritten als k�rzesten Pfad
 * zum Ziel zur�ckgegeben zu werden
 * Dabei wird ein Path in ein GameObject gespeichert, wenn f�r dieses GameObject der k�rzestete Pfad
 * erfragt wurde
 * Innerhalb von Path gibt es getter-Methoden, die zb den n�chsten geplanten Zug zur�ckgeben, 
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

	//gibt den n�chsten Schritt zur�ck 
	public Node getNextNode(int ownRow, int ownColumn) {
		
		for(int j = 0; j < shortestPath.size(); j++) {
			if(shortestPath.get(j).getParentNode().getRow() == ownRow && shortestPath.get(j).getParentNode().getColumn() == ownColumn ) {
				return shortestPath.get(j);
			}
		}
		return null;
	}

//==========Ende methoden
}
