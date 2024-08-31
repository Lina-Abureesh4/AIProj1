package AssignmentI;

public class Queue<T> {

	private CLinkedList<T> queue;

	public Queue() {
		queue = new CLinkedList<>();
	}

	public void enqueue(T data) {
		queue.insertLast(data);
	}

	public Node<T> dequeue() {
		return queue.deleteFirst();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public void clear() {
		while (!isEmpty())
			dequeue();
	}

	public Node<T> getFirst() {
		return queue.getLast().getNext();
	}
}
