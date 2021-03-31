package softwareengineering.rotmilan;

import java.util.Comparator;


public class NodePathComparator implements Comparator<Node> {
	
	@Override
	public int compare(Node n1, Node n2) {
		
		int result = 0;
		
		if(n1.getPathToStart().getPathInNodes().size() < n2.getPathToStart().getPathInNodes().size() ) {
			return -1;
		}
		if(n1.getPathToStart().getPathInNodes().size() > n2.getPathToStart().getPathInNodes().size() ) {
			return 1;
		}
		return result;
	}

}
