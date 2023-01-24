package search_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import graph_utils.Edge;
import graph_utils.Graph;
import graph_utils.Node;

public class DepthFirst extends SearchAlgorithm {

	//PROFUNDIDADE PRIMEIRO

	List<Node> expandido = new ArrayList<>();

	public DepthFirst(Graph graph) {
		super(graph);
	}

	@Override
	public List<Node> start(Node n_initial, Node n_final) {

		Stack<Node> pilha = new Stack<>();
		List<Node> lista = new ArrayList<>();

		pilha.push(n_initial);
		expandido.add(n_initial);

		while(!pilha.isEmpty()){
			Node no = pilha.pop();
			if(no.equals(n_final)){
				lista.add(n_final);
				return lista;
			}else{
				List<Edge> vizinhos = super.adjacencyOfNode(no);
				if(vizinhos != null){
					for(Edge e : vizinhos){
						if(!expandido.contains(e.getN1())){
							expandido.add(e.getN1());
							pilha.push(e.getN1());
						}
						if(!lista.contains(e.getN0())){
							lista.add(e.getN0());
						}
					}
				}
			}
		}
		return lista;
	}

}
