package AssignmentI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

public class SeatingArrangement {

	static String path = "dislike.csv"; // file path
	private static String[] people; // guests names
	private static double[][] heuristics; // heuristic dislike values as read from the file
	private static MinHeap<Double> frontier; // MinHeap to store costs and give the minimum value
	private static Graph graph = new Graph(true, false);
	private static Vertex startVer; // initial state
	private static Vertex goal; // goal

	public static void main(String[] args) {

		readDislike(0); // try using values: 4, 3, 0, 2
		LinkedList<Vertex> result = UCS(startVer);
		printResult(result);

		System.out.println(
				"-------------------------------------------------------------------------------------------------");
		LinkedList<Vertex> result2 = AStar(startVer);
		printResult(result2);

		System.out.println(
				"-------------------------------------------------------------------------------------------------");
		Set<Vertex> result3 = GBFS(startVer);
		printResult(result3);
	}

	// read dislike data
	public static void readDislike(int startFrom) {
		File file = new File(path);
		try {
			Scanner sc = new Scanner(file);
			people = sc.nextLine().replaceFirst(",", "").split(",");
			for (int i = 0; i < people.length; i++) {
				Vertex ver = graph.addVertex(people[i]);
				if (i == startFrom) {
					startVer = ver;
					goal = startVer;
				}
			}
			heuristics = new double[people.length][people.length];
			int i = 0;
			while (sc.hasNext()) {
				String values[] = sc.nextLine().replaceFirst("[a-z | A-Z]*,", "").split(",");
				for (int j = 0; j < people.length; j++) {
					heuristics[i][j] = Float.parseFloat(values[j].replaceFirst("%", ""));
					graph.getVertexByData(people[i]).addEdge(graph.getVertexByData(people[j]), heuristics[i][j]);
				}
				i++;
			}

			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Uniform Cost Search Algorithm
	public static LinkedList<Vertex> UCS(Vertex startVer) {

		Set<LinkedList<Vertex>> explored = new LinkedHashSet<>(); // explored seating arrangements are stored here
		LinkedList<Vertex> start = new LinkedList<>(); // initial state (seating arrangement)
		start.add(startVer);
		frontier = new MinHeap<>(100000);
		HashMap<LinkedList<Vertex>, Double> map = new HashMap<>(); // mapping from any seating arrangement to its total
																	// cost
		map.put(start, 0.0);
		frontier.add(map.get(start));

		while (!frontier.isEmpty()) {
			LinkedList<Vertex> state = getKey2(map, frontier.removeMin());
			explored.add(state);

			if (goalTest(state)) {
				System.out.println("UCS Algorithm Succeeded!");
				System.out.println("Total Cost: " + map.get(state));
				return state;
			}

			for (Edge edge : state.get(state.size() - 1).getEdges()) {
				Vertex neighbour = edge.getEnd();
				if (!state.contains(neighbour) || (state.contains(neighbour) && state.size() == people.length
						&& neighbour.compareTo(goal) == 0)) {
					LinkedList<Vertex> new_state = new LinkedList<>();
					for (Vertex ver : state)
						new_state.add(ver);
					new_state.add(neighbour);
					if (!exist(explored, new_state)) {
						double new_cost = map.get(state) + Math.pow(edge.getWeight(), 2); // cost: f(n) = g(n) = n^2
						map.put(new_state, new_cost);
						frontier.add(new_cost);
					}
				}
			}
		}
		return null;
	}

	// Greedy Best-First Search Algorithm
	public static Set<Vertex> GBFS(Vertex startVer) {

		Set<Vertex> explored = new LinkedHashSet<>(); // seating arrangement is stored here
		HashMap<Vertex, Double> map = new HashMap<>(); // mapping from any state to its cost
		frontier = new MinHeap<>(1000);
		map.put(startVer, 0.0);
		frontier.add(map.get(startVer));

		while (!frontier.isEmpty()) {
			Vertex state = getKey(map, frontier.removeMin());

			if (state == null)
				continue;

			explored.add(state);

			if (goalTest(explored)) {
				System.out.println("GBFS Algorithm Succeeded!");
				System.out.println("Total Cost: " + (map.get(state) + state.getEdge(startVer).getWeight()));
				return explored;
			}

			for (Edge edge : state.getEdges()) {
				Vertex neighbour = edge.getEnd();
				if (!explored.contains(neighbour)) {
					double new_cost = map.get(state) + edge.getWeight(); // cost: f(n) = h(n) = n
					map.put(neighbour, new_cost);
					frontier.add(new_cost);
				}
			}

		}
		return null;
	}

	// A* Algorithm
	public static LinkedList<Vertex> AStar(Vertex startVer) {

		Set<LinkedList<Vertex>> explored = new LinkedHashSet<>(); // explored seating arrangements are stored here
		LinkedList<Vertex> start = new LinkedList<>(); // initial state (seating arrangement)
		start.add(startVer);
		frontier = new MinHeap<>(1000000);
		HashMap<LinkedList<Vertex>, Double> map = new HashMap<>(); // mapping from seating arrangement to its total cost
		map.put(start, 0.0);
		frontier.add(map.get(start));

		while (!frontier.isEmpty()) {
			LinkedList<Vertex> state = getKey2(map, frontier.removeMin());
			explored.add(state);

			if (goalTest(state)) {
				System.out.println("A* Algorithm Succeeded!");
				System.out.println("Total Cost: " + map.get(state));
				return state;
			}

			for (Edge edge : state.get(state.size() - 1).getEdges()) {
				Vertex neighbour = edge.getEnd();
				if (!state.contains(neighbour) || (state.contains(neighbour) && state.size() == people.length
						&& neighbour.compareTo(goal) == 0)) {
					LinkedList<Vertex> new_state = new LinkedList<>();
					for (Vertex ver : state)
						new_state.add(ver);
					new_state.add(neighbour);
					if (!exist(explored, new_state)) {
						// cost: f(n) = g(n) + h(n) = n^2 + n
						double new_cost = map.get(state) + Math.pow(edge.getWeight(), 2) + edge.getWeight();
						map.put(new_state, new_cost);
						frontier.add(new_cost);
					}
				}
			}
		}
		return null;
	}

	// test if the goal seating arrangement given by GBFS algorithm is reached
	public static boolean goalTest(Set<Vertex> explored) {
		return explored.size() == people.length;
	}

	// get the key by its value in map (used for GBSF algorithm)
	public static Vertex getKey(HashMap<Vertex, Double> map, Double value) {
		for (Entry<Vertex, Double> entry : map.entrySet()) {
			if (entry.getValue().compareTo(value) == 0) {
				return entry.getKey();
			}
		}
		return null;
	}

	// print final seating arrangement given by GBFS algorithm
	public static void printResult(Set<Vertex> result) {
		if (result == null || result.size() == 0)
			System.out.println("There is no solution");
		else
			System.out.print("Seating Arrangement: " + result);
	}

	// test if the goal seating arrangement given by USC or A* algorithm is reached
	public static boolean goalTest(LinkedList<Vertex> state) {
		return state.size() == people.length + 1 && state.get(state.size() - 1).compareTo(goal) == 0;
	}

	// get the key by its value in map (used for UCS or A* algorithm)
	public static LinkedList<Vertex> getKey2(HashMap<LinkedList<Vertex>, Double> map, Double value) {
		for (Entry<LinkedList<Vertex>, Double> entry : map.entrySet()) {
			if (entry.getValue().compareTo(value) == 0) {
				return entry.getKey();
			}
		}
		return null;
	}

	// print final seating arrangement given by UCS or A* algorithm
	public static void printResult(LinkedList<Vertex> result) {
		if (result == null || result.size() == 0)
			System.out.println("There is no solution");
		else {
			System.out.print("Seating Arrangement: [");
			for (int i = 0; i < result.size() - 1; i++) {
				System.out.print(result.get(i).getData());
				if (i < result.size() - 2)
					System.out.print(", ");
			}
			System.out.println("]");
		}
	}

	// test if a given state (seating arrangement) was explored in UCS or A*
	// algorithm
	public static boolean exist(Set<LinkedList<Vertex>> explored, LinkedList<Vertex> state) {
		for (LinkedList<Vertex> list : explored) {
			if (list.size() == state.size()) {
				if (list.equals(state))
					return true;
			}
		}
		return false;
	}
}
