import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stabel<T> implements Liste<T> {
	protected class Node{
		Node neste;
		T element;
		Node(T elm){
			element=elm;
		}
	}
	protected Node head,tail;
	protected int size;
	
	@Override
	public Iterator iterator() {
		return new stabIterator();
	
	}
	protected class stabIterator implements Iterator{
		private Node current;
		stabIterator(){
			current=head;
		}
		@Override
		public boolean hasNext() {
			return current!=null;}
		@Override
		public T next() {
			T element = current.element;
           current = current.neste;
            return element;
		}
		
	}
		
	@Override
	public int storrelse() {
		return size;
	}

	@Override
	public boolean erTom() {
		return  head==null;
	}
	@Override
	public void settInn(T element) {
		if(erTom()){
			head=new Node(element);
		}else{
			Node nyNode=new Node(element);
			nyNode.neste=head;
			head=nyNode;	
		}
		size++;
	}
	@Override
	public T fjern() {
		Node temp=head;
		if(erTom()){
			return null;
		}else{
			head=head.neste;
			}
		size--;
	return temp.element;
	}
	
}
