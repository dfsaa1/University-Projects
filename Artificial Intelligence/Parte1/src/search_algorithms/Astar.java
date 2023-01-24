package search_algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import graph_utils.Edge;
import graph_utils.Graph;
import graph_utils.Node;

public class Astar extends SearchAlgorithm {

	List<Node> expandido = new ArrayList<>();


	public Astar(Graph graph) {
		super(graph);
	}

	@Override
	public List<Node> start(Node n_initial, Node n_final) {
		Queue<Node> fila = new PriorityQueue<>(new Comparator<Node>(){
			public int compare(Node a, Node b){
				return a.getF()-b.getF();
			}
		}	
				);
		List<Node> lista = new ArrayList<>();

		fila.offer(n_initial);
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
						Node child =e.getN1();
						int custoTotal=no.getCost()+e.getCost();
						int f = custoTotal+child.getHeuristic();
						
						if(!fila.contains(child) || f<child.getF()){
							child.setCost(custoTotal);
							if(fila.contains(child)){
								fila.remove(child);
							}
							if(!expandido.contains(e.getN1())){
								expandido.add(e.getN1());
								fila.add(e.getN1());
							}
						}	
					}
				}
			}
		}
		return lista;
	}

}
