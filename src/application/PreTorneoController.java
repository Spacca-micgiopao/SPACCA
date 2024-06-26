package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PreTorneoController implements Initializable {
	
	private Main main;
	private Stage stage;
	private String[] tipiTorneo= {"Torneo con 4 giocatori","Torneo con robot"};
	private int numeroGiocatori;
	private MediaPlayer player;
	private static boolean SecondoGiocatoreBot= false;
	@FXML
	private ChoiceBox<String> ScegliTipoTorneo;
	@FXML
	private ChoiceBox<String> CaricaTorneo;
	
	public void setStage(Stage stage) {
        this.stage = stage;
    }
	public void setMain(Main main) {
		this.main = main;
	}
	public void insertMusic() {
    	//MUSICA
        	try {
    			 Media sound = new Media(new File("Musica/marioMusic.mp3").toURI().toString());
    			   player = new MediaPlayer(sound);
    			   //continua sempre a suonare
    			   player.setCycleCount(MediaPlayer.INDEFINITE);
    			   //player.play();
    		}catch(Exception e ) {
    			System.out.println("errore riproduzione");
    		}
     }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//insertMusic();
		ScegliTipoTorneo.getItems().addAll(tipiTorneo);
		ScegliTipoTorneo.setOnAction(this::getTipoTorneo);
		CaricaTorneo.getItems().clear();
		if(SalvaTorneo.Tornei != null)
			CaricaTorneo.getItems().addAll(SalvaTorneo.Tornei);
	}
	
	public void getTipoTorneo(ActionEvent event) {
		String tipoTorneo = ScegliTipoTorneo.getValue();
		if(tipoTorneo=="Torneo con 4 giocatori") {
			numeroGiocatori =4;
		}
		
		if(tipoTorneo=="Torneo con robot") {
			SecondoGiocatoreBot=true;
		}
	}
	public static boolean getSecondoGiocatoreBot(){
		return SecondoGiocatoreBot;
	}
	public void handleConfermaTipoTorneo(ActionEvent event) {
    	//player.stop();
		try {
			if(ScegliTipoTorneo.getValue() != null)
				main.showPreTorneo4Scene();
		}catch(Exception e) {
			System.out.println("errore caricamento preTorneo4");
			e.printStackTrace();
		}
    }
	
	public void Caricatorneo(ActionEvent event) {
		try {
			if(CaricaTorneo.getValue() != null) {
			GameController.flag = 2;
			SalvaTorneo.setFile(CaricaTorneo.getValue());
			main.showScenaGiocoScene();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void handleBottoneMenu(ActionEvent event) {
		//player.stop();
    	try {
    		main.showMainMenuScene();
    	}catch(Exception e) {
    		System.out.println("errore nel caricamento main menu");
    	}
    }
	
}
