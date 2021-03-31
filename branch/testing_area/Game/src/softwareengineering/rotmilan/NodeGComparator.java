package softwareengineering.rotmilan;

import java.util.Comparator;


public class NodeGComparator implements Comparator<Node> {
	
	@Override
	public int compare(Node n1, Node n2) {
		
		int result = 0;
		
		if(n1.getgCost() < n2.getgCost()) {
			return -1;
		}
		if(n1.getgCost() > n2.getgCost() ) {
			return 1;
		}
		return result;
	}

}
