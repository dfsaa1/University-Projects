package tests;

import graph_utils.*;
import search_algorithms.*;

public class SolutionAstar {
	public static void main(String[] args) {
		
		/* 
		 * 								   >(7)
		 * 								  /
		 * 								 /
		 * 							 >(9)
		 * 							/	 \
		 *						   /	  \
		 * 					   >(3)	       \
		 * 					  /	   \		\
		 * 					 /		\		 v
		 * (6)--->(10)--->(4)		 \------>(11)
		 * 					 \             /
		 * 					  \           /
		 * 					   >(8)--->(5)--->(1)
		 * 								  \
		 *								   \
		 *									>(2)
		 *
		 * Initial -> (6) Final -> (11)
		 */
		
		Graph graph = new Graph();

		// creating the nodes
		Node n1 = new Node("1");
		Node n2 = new Node("2");
		Node n3 = new Node("3");
		Node n4 = new Node("4");
		Node n5 = new Node("5");
		Node n6 = new Node("6");
		Node n7 = new Node("7");
		Node n8 = new Node("8");
		Node n9 = new Node("9");
		Node n10 = new Node("10");
		Node n11 = new Node("11");
		
		// Assign the heuristic value to each node 
		// Sabendo que a função heuristica corresponde 'a estimativa do custo do melhor caminho que liga o no' n ao estado final
		n1.setHeuristic(2); //n1-n5 + n5-n11
		n2.setHeuristic(14); //n2-n5 + n5-n11
		n3.setHeuristic(13); //n3-n9 + n9-n11
		n4.setHeuristic(27); //n4-n8 + n8-n5 + n5-n11
		n5.setHeuristic(1); //n5-n11
		n6.setHeuristic(31); //n6-n10 + n10-n4 + n4-n8 + n8-n5 + n5-n11
		n7.setHeuristic(18); //n7-n9 + n9-n11
		n8.setHeuristic(14); //n8-n5 + n5-n11
		n9.setHeuristic(9); //n9-n11
		n10.setHeuristic(30); //n10-n4 + n4-n8 + n8-n5 + n5-n11
		n11.setHeuristic(0); //n11

		// Creating and adding edges to graph
		// Sendo 1 quadrado equivalente a um custo de 1
		graph.addEdge(n6, n10,1);
		graph.addEdge(n10, n4,3);
		graph.addEdge(n4, n3,15);
		graph.addEdge(n4, n8,13);
		graph.addEdge(n3, n9,4);
		graph.addEdge(n3, n11,19);
		graph.addEdge(n9, n7,9);
		graph.addEdge(n9, n11,9);
		graph.addEdge(n8, n5,13);
		graph.addEdge(n5, n11,1);
		graph.addEdge(n5, n1,1);
		graph.addEdge(n5, n2,13);
		
		System.out.println("initial node:" + n6.getLabel());
		System.out.println("final node:" + n11.getLabel());
		
		System.out.println("-----Astar-----");
		SearchAlgorithm astarAlg = new Astar(graph);
		astarAlg.printResult(astarAlg.startSearch(n6,n11));
	
	}
}
