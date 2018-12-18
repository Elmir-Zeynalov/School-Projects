import java.util.Iterator;
public class StatiskTabell<T> implements Tabell<T> {
	
	 T tabell[];
	 private int antallElem;
	 
	public StatiskTabell(int lengArr){
		tabell=(T[]) new Object[lengArr];
		antallElem=0;
	}
	@Override
	public Iterator<T> iterator() {
		return new MinIteratorG();
	}
	private class MinIteratorG implements Iterator<T>{
		private int startPlass;
		MinIteratorG(){
			this.startPlass=0;
		}
		public boolean hasNext(){
			return startPlass< antallElem &&tabell[startPlass]!=null;
		}
		public T next(){
			//startPlass++;
			return tabell[startPlass++];
		}
		public void remove(){
			 throw new UnsupportedOperationException();
		}
	}

	@Override
	public int storrelse() {
		return antallElem;
	}
	@Override
	public boolean erTom() {
		if(antallElem==0){
		return true;}
	else{
			return false;
		}
	}
	@Override
	public void settInn(T element) {
		if(antallElem<tabell.length){
			tabell[antallElem]=element;
			antallElem++;
		}else{
			//int size=tabell.length;
			//throw new FullTabellUnntak(size);
		}
	}
	@Override
	public T hentFraPlass(int plass) { 
		if(plass<0 || plass>=tabell.length){
			throw new UgyldigPlassUnntak(plass, tabell.length);
			
		}
	
	return tabell[plass]; 

	}

}
