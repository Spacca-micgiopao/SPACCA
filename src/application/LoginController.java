package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.HashMap;


public class LoginController {
//Classe per controllare la pagina di Login
	
	private Main main;
	//Crea le istanze per prendere in input Nome e Password
	@FXML
	private TextField inputnome;
	@FXML
	private TextField inputpassword;
	@FXML
	private Button tastoconferma;
	
	//Hashmap per avere username e password per l'admin
	HashMap<String,String> accesso = new HashMap<String,String>();
	
	String Nome;
	String Password;

	
	
	//Circa funziona,da fare : salvare tutti i dati in un file da cui leggerli
	//si può in teoria accedere con qualsiasi combinazione di username e password quindi va risolto
	public void setMain(Main main) {
		this.main = main;
	}
	public void conferma(ActionEvent event) throws IOException {
		//Modificare qui per decidere nome utente e password dell'admin
		accesso.put("Gio", "Cacco");
		Nome = inputnome.getText();
		Password = inputpassword.getText();
		HashMap<String,String> login = new HashMap<String,String>();
		login.put(Nome,Password);
		if(accesso.containsValue(Password) && accesso.containsKey(Nome)) {
			 try {
				main.showPrePartitaScene();
			} catch (Exception e) {
				System.out.println("errore");
			}
			
		}
	}

	//Tasto temporaneo per saltare direttamente al tabellone senza dover fare login
	//ovviamente in questo modo nomi giocatori risultano null
	public void switchtoTabellone(ActionEvent event) throws IOException {
		try {
			main.showScenaGiocoScene();
		} catch (Exception e) {
			System.out.println("");
		}
		

	}
	
	
		
}
	