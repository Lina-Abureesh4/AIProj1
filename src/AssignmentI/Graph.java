package AssignmentI;

import java.util.ArrayList;

public class Graph {

	private ArrayList<Vertex> vertices;
	private boolean isWeighted;
	private boolean isDirected;

	public Graph(boolean isWeighted, boolean isDirected) {
		this.vertices = new ArrayList<Vertex>();
		this.isWeighted = isWeighted;
		this.isDirected = isDirected;
	}

	public Vertex addVertex(String data) {
		Vertex addedVer = new Vertex(data);
		vertices.add(addedVer);
		return addedVer;
	}

	public void addEdge(Vertex ver1, Vertex ver2, Double weight) {
		if(ver1.compareTo(ver2) == 0)
			return; 
		
		if (!isWeighted)
			weight = null;

		ver1.addEdge(ver2, weight);

		if (!isDirected)
			ver2.addEdge(ver1, weight);
	}

	public void removeEdge(Vertex ver1, Vertex ver2) {
		ver1.removeEdge(ver2);
		if (!isDirected)
			ver2.removeEdge(ver1);
	}

	public void removeVertex(Vertex ver) {
		vertices.remove(ver);
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public boolean isWeighted() {
		return isWeighted;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public Vertex getVertexByData(String data) {
		for (Vertex ver : vertices)
			if (ver.getData().equalsIgnoreCase(data))
				return ver;
		return null;
	}
	
	public void printGraph() {
		for(Vertex ver: vertices)
			System.out.println(ver.printVertex(isWeighted));
	}
}
