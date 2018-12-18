import javafx.application.Application;
import javafx.scene.input.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.util.Optional;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;

public class Oblig7 extends Application{
	static GUIRute[][] ruter;
	Stage vindu=new Stage();
	Labyrint lab;
	private static int antRader;
  	private static int antKolonner;
    private static int antUtveier = 0;
	Rute rute;

	@Override
	public void start(Stage stage)throws Exception{
		velgFil();
		BorderPane root=new BorderPane();
		//*******************************************************************************
		// oppretter Labrynten ved hjelp av GridPane() og setter pa midten av vinduet
		antRader = lab.getRader();
    	antKolonner = lab.getKolonner();
   		Rute[][] labyrintarray = lab.hentLabyrintArray();
		GridPane rutenett = new GridPane();
    	ruter = new GUIRute[antRader][antKolonner];
    	for(int rad = 0; rad < antRader; rad++) {
      		for(int kol = 0; kol < antKolonner; kol++) {
        		ruter[rad][kol] = new GUIRute(labyrintarray[rad][kol], antRader, antKolonner);
        		rutenett.add(ruter[rad][kol], kol, rad);
      		}
    	}
		root.setCenter(rutenett);
		//******************************************************
		//***********filVelger Button***************************
		Button velg =new Button("VelgFil");
		velg.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				try{
				velgFil();
				antRader = lab.getRader();
    	antKolonner = lab.getKolonner();
   		 Rute[][] labyrintarray = lab.hentLabyrintArray();
		GridPane rutenett = new GridPane();
    	ruter = new GUIRute[antRader][antKolonner];
    	for(int rad = 0; rad < antRader; rad++) {
      		for(int kol = 0; kol < antKolonner; kol++) {
        		ruter[rad][kol] = new GUIRute(labyrintarray[rad][kol], antRader, antKolonner);
        		rutenett.add(ruter[rad][kol], kol, rad);
      		}
    	}
		root.setCenter(rutenett);
			}catch(Exception t){}
			}
		});
		//*****************************************************
		Button ryddOpp= new Button("Rydd opp");
        ryddOpp.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	resetFarger();
              	antUtveier=0; 	
            }
        });
        root.setBottom(ryddOpp);
        root.setTop(velg);
		stage.setScene(new Scene(root));
		stage.setTitle("Labyrint");
		stage.show();

	}
	//metode for a aapne filvelgeren
	public void velgFil()throws Exception{
	 	FileChooser filvelger = new FileChooser();
    	filvelger.setTitle("Velg fil");
     	ExtensionFilter filter = new ExtensionFilter("Tekstfiler", "*.txt", "*.in");
     	filvelger.getExtensionFilters().add(filter);
     	File valgtFil = filvelger.showOpenDialog(vindu);
     	if(valgtFil == null) {
        	System.out.println("feil: ingen fil");
        	return;
      	}
      	lab = Labyrint.lesFraFil(valgtFil);
      	Rute[][] labyrintAvRuter = lab.hentLabyrintArray();
  	}

	  
public class GUIRute extends Pane {
  	private int storrelse;
  	private Rute rute;
	private int antRader;
  	private int antKolonner;

  	public GUIRute(Rute rute, int antRader, int antKolonner) {
		this.rute = rute;
		this.antRader = antRader;
		this.antKolonner = antKolonner;
  
    	oppdaterFarge();
    	setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
    	setMinWidth(50);
    	setMinHeight(50);
    	addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
       		@Override
     		public void handle(MouseEvent event) {
	         	Liste<String> losninger=null;
	        	try {
	            	losninger = lab.finnUtveiFra(rute.hentKolonne(), rute.hentRad());
	            	if(rute instanceof HviteRute) {
	              		resetFarger();
	              		setUtveiFarge();
	             		finnerLosninger(losninger);
	            	} else {
	              		setUtveiFarge();
	             		resetFarger();
	           			setIngenUtveier();
	            		}
	          	} catch(NullPointerException e) {
	          		System.out.println("null losninger");
	           		setIngenUtveier();
          			}
        		}
    		});

  }
  // setter Farger paa boksene 
  public void oppdaterFarge() {
    if(rute instanceof HviteRute) {
     	setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }
    if(rute instanceof SortRute) {
     	setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }
  }//egen metode for a skille utveiene
  public void setUtveiFarge() {
    setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
  }
}
	
	public static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
       boolean[][] losning = new boolean[hoyde][bredde];
       java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
       java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
       while(m.find()) {
          int x = Integer.parseInt(m.group(1))-1;
          int y = Integer.parseInt(m.group(2))-1;
          losning[y][x] = true;

       }
       return losning;

    }
   // public static void oppdaterAntUtveier() {
    	//displayAntLosning.setText("" + antUtveier);
  	//}
  public static void setIngenUtveier() {
  	antUtveier = 0;
  }
  public static void resetFarger() {
    for(int rad = 0; rad < antRader; rad++) {
      for(int kol = 0; kol < antKolonner; kol++) {
          ruter[rad][kol].oppdaterFarge();
      }
    }
  }
  //metode som finner losningene og bruker den gitte boolean[][] metoden for aa finne utveine/losningene
   public void finnerLosninger(Liste<String> utveier) {
    antUtveier = utveier.storrelse();
   Koe<String> alleUtveier = new Koe<String>();
    for(String str : utveier) {
      alleUtveier.settInn(str);
    }
    String utvei = alleUtveier.head.element;
    System.out.println(utvei);
    boolean[][] losninger = losningStringTilTabell(utvei, antKolonner, antRader);
    for(int i = 0; i < antRader; i++) {
      for(int j = 0; j < antKolonner; j++) {
        if(losninger[i][j]) {
          ruter[i][j].setUtveiFarge();
        }
      }
    }
   
  }
	public static void main(String[] args){
		launch(args);
	}
}