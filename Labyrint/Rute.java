abstract class Rute{
 protected int rad;
    protected int kolonne;
    private Rute[] retning = new Rute[4]; //array for nord/syd/vest/oest, i den rekkefoelgen
    private Labyrint lab;
   // private boolean paaVeien = false;

    Rute(int rad, int kolonne, Labyrint lab){
        this.rad = rad;
        this.kolonne = kolonne;
        this.lab = lab;
    }
    public void retningIn(){
        retning[0] = lab.getRute(rad-2, kolonne-1);   //nord
        retning[1] = lab.getRute(rad, kolonne-1);     //syd
        retning[2] = lab.getRute(rad-1, kolonne-2);  //vest
        retning[3] = lab.getRute(rad-1, kolonne);    //oest
    }
    public void finnUtvei(){
        gaa("", this);
    }
    public void gaa(String a, Rute fra){
        if ((this instanceof HviteRute) ){
            if (this instanceof Aapning){
                a += "("+kolonne+","+rad+")";
                lab.leggTilUtvei(a);
            }
            else{
                a += "("+kolonne+","+rad+") --> ";
                for (Rute r: retning){
                    if (r!= null && r != fra){
                        r.gaa(a, this);
                    }
                }
            }
        }
    }
    abstract char tilTegn();
 public int hentRad() { return rad; }
  public int hentKolonne() { return kolonne; }
}