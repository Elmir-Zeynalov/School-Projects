	public class Koe<T> extends Stabel<T> implements Liste<T> {

		@Override
		public void settInn(T element) {
			if(erTom()){
				head=new Node (element);
				tail=head;
			}else{
			    Node nyNode=new Node(element);
				tail.neste=nyNode;
				tail=nyNode;
			}
			size++;
		}	
	}

