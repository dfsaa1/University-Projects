package search_algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import graph_utils.Edge;
import graph_utils.Graph;
import graph_utils.Node;

public class BreadthFirst extends SearchAlgorithm {

	//LARGURA PRIMEIRO

	List<Node> expandido = new ArrayList<>();

	public BreadthFirst(Graph graph) {
		super(graph);
	}

	@Override
	public List<Node> start(Node n_initial, Node n_final) {
		Queue<Node> fila = new LinkedList<>();
		List<Node> lista = new ArrayList<>();

		fila.add(n_initial);
		expandido.add(n_initial);

		while(!fila.isEmpty()){
			Node no = fila.poll();
			lista.add(no);
			if(no.equals(n_final)){
				return lista;
			}else{
				List<Edge> vizinhos = super.adjacencyOfNode(no);
				if(vizinhos != null){
					for(Edge e : vizinhos){
						if(!expandido.contains(e.getN1())){
							expandido.add(e.getN1());
							fila.add(e.getN1());	
						}
					}
				}
			}
		}
		return lista;
	}

}
