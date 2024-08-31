package AssignmentI;

public class Edge {

	private Vertex start;
	private Vertex end;
	private Double weight;

	public Edge(Vertex start, Vertex end, Double weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}

	public Vertex getStart() {
		return start;
	}

	public void setStart(Vertex start) {
		this.start = start;
	}

	public Vertex getEnd() {
		return end;
	}

	public void setEnd(Vertex end) {
		this.end = end;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
}
