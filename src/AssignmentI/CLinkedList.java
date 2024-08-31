package AssignmentI;

public class CLinkedList<T> {

	private Node<T> last;

	public Node<T> getLast() {
		return last;
	}

	public void insertLast(T data) {
		Node<T> newNode = new Node<>(data);
		if (isEmpty())
			last = newNode;
		else
			newNode.setNext(last.getNext());
		last.setNext(newNode);
		last = newNode;
	}

	public Node<T> deleteFirst() {
		if (!isEmpty()) {
			Node<T> deleted = last.getNext();
			if (last.getNext() == last)
				last = null;
			else
				last.setNext(last.getNext().getNext());
			return deleted;
		}
		return null;
	}

	public void traverse() {
		Node<T> curr = last.getNext();
		if (curr != null) {
			do {
				System.out.println(curr);
				curr = curr.getNext();
			} while (curr != last.getNext());
		}
	}

	public boolean isEmpty() {
		return last == null;
	}
}