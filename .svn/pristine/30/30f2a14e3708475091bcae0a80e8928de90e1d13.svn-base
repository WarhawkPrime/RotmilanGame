package softwareengineering.rotmilan;

import java.util.Comparator;

public class GameObjectComparator implements Comparator<GameObject> {
	
	@Override
	public int compare(GameObject g1, GameObject g2) {
		
		int result = 0;
		
		if(g1.getTempCalculatedHDistanceToTarget() < g2.getTempCalculatedHDistanceToTarget() ) {
			return -1;
		}
		if(g1.getTempCalculatedHDistanceToTarget() > g2.getTempCalculatedHDistanceToTarget() ) {
			return 1;
		}
		return result;
	}

}
