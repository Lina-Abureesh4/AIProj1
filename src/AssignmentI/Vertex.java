package AssignmentI;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {

	private String data;
	private ArrayList<Edge> edges;

	public Vertex(String data) {
		this.data = data;
		this.edges = new ArrayList<Edge>();
	}

	public void addEdge(Vertex endVertex, double weight) {
		if (this.compareTo(endVertex) != 0)
			this.edges.add(new Edge(this, endVertex, weight));
	}

	public void removeEdge(Vertex endVertex) {
		// this predicate allows to remove the edge from edges whose endVertex is equal
		// to the one passed
		this.edges.removeIf(edge -> edge.getEnd().equals(endVertex));
	}
	
	public Edge getEdge(Vertex endVertex) {
		for (Edge edge: edges) {
			if(edge.getEnd().compareTo(endVertex) == 0)
				return edge;
		}
		return null; 
	}

	public String getData() {
		return data;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	// this method is used to print the vertex and its edges with the endVertex for
	// each
	// if showWeight parameter is true, the weights will be shown, otherwise, they
	// will not.
	public String printVertex(boolean showWeight) {
		String path = this.data + " --";

		if (edges.size() != 0) {
			for (int i = 0; i < edges.size(); i++) {
				if (showWeight)
					path += "(" + edges.get(i).getWeight() + ")";
				path += "--> " + edges.get(i).getEnd().data;
				if (i < edges.size() - 1)
					path += ", ";
			}
		} else {
			path += "--> null";
		}

		return path;
	}

	@Override
	public int compareTo(Vertex o) {
		return this.data.compareTo(o.getData());
	}

	@Override
	public String toString() {
		return "" +  data;
	}
	
	
}
