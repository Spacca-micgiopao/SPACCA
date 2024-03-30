package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerPrePartita implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;	
	
	//FILE per salvare le informazioni
	public String G1;
	public String G2;
	//istanza di infoPartita per caricare e scrivere i risultati
	InfoPartita infopartita = new InfoPartita();
	
	//TUTTO FXML
	@FXML
	private TextField InputG1;
	@FXML
	private TextField InputG2;
	@FXML
	private Label Errori;
	@FXML
	Label PartitaScelta;
	@FXML
	ChoiceBox<String> ScegliPartita;
	
	//Carica la lista delle partite salvate nella checkbox
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ScegliPartita.getItems().addAll(infopartita.Partite);
	}
	
	//Costruzione di una nuova partita
	public void IniziaPartita(ActionEvent event) throws IOException {
			//Ottiene in nomi dei 2 giocatori se sono vuoti la partita non puo iniziare
			G1 = InputG1.getText();
			G2 = InputG2.getText();
			if(G1.isBlank() || G2.isBlank())
				Errori.setText("Almeno uno dei nomi non è stato inserito!");
			else if(G1.length() > 20 || G2.length() > 20) {
				Errori.setText("Almeno uno dei nomi è troppo lungo!");
			}
			else if(G1.equalsIgnoreCase(G2)) {
				Errori.setText("Non puoi giocare contro te stesso!");
			}
			//Se i nomi sono consoni agli standard del gioco posso creare la partita
			else {
			infopartita.getG(G1,G2);
			infopartita.ScriviDati();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));
			root = loader.load();
			GameController gamecontroller = loader.getController();
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			}
		}
}
