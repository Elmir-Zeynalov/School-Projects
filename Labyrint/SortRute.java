public class SortRute extends Rute{
   SortRute(int rad, int kolonne, Labyrint lab){
        super(rad, kolonne, lab);
    }
    char tilTegn(){
        return '#';
    }
    @Override
  public String toString() {
    return tilTegn() +  " [" + rad + "][" + kolonne + "]";
  }
}
