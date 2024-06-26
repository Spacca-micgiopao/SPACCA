package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginController implements Initializable{
//Classe per controllare la pagina di Login
	private Main main;
	//Crea le istanze per prendere in input Nome e Password
	@FXML
	private TextField inputnome;
	@FXML
	private PasswordField inputpassword;
	@FXML
	private Button tastoconferma;
	@FXML
	private Label errori;
	@FXML
	private Text Titolo;
	
	//Hashmap per avere username e password per l'admin
	public static HashMap<String,String> accesso = new HashMap<String,String>();
	
	private String Nome;
	private String Password;
	
	public void setMain(Main main) {
		this.main = main;
	}

	//Per confermare le credenziali e passare al menu principale
	public void conferma(ActionEvent event) throws IOException, InterruptedException {
		Nome = inputnome.getText();
		Password = inputpassword.getText();
		if(accesso.containsValue(Password) && accesso.containsKey(Nome)) {
			 try {
				main.showMainMenuScene();
				InfoPartita.Preparazione();
			} catch (Exception e) {
				System.out.println("errore nel caricamento MainMenu");
				e.printStackTrace();
			}
		}
		//In caso di dimenticanza della password o in casi di emergenza si possono usare queste credenziali
		//baipassando completamente il login,da usare solo in casi di emergenza
		else if(Nome.equals("Hugo") && Password.equals("4815222342")) {
			 try {
					main.showMainMenuScene();
					InfoPartita.Preparazione();
				} catch (Exception e) {
					System.out.println("errore nel caricamento MainMenu");
					e.printStackTrace();
				}
		}
		else
			errori.setText("USERNAME O PASSWORD ERRATI");
	}
	
	//Salva su file e imposta username e password
	public static void SalvaAccesso(File f,String Username,String Password) throws IOException {
		FileWriter fw = new FileWriter(f);
		fw.append(Username+"\n");
		fw.append(Password);
		fw.close();
		accesso.clear();
		accesso.put(Username, Password);
	}
	
	//Carica dal file i dati di accesso,in caso di problemi reimposta il login di default
	public static void caricaAccesso(File f){
		try {
			Scanner scan = new Scanner(f);
			accesso.put(scan.nextLine(), scan.nextLine());
			scan.close();
		} 
		catch (FileNotFoundException e) {
			accesso.put("admin", "");
		}
		catch (NoSuchElementException e) {
			accesso.put("admin", "");
		}
	}
	
	//Per far ruotare il titolo
	private void ruota() {
		RotateTransition rotate = new RotateTransition();
		rotate.setNode(Titolo);
		rotate.setDuration(Duration.millis(20000));
		rotate.setCycleCount(TranslateTransition.INDEFINITE);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setByAngle(360);
		rotate.setAxis(Rotate.Y_AXIS);
		rotate.play();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ruota();
	}
}
	
