package application;

import java.io.*;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.ListView;

public class Salvataggi implements Serializable{
	
	//Per serializzazione
	private static final long serialVersionUID = 8861363815419378160L;
	protected GameController gamecontroller;
	protected static File file = new File("");

	public Salvataggi(GameController gamecontroller) {
		this.gamecontroller = gamecontroller;
	}
	
	public static void setFile(String nome) throws IOException {
			file = new File("SPACCA/src/Salvataggi/"+nome+".ser");
			if(!file.exists())
				file.createNewFile();
	}
	
	//Salva in un file serializzato i dati importanti di una partita,in questo caso salva
	//lo stato di alcune parti del gamecontroller	
	public void salvaPartita() throws EOFException{ 
		try { 
			FileOutputStream fileout = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.reset(); 
			out.writeBoolean(gamecontroller.botgioco);
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
			//Carte nei tavoli (salvate nel formato "colore_valore")
			out.writeObject(gamecontroller.carteTavolo1);
			out.writeObject(gamecontroller.carteTavolo2);
			out.writeObject(gamecontroller.carteTavolo3);
			//per capire se siamo in una partita con il bot
			fileout.close();
			out.close(); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvaPartita(GameController gamecontroller) throws EOFException{ 
		try { 
			FileOutputStream fileout = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.reset(); 
			out.writeBoolean(gamecontroller.botgioco);
			//Punteggi dei tavoli
			out.writeInt(gamecontroller.punteggioG1Tavolo1);
			out.writeInt(gamecontroller.punteggioG1Tavolo2);
			out.writeInt(gamecontroller.punteggioG1Tavolo3);
			out.writeInt(gamecontroller.punteggioG2Tavolo1);
			out.writeInt(gamecontroller.punteggioG2Tavolo2);
			out.writeInt(gamecontroller.punteggioG2Tavolo3);
			//Nomi dei partecipanti
			out.writeObject(GameController.player1Name);
			out.writeObject(GameController.player2Name); 
			//Carte di ogni giocatore
			out.writeObject(gamecontroller.listaCarteGiocatore1);
			out.writeObject(gamecontroller.listaCarteGiocatore2);
			//Mazzi
			out.writeObject(gamecontroller.mazzoGiocatore1);
			out.writeObject(gamecontroller.mazzoGiocatore2); 
			//Carte nei tavoli (salvate nel formato "colore_valore")
			out.writeObject(gamecontroller.carteTavolo1);
			out.writeObject(gamecontroller.carteTavolo2);
			out.writeObject(gamecontroller.carteTavolo3);
			//per capire se siamo in una partita con il bot
			fileout.close();
			out.close(); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Carica il dati della pertita scelta
	public void caricaPartita(GameController gc) {
		try {
			if(file.exists()) {
			FileInputStream filein = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(filein);
			//Punteggi dei tavoli
			gc.botgioco = in.readBoolean();
			gc.punteggioG1Tavolo1 = in.readInt();
			gc.punteggioG1Tavolo2 = in.readInt();
			gc.punteggioG1Tavolo3 = in.readInt();
			gc.punteggioG2Tavolo1 = in.readInt();
			gc.punteggioG2Tavolo2 = in.readInt();
			gc.punteggioG2Tavolo3 = in.readInt();
			//Nomi dei partecipanti
			GameController.player1Name = (String) in.readObject();
			GameController.player2Name = (String) in.readObject();
			//Carte di ogni giocatore
			gc.listaCarteGiocatore1 = (ListView<Carta>) in.readObject();
			gc.listaCarteGiocatore2 = (ListView<Carta>) in.readObject();
			//Mazzi
			gc.mazzoGiocatore1 = (Mazzo) in.readObject();
			gc.mazzoGiocatore2 = (Mazzo) in.readObject();
			//Carte nei tavoli
			gc.carteTavolo1 =(String[]) in.readObject();
			gc.carteTavolo2 =(String[]) in.readObject();
			gc.carteTavolo3 =(String[]) in.readObject();
			filein.close();
			in.close();
			}
			else {
				file.createNewFile();
				salvaPartita(gc);
			}
				
		} catch (IOException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void associazioneImmaginiAMazzo(Mazzo mazzo) {
		for(int i=0;i<mazzo.getNumeroCarte();i++) {
			if(mazzo !=null && mazzo.getCarta(i)!= null) {
				Carta CG1 = mazzo.getCarta(i);
				if(CG1 != null) {
					String colore = CG1.getColore();
					int valore = CG1.getValore();
					String percorsoImmagine=  "SPACCA/src/Immagini/" + colore + "_" + valore + ".png";
					Image image = new Image("file:" + percorsoImmagine);
					CG1.setImage(image);
				}
					
			}
		}
	}
	public static void associazioneImmaginiATavolo(String[] carteTavolo1,List<ImageView> imageViewsTavolo1) {
		for(int i=0;i<carteTavolo1.length;i++) {
			if(carteTavolo1[i]!=null) {
				String percorsoImmagine = "SPACCA/src/Immagini/"+carteTavolo1[i]+".png";
				Image image = new Image("file:" + percorsoImmagine);
				imageViewsTavolo1.get(i).setImage(image);
			}
		}
	}
		
	//Elimina il file
	public static void Elimina() {
		file.delete();
	}
}