package softwareengineering.rotmilan.unitTests;

import softwareengineering.Direction;
import softwareengineering.rotmilan.*;

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
		Node a = new Node(3,5,true, Direction.UP);
		a.setfCost(10);
		Node b = new Node(2,4,false, Direction.UP);
		b.setfCost(20);
		Node c = new Node(2, 0, true, Direction.UP);
		c.setfCost(5);
		Node d = new Node(1, 6, false, Direction.UP);
		d.setfCost(40);
		
		pq.add(a);
		pq.add(b);
		pq.add(c);
		pq.add(d);
		
		assertEquals(5, pq.poll().getfCost());
				
	}
	
	@Test
	public void testhComparator() {
		
		Comparator<Node> cmp = new NodeHComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(cmp);
		
		Node a = new Node(3,5,true, Direction.UP);
		a.sethCost(10);
		Node b = new Node(2,4,false, Direction.UP);
		b.sethCost(20);
		Node c = new Node(2, 0, true, Direction.UP);
		c.sethCost(5);
		Node d = new Node(1, 6, false, Direction.UP);
		d.sethCost(40);
		
		pq.add(a);
		pq.add(b);
		pq.add(c);
		pq.add(d);
		
		assertEquals(6, pq.poll().gethCost());

	}
	
	
}
