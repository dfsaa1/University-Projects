package search_algorithms;

import java.util.*;
import graph_utils.*;

/**
 * This abstract class represents a general search algorithm
 * 
 * @author Rita Peixoto, Sancho Oliveira
 * @version 1.1
 *
 */

public abstract class SearchAlgorithm {

	// The graph which represents the search space
	private Graph graph;
	// The adjacency list of the graph
	private Map<Node, List<Edge>> adjacencyList;

	// Constructor, graph as parameter
	public SearchAlgorithm(Graph graph) {
		this.graph = graph;
		adjacencyList = graph.getAdjacencyList();
	}

	// Returns the graph
	public Graph getGraph() {
		return graph;
	}

	// Returns the adjacency list of a Node, a list of edges to the neighbors nodes
	public List<Edge> adjacencyOfNode(Node n) {
		return adjacencyList.get(n);
	}

	// INCOMPLETE METHOD
	/*
	 * Verifies if the initial node is equal to the final node if it is equals it
	 * stops the search else calls the abstract method start
	 */
	// Returns a ordered list of nodes with the search result
	
	public List<Node> startSearch(Node n_initial, Node n_final) {
		List<Node> result = new ArrayList<Node>();

		if (n_initial.equals(n_final)) {
			result.add(n_initial);
			System.out.println("Initial node is equal to the final node");
			return result;
		} else {
			result = start(n_initial, n_final);
			return result;
		}
	}

	// Abstract method
	// Returns a ordered list of nodes with the search result
	public abstract List<Node> start(Node n_initial, Node n_final);

	// Prints the result of a search algorithm
	public void printResult(List<Node> list) {
		System.out.println("Search Result:");
		String result = "";
		for (Node n : list) {
			if (list.get(list.size() - 1).equals(n)) {
				result += n.getLabel();
			} else {
				result += n.getLabel() + "->";
			}
		}
		System.out.println(result);
	}
}
