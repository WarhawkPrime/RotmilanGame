package rotmilan.unitTests;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import rotmilan.*;
import softwareengineering.*;
import java.util.ArrayList;

public class TestSteinAuswahl {
	@Test
	public void testTorusSteinAuswahl()
	{
	int goal_row = 4;
	int goal_column = 2;
	int gameRows = 6;
	int gameColumns = 5;
	
/*	
	0	0	1	0	2
	1	0	0	0	2
	0	0	-2	0	0
	0	0	0	0	2
	0	0	-1	0	2
	0	0	0	0	2
*/	
	
	int color = 1;
	//int goal = Objecttype.GOAL;
	ArrayList<GameObject> ourStones = new ArrayList<GameObject>();
	GameObject expected = new GameObject(0, 2, color);
	ourStones.add(expected);
	ourStones.add(new GameObject(1, 0, color));
	//ourStones.add(new GameObject(0,2,color, 0));
	SteinAuswahl steinauswahl = new SteinAuswahl(goal_row, goal_column, gameRows, gameColumns);
	
	GameObject actual = steinauswahl.bestenSteinAuswaehlen(ourStones);
	assertEquals(expected, actual);
	}
	
}
