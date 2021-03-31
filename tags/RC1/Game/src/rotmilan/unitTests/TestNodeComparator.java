package rotmilan.unitTests;

import rotmilan.*;
import org.junit.*;
//import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;


public class TestNodeComparator {

	
	//Write test functions for comparator class here
	@Test
	public void TestNodeComparator() {
		
		Comparator<Node> cmp = new NodeComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(cmp);
		Node a = new Node(3,5,true);
		a.setFinalCost(10);
		Node b = new Node(2,4,false);
		b.setFinalCost(20);
		Node c = new Node(2, 0, true);
		c.setFinalCost(5);
		Node d = new Node(1, 6, false);
		d.setFinalCost(40);
		
		pq.add(a);
		pq.add(b);
		pq.add(c);
		pq.add(d);
		
		assertEquals(5, pq.poll().getFinalCost());
				
	}
	
	
	
}
