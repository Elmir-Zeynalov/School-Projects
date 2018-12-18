
public class OrdnetLenkeListe<T extends Comparable<T>>extends Stabel<T>implements Liste<T> {
		@Override
		public void settInn(T element) {
			Node nyNode = new Node(element);
			 if(erTom()){
					head=nyNode;
				}
			 else if(element.compareTo(head.element)<0){
					nyNode.neste=head;
					head=nyNode;
				}else{
					Node temp=head.neste;
					Node forrige=head;
					while(temp!=null){
						if(element.compareTo(temp.element)<0)
							break;
						forrige=temp;
						temp=temp.neste;
					}
					nyNode.neste=forrige.neste;
					forrige.neste=nyNode;
				}
			size++;
		}
		public void skrivUt2() {
	        for(Node gjeldende = head; gjeldende != null; gjeldende = gjeldende.neste) {
	            System.out.println(gjeldende.element);
	        }
	    }
	}