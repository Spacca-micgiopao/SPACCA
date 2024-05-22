package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class SalvaTorneo implements Serializable {

	private static final long serialVersionUID = 7423889382853862983L;
	static File dir = new File("src/SalvataggiTorneo");
	static File file;
	protected static String[] Tornei;
	
	//Crea cartelle e carica la lista dei tornei,da usare allo start
	public static void Preparazione() throws IOException {
		if(!dir.exists())
			dir.mkdir();
		if(dir.list() != null)
		Tornei = dir.list();
		else
			Tornei = new String[0];
		for(int i = 0;i<Tornei.length;i++) 
			Tornei[i] = Tornei[i].replace(".ser", "");
	}
	
	public static void setFile(String s) throws IOException {
		System.out.println(s);
		file = new File("src/SalvataggiTorneo/"+s+".ser");
	}
	
	//Per salvare il torneo quando si esce
	protected static void Salvataggio(GameController gc) {
		try {
			if(!file.exists())
				file.createNewFile();
			FileOutputStream fileout = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.reset();
			//Parametri da salvare
			out.writeInt(gc.numeroPartita);
			out.writeObject(gc.partite);
			out.writeObject(gc.G);
			out.writeObject(gc.vincitori);
			out.writeObject(gc.numeroVittorieG);
			fileout.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Per caricare un nuovo torneo
	protected static void CaricaTorneo(GameController gc) throws FileNotFoundException {
		try {
			GameController.flag = 2;
			FileInputStream filein = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(filein);
			//Parametri da caricare
			gc.numeroPartita = in.readInt();
			gc.partite = (List<String>) in.readObject();
			gc.G = (String[]) in.readObject();
			gc.vincitori = (List<String>) in.readObject();
			gc.numeroVittorieG = (int[]) in.readObject();
			filein.close();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Elimina tutti i tornei
	protected static void Cancella() {
		String[] n = dir.list();
			if(dir.isDirectory() && dir.exists()) {
				for(int i = 0;i<n.length;i++) {
					File f = new File("src/SalvataggiTorneo/"+n[i]);
					f.delete();
				}
			}
	}
}
