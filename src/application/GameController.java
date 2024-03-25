package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class GameController {
	
	private Main main;
	
	public String player1Name;
	public String player2Name;
	    
	private Mazzo mazzoGiocatore1;
    private Mazzo mazzoGiocatore2;
    private Mazzo mazzoCompleto;
    
    private Carta cartaSelezionata; //selezionata da utente serve per lo spostamento
    private ImageView cartaCliccata;
    private Mazzo mazzoProvenienzaCartaSelezionata;
    private int punteggioG1Tavolo1;
    private int punteggioG1Tavolo2;
    private int punteggioG1Tavolo3;
    private int punteggioG2Tavolo1;
    private int punteggioG2Tavolo2;
    private int punteggioG2Tavolo3;
    private int vittoriaSuTavoloG1;
    private int vittoriaSuTavoloG2;
    private boolean tuttiTavoliPieni=false;
   // private boolean partitaFinita= false; 
    
    private Stage stage;
    @FXML
    private AnchorPane backgroundPane;
    
    private boolean turnoGiocatore1= true;  //Prima turno giocatore1: pesca poi gioca una carta poi turno giocatore2...
    @FXML
    private Label turnoLabel;
    @FXML
    private Label LabelVincitaT1;
    @FXML
    private Label LabelVincitaT2;
    @FXML
    private Label LabelVincitaT3;
    @FXML
    private ListView<Carta> listaCarteGiocatore1;
    @FXML
    private ListView<Carta> listaCarteGiocatore2;
    @FXML
    private ImageView Carta1G1;  //prima carta del mazzo del giocatore 1
    @FXML
    private ImageView Carta2G1;
    @FXML
    private ImageView Carta3G1;
    @FXML
    private ImageView Carta4G1;
    
    @FXML
    private ImageView Carta1G2; //prima carta del mazzo del giocatore 2
    @FXML
    private ImageView Carta2G2;
    @FXML
    private ImageView Carta3G2;
    @FXML
    private ImageView Carta4G2;
    
    @FXML
    private List<ImageView> imageViewsGiocatore1;
    @FXML
    private List<ImageView> imageViewsGiocatore2;
    @FXML
    private List<ImageView> imageViewsTavolo1;
    @FXML
    private List<ImageView> imageViewsTavolo2;
    @FXML
    private List<ImageView> imageViewsTavolo3;
    
    @FXML
    private Button PescaGiocatore1;
    @FXML
    private Button PescaGiocatore2;
    @FXML
   	private Button BottoneUscita;
  //tavoli da gioco
    //tavolo1
    @FXML 
    private GridPane Tavolo1;
    @FXML
    private ImageView CartaT1p00; //Carta nel tavolo1 in posizione 0,0
    @FXML
    private ImageView CartaT1p10;
    @FXML
    private ImageView CartaT1p01;
    @FXML
    private ImageView CartaT1p11;
    //tavolo2
    @FXML 
    private GridPane Tavolo2;
    @FXML
    private ImageView CartaT2p00; 
    @FXML
    private ImageView CartaT2p10;
    @FXML
    private ImageView CartaT2p01;
    @FXML
    private ImageView CartaT2p11;
    //tavolo3
    @FXML
    private GridPane Tavolo3;
    @FXML
    private ImageView CartaT3p00; 
    @FXML
    private ImageView CartaT3p10;
    @FXML
    private ImageView CartaT3p01;                        
    @FXML               
    private ImageView CartaT3p11;
    //serve per il cambio scena
    public void setMain(Main main) {
    	this.main= main;
    }
    //nomi dei giocatori
    public void setPlayersNames() {
        this.player1Name = ControllerPrePartita.getPlayer1(); 
        this.player2Name = ControllerPrePartita.getPlayer2();
      
    }


    //INIZIALIZZAZIONE
    public void initialize() {
    	setPlayersNames();
    	turnoLabel.setText("TURNO DI "+ " "+ this.player1Name);
    	turnoLabel.setStyle("-fx-text-fill: black;");
    	//Impostazione SFONDO
    	try {
            // Percorso del file immagine
            String filePath = "src/Sfondo/Background_gioco_prova1.jpg";

            // Creazione di un oggetto File con il percorso del file
            File file = new File(filePath);

            // Creazione di un oggetto FileInputStream per leggere il file
            FileInputStream inputStream = new FileInputStream(file);
            backgroundPane.setStyle("-fx-background-image: url('" + file.toURI().toString() + "')");
            
           
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	//mazzo con tutte le carte disponibili
    	mazzoCompleto = new Mazzo();
    	mazzoCompleto.CreaMazzoCompleto();
       // System.out.println("numero carte nel mazzo iniziale completo"+mazzoCompleto.getNumeroCarte());
    	mazzoGiocatore1 = new Mazzo();
    	mazzoGiocatore2 = new Mazzo();
        caricaCarteIniziali();
        
        //per le ImageView: creazione di una lista contenente tutte le ImageView per facilitare 
        //l'aggiunta delle immagini in aggiorna interfaccia
        //giocatore1
        imageViewsGiocatore1 = new ArrayList<>();
        imageViewsGiocatore1.add(Carta1G1);
        imageViewsGiocatore1.add(Carta2G1);
        imageViewsGiocatore1.add(Carta3G1);                                                              
        imageViewsGiocatore1.add(Carta4G1);
        //giocatore2
        imageViewsGiocatore2 = new ArrayList<>();
        imageViewsGiocatore2.add(Carta1G2);
        imageViewsGiocatore2.add(Carta2G2);
        imageViewsGiocatore2.add(Carta3G2);
        imageViewsGiocatore2.add(Carta4G2);
        aggiornaInterfaccia();
        //tavolo1
        imageViewsTavolo1 = new ArrayList<>();
        imageViewsTavolo1.add(CartaT1p00); //Carta nel tavolo1 in posizione 0,0
        imageViewsTavolo1.add(CartaT1p10);
        imageViewsTavolo1.add(CartaT1p01);
        imageViewsTavolo1.add(CartaT1p11);
        //tavolo2
        imageViewsTavolo2 = new ArrayList<>();
        imageViewsTavolo2.add(CartaT2p00); //Carta nel tavolo1 in posizione 0,0
        imageViewsTavolo2.add(CartaT2p10);
        imageViewsTavolo2.add(CartaT2p01);
        imageViewsTavolo2.add(CartaT2p11);
        //tavolo3
        imageViewsTavolo3 = new ArrayList<>();
        imageViewsTavolo3.add(CartaT3p00); //Carta nel tavolo1 in posizione 0,0
        imageViewsTavolo3.add(CartaT3p10);
        imageViewsTavolo3.add(CartaT3p01);
        imageViewsTavolo3.add(CartaT3p11);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void caricaCarteIniziali() {
    	//carica 3 carte nel mazzo di ogni giocatore
    	for(int i=0;i<3;i++) {
    		Carta cartaCasuale = mazzoCompleto.pescaCartaCasuale();
	    	if (cartaCasuale != null) {
	             mazzoGiocatore1.aggiungiCarta(cartaCasuale);
	         }
    	}
    	for(int i=0;i<3;i++) {
    		Carta cartaCasuale = mazzoCompleto.pescaCartaCasuale();
	    	if (cartaCasuale != null) {
	             mazzoGiocatore2.aggiungiCarta(cartaCasuale);
	         }
    	}
    }
    private void aggiornaTurnoLabel() {
 
        if (turnoGiocatore1) {
            turnoLabel.setText("TURNO DI "+ " "+ this.player1Name);
            turnoLabel.setStyle("-fx-text-fill: black;");
        } else {
            turnoLabel.setText("TURNO DI "+ " "+ this.player2Name);
            turnoLabel.setStyle("-fx-text-fill: blue;");
        }
    }
    public void passaTurno() {
    	turnoGiocatore1= !turnoGiocatore1; 
    	
    }
    //FINE PARTITA
    public void verificaFinePartita(){
    	tuttiTavoliPieni = verificaTuttiTavoliSonoPieni();
    	if(tuttiTavoliPieni== true) {
    	//	partitaFinita=true;
    		vittoriaSuTavoloG1 =0;
    		vittoriaSuTavoloG2=0;
    		turnoLabel.setText("FINE PARTITA");
    		//Verifica punteggi su tavolo1
    		if(punteggioG1Tavolo1 > punteggioG2Tavolo1) {
    			vittoriaSuTavoloG1+=1;
    			LabelVincitaT1.setText(this.player1Name+" VINCE");
    		}else {
    			if(punteggioG1Tavolo1 <  punteggioG2Tavolo1) {
    				vittoriaSuTavoloG2+=1;
    				LabelVincitaT1.setText(this.player2Name+" VINCE ");
    			}
    			else {
    				LabelVincitaT1.setText("PAREGGIO");
    			}
    		}
    		//Punteggi su tavolo2
    		if(punteggioG1Tavolo2 > punteggioG2Tavolo2) {
    			vittoriaSuTavoloG1+=1;
    			LabelVincitaT2.setText(this.player1Name+" VINCE ");
    		}else {
    			if(punteggioG1Tavolo2 <  punteggioG2Tavolo2) {
    				vittoriaSuTavoloG2+=1;
    				LabelVincitaT2.setText(this.player2Name+" VINCE");
    			}
    			else {
    				LabelVincitaT2.setText("PAREGGIO");
    			}
    		}
    		//punteggi su tavolo 3
    		if(punteggioG1Tavolo3 > punteggioG2Tavolo3) {
    			vittoriaSuTavoloG1+=1;
    			LabelVincitaT3.setText(this.player1Name+ " VINCE");
    		}else {
    			if(punteggioG1Tavolo3 <  punteggioG2Tavolo3) {
    				vittoriaSuTavoloG2+=1;
    				LabelVincitaT3.setText(this.player2Name+" VINCE");
    			}
    			else {
    				LabelVincitaT3.setText("PAREGGIO");
    			}
    		}
    		
    		//Calcolo punteggio finale
    		if(vittoriaSuTavoloG1 > vittoriaSuTavoloG2) {
    			turnoLabel.setText("VINCITORE FINALE E' "+ " " + player1Name+ " !");
    		}
    		else if(vittoriaSuTavoloG1 == vittoriaSuTavoloG2){
    			turnoLabel.setText("PAREGGIO FINALE");
    		}
    		else {
    		
    			turnoLabel.setText("VINCITORE FINALE E' "+" "+ player2Name+" !");
    		}
    		
    	}
    }
    //condizione della fine partita: tutti tavoli risultano pieni
    public boolean verificaTuttiTavoliSonoPieni() {
    	for(int i=0;i<imageViewsTavolo1.size();i++) {
    		if(imageViewsTavolo1.get(i).getImage()== null ) {
    			return false;
    		}
    	}
    	for(int i=0;i<imageViewsTavolo2.size();i++) {
    		if(imageViewsTavolo2.get(i).getImage()== null ) {
    			return false;
    		}
    	}
    	for(int i=0;i<imageViewsTavolo3.size();i++) {
    		if(imageViewsTavolo3.get(i).getImage()== null ) {
    			return false;
    		}
    	}

    	
    	return true;
    }
    //BOTTONI PESCA 
    //pesca una carta per il giocatore 1 
    public void handlePescaGiocatore1Action(ActionEvent event) {
    	if(turnoGiocatore1) {
	    	
	    	Carta cartaCasuale = mazzoCompleto.pescaCartaCasuale();
	    	
	    	if(cartaCasuale != null &&  mazzoGiocatore1.getNumeroCarte()<4) {
	    		mazzoGiocatore1.aggiungiCarta(cartaCasuale);
	    		aggiornaInterfaccia();
	    	}else {
	    		if(cartaCasuale== null) 
				{System.out.println("carta nulla");
			}
			if(mazzoGiocatore1.getNumeroCarte()>=4) {
				System.out.println("non c'è spazio");
				}
	    	}	
    	}
	
    }
    //pesca una carta per il giocatore2
    public void handlePescaGiocatore2Action(ActionEvent event) {
    	if(!turnoGiocatore1) {
	    	Carta cartaCasuale = mazzoCompleto.pescaCartaCasuale();
	    	
	    	if(cartaCasuale != null && mazzoGiocatore2.getNumeroCarte()<4 ) {
	    		mazzoGiocatore2.aggiungiCarta(cartaCasuale);
	    		aggiornaInterfaccia();
	    		
	    	}else {
	    		if(cartaCasuale== null) 
	    			{System.out.println("carta nulla");
	    		}
	    		if(mazzoGiocatore2.getNumeroCarte()>=4) {
	    			System.out.println("non c'è spazio");
	    			}
	    	}
    	}
    	
    }
    //Bottone uscita dalla finestra
    public void handleBottoneUscita(ActionEvent event) {
    	stage.close();
    }
   //AGGIORNA INTERFACCIA
    public void aggiornaInterfaccia() {
        // Aggiorna le immagini del mazzo del giocatore 1
        for (int i = 0; i < imageViewsGiocatore1.size(); i++) {
            if (i < mazzoGiocatore1.getCarte().size()) {
                Carta c = mazzoGiocatore1.getCarta(i);
                if (c != null) {
                    Image immagine = c.getImmagine();
                    imageViewsGiocatore1.get(i).setImage(immagine);
                } else {
                    // Se la carta è null
                    imageViewsGiocatore1.get(i).setImage(null);
                }
            } else {
                // Se non ci sono più carte nel mazzo del giocatore l'immagine dell'ImageView diventa null
                imageViewsGiocatore1.get(i).setImage(null);
            }
        }

        // Aggiorna le immagini del mazzo del giocatore 2
        for (int i = 0; i < imageViewsGiocatore2.size(); i++) {
            if (i < mazzoGiocatore2.getCarte().size()) {
                Carta c = mazzoGiocatore2.getCarta(i);
                if (c != null) {
                    Image immagine = c.getImmagine();
                    imageViewsGiocatore2.get(i).setImage(immagine);
                } else {
                    // Se la carta è null
                    imageViewsGiocatore2.get(i).setImage(null);
                }
            } else {
                // Se non ci sono più carte nel mazzo del giocatore l'immagine dell'ImageView diventa null
                imageViewsGiocatore2.get(i).setImage(null);
            }
        }
    }


    private void spostaCartaSuTavolo(Carta carta, ImageView posizioneTavolo) {
        // Imposta l'immagine della carta sulla posizione del tavolo
    	 if (carta != null) {
    	        // Imposta l'immagine della carta sulla posizione del tavolo
    	        posizioneTavolo.setImage(carta.getImmagine());
    	        cartaCliccata.setEffect(null);
    	        aggiornaTurnoLabel();
    	    } else {
    	        System.out.println("Errore: carta selezionata è nulla");
    	    }
	}
    
    //GESTORE DEI CLICK SULLE CARTE DEI MAZZI DEI DUE GIOCATORI
    public void handleClickCartaGiocatore1(MouseEvent event) {
    	if(turnoGiocatore1) {
	        cartaCliccata = (ImageView) event.getSource();
	        
	        // Trova l'indice dell'ImageView cliccata
	        int index = imageViewsGiocatore1.indexOf(cartaCliccata);
	        if (index == -1) {
	            return;
	            
	        }
	        mazzoProvenienzaCartaSelezionata= mazzoGiocatore1;
	        // Ottiene la carta associata all'immagine cliccata
	        if (index != -1) {
	            if (index < mazzoGiocatore1.getCarte().size()) {
	                cartaSelezionata = mazzoGiocatore1.getCarta(index);
	                cartaCliccata.setEffect(new DropShadow());
	            } else {
	                return;
	            }
	        }
	        passaTurno();
    	}
    	
	        
    }
    public void handleClickCartaGiocatore2(MouseEvent event) {
    	if(!turnoGiocatore1) {
	        cartaCliccata = (ImageView) event.getSource();
	        // Trova l'indice dell'ImageView cliccata
	        int index = imageViewsGiocatore2.indexOf(cartaCliccata);
	        if (index == -1) {
	            return;
	        }
	        mazzoProvenienzaCartaSelezionata = mazzoGiocatore2;
	        // Ottiene la carta associata all'immagine cliccata
	        if (index != -1) {
	           if (index < mazzoGiocatore2.getCarte().size()) {
	        		cartaSelezionata = mazzoGiocatore2.getCarta(index);
	        		cartaCliccata.setEffect(new DropShadow());
	            } else {
	                return;
	            }
	        }
	        passaTurno();
    	}
        
    }
   
    //GESTORE DEI CLICK SU UNA POSIZIONE DEI TAVOLI
    //CLICK SU PRIMO TAVOLO
    public void handleClickPosizioneTavolo1(MouseEvent event) { // metodo funziona solo se le carte sono diverse
        if (cartaSelezionata != null ) {
            ImageView posizioneTavoloCliccata = (ImageView) event.getSource();
            
            // Verifica che l'indice dell'ImageView sia valido
            int index = imageViewsTavolo1.indexOf(posizioneTavoloCliccata);
            if (index == -1) {
                // ImageView non trovata, esci dalla funzione
                return;
            }
            
            //controllo se la posizione del tavolo è già stata occupata
            if (posizioneTavoloCliccata.getImage() != null) {
                // La posizione è già occupata, quindi non posso posizionare la carta
                return;
            }
            //Aggiornamento punteggio
            if(mazzoProvenienzaCartaSelezionata == mazzoGiocatore1) {
            	punteggioG1Tavolo1 += cartaSelezionata.getValore();
            }else {
            	punteggioG2Tavolo1 += cartaSelezionata.getValore();
            }
            // Sposta la carta selezionata nella posizione del tavolo cliccata
            spostaCartaSuTavolo(cartaSelezionata, posizioneTavoloCliccata);
            // Rimuovi la carta dal mazzo del giocatore
            if (mazzoGiocatore1.contieneCarta(cartaSelezionata)) {
                mazzoGiocatore1.rimuoviCarta(cartaSelezionata);
            } else if (mazzoGiocatore2.contieneCarta(cartaSelezionata)) {
                mazzoGiocatore2.rimuoviCarta(cartaSelezionata);
            }
            //svuota imageView dal mazzoGiocatore1
            for(int i=0;i < imageViewsGiocatore1.size();i++ ) {
            	
            	if(imageViewsGiocatore1.get(i).getImage()==cartaSelezionata.getImmagine()) {
            		imageViewsGiocatore1.get(i).setImage(null);
            		break;
            	}
            }
          //svuota imageView dal mazzoGiocatore2
            for(int i=0;i < imageViewsGiocatore2.size();i++ ) {
            	
            	if(imageViewsGiocatore2.get(i).getImage()==cartaSelezionata.getImmagine()) {
            		imageViewsGiocatore2.get(i).setImage(null);
            		break;
            	}
            }
            // Resetta la carta selezionata
            cartaSelezionata = null;
         
        }
        aggiornaInterfaccia();
        verificaFinePartita();
    }
    //CLICK SUL SECONDO TAVOLO
    public void handleClickPosizioneTavolo2(MouseEvent event) { // metodo funziona solo se le carte sono diverse
        if (cartaSelezionata != null ) {
            ImageView posizioneTavoloCliccata = (ImageView) event.getSource();
            
            // Verifica che l'indice dell'ImageView sia valido
            int index = imageViewsTavolo2.indexOf(posizioneTavoloCliccata);
            if (index == -1) {
                // ImageView non trovata, esci dalla funzione
                return;
            }
            
            //controllo se la posizione del tavolo è già stata occupata
            if (posizioneTavoloCliccata.getImage() != null) {
                // La posizione è già occupata, quindi non posso posizionare la carta
                return;
            }
          //Aggiornamento punteggio
            if(mazzoProvenienzaCartaSelezionata == mazzoGiocatore1) {
            	punteggioG1Tavolo2 += cartaSelezionata.getValore();
            }else {
            	punteggioG2Tavolo2 += cartaSelezionata.getValore();
            }
            // Sposta la carta selezionata nella posizione del tavolo cliccata
            spostaCartaSuTavolo(cartaSelezionata, posizioneTavoloCliccata);
            // Rimuovi la carta dal mazzo del giocatore
            if (mazzoGiocatore1.contieneCarta(cartaSelezionata)) {
                mazzoGiocatore1.rimuoviCarta(cartaSelezionata);
            } else if (mazzoGiocatore2.contieneCarta(cartaSelezionata)) {
                mazzoGiocatore2.rimuoviCarta(cartaSelezionata);
            }
            //svuota imageView dal mazzoGiocatore1
            for(int i=0;i < imageViewsGiocatore1.size();i++ ) {
            	
            	if(imageViewsGiocatore1.get(i).getImage()==cartaSelezionata.getImmagine()) {
            		imageViewsGiocatore1.get(i).setImage(null);
            		break;
            	}
            }
          //svuota imageView dal mazzoGiocatore2
            for(int i=0;i < imageViewsGiocatore2.size();i++ ) {
            	
            	if(imageViewsGiocatore2.get(i).getImage()==cartaSelezionata.getImmagine()) {
            		imageViewsGiocatore2.get(i).setImage(null);
            		break;
            	}
            }
            // Resetta la carta selezionata
            cartaSelezionata = null;
         
        }
        aggiornaInterfaccia();
        verificaFinePartita();
    }
    //CLICK SUL TERZO TAVOLO
    public void handleClickPosizioneTavolo3(MouseEvent event) { // metodo funziona solo se le carte sono diverse
        if (cartaSelezionata != null ) {
            ImageView posizioneTavoloCliccata = (ImageView) event.getSource();
            
            // Verifica che l'indice dell'ImageView sia valido
            int index = imageViewsTavolo3.indexOf(posizioneTavoloCliccata);
            if (index == -1) {
                // ImageView non trovata, esci dalla funzione
                return;
            }
            
            //controllo se la posizione del tavolo è già stata occupata
            if (posizioneTavoloCliccata.getImage() != null) {
                // La posizione è già occupata, quindi non posso posizionare la carta
                return;
            }
          //Aggiornamento punteggio
            if(mazzoProvenienzaCartaSelezionata == mazzoGiocatore1) {
            	punteggioG1Tavolo3 += cartaSelezionata.getValore();
            }else {
            	punteggioG2Tavolo3 += cartaSelezionata.getValore();
            }
            // Sposta la carta selezionata nella posizione del tavolo cliccata
            spostaCartaSuTavolo(cartaSelezionata, posizioneTavoloCliccata);
            // Rimuovi la carta dal mazzo del giocatore
            if (mazzoGiocatore1.contieneCarta(cartaSelezionata)) {
                mazzoGiocatore1.rimuoviCarta(cartaSelezionata);
            } else if (mazzoGiocatore2.contieneCarta(cartaSelezionata)) {
                mazzoGiocatore2.rimuoviCarta(cartaSelezionata);
            }
            //svuota imageView dal mazzoGiocatore1
            for(int i=0;i < imageViewsGiocatore1.size();i++ ) {
            	
            	if(imageViewsGiocatore1.get(i).getImage()==cartaSelezionata.getImmagine()) {
            		imageViewsGiocatore1.get(i).setImage(null);
            		System.out.println("svuotamento iamgeView da lista giocatore1");
            		break;
            	}
            }
          //svuota imageView dal mazzoGiocatore2
            for(int i=0;i < imageViewsGiocatore2.size();i++ ) {
            	
            	if(imageViewsGiocatore2.get(i).getImage()==cartaSelezionata.getImmagine()) {
            		imageViewsGiocatore2.get(i).setImage(null);
            		break;
            	}
            }
            // Resetta la carta selezionata
            cartaSelezionata = null;
           
        }
        aggiornaInterfaccia();
        verificaFinePartita();
    }
   
    
    
    
    
    
    
    
    
}
    

