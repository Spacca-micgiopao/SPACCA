package application;

import java.io.*;

import javafx.scene.control.ListView;

public class Salvataggi implements Serializable{
	
	protected GameController gamecontroller;
	File file = new File("partita2.ser");

	public Salvataggi(GameController gamecontroller) {
		this.gamecontroller = gamecontroller;
	}
	
	//Salva in un file serializzato i dati importanti di una partita,in questo caso salva
	//lo stato di alcune parti del gamecontroller
	public void salvaPartita() {
	    try (FileOutputStream fileout = new FileOutputStream(file);
	         ObjectOutputStream out = new ObjectOutputStream(fileout)) {
	    	out.reset();
	    	//Punteggi dei tavoli
	        out.writeInt(gamecontroller.punteggioG1Tavolo1);
	        out.writeInt(gamecontroller.punteggioG1Tavolo2);
	        out.writeInt(gamecontroller.punteggioG1Tavolo3);
	        out.writeInt(gamecontroller.punteggioG2Tavolo1);
	        out.writeInt(gamecontroller.punteggioG2Tavolo2);
	        out.writeInt(gamecontroller.punteggioG2Tavolo3);
	        //Nomi dei partecipanti
	        out.writeObject(gamecontroller.player1Name);
	        out.writeObject(gamecontroller.player2Name);
	        //Carte di ogni giocatore
	        out.writeObject(gamecontroller.listaCarteGiocatore1);
	        out.writeObject(gamecontroller.listaCarteGiocatore2);
	        //Mazzi
	        out.writeObject(gamecontroller.mazzoGiocatore1);
	        out.writeObject(gamecontroller.mazzoGiocatore2);
	        out.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	//Carica il dati della pertita scelta
	public void caricaPartita(GameController gc) {
		try {
			FileInputStream filein = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(filein);
			gc.punteggioG1Tavolo1 = in.readInt();
			gc.punteggioG1Tavolo2 = in.readInt();
			gc.punteggioG1Tavolo3 = in.readInt();
			gc.punteggioG2Tavolo1 = in.readInt();
			gc.punteggioG2Tavolo2 = in.readInt();
			gc.punteggioG2Tavolo3 = in.readInt();
			gc.player1Name = (String) in.readObject();
			gc.player2Name = (String) in.readObject();
			gc.listaCarteGiocatore1 = (ListView<Carta>) in.readObject();
			gc.listaCarteGiocatore2 = (ListView<Carta>) in.readObject();
			//gc.mazzoGiocatore1 = (Mazzo) in.readObject();
			in.close();
			filein.close();
		} catch (IOException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}