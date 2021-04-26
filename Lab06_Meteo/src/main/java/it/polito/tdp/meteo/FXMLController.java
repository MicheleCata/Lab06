/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Integer mese = boxMese.getValue();
    	
    	List<Citta> citta = model.trovaSequenza(mese);
    	
    	for (Citta c: citta) {
    		txtResult.appendText(c+"\n");
    	}
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Integer mese = boxMese.getValue();
    	
    	Map <String,Double> umidita = model.getUmiditaMedia(mese);
    	
    	
    	for (String citta: umidita.keySet()) {
    		txtResult.appendText(citta+" "+umidita.get(citta)+"\n");
    	}
    	
    	
    	
    }
    
    
	public void setModel (Model m) {
    	this.model=m;
    	List <Integer> listaMesi = new ArrayList<Integer>();
    	
    	for (int i=1; i<13; i++) {
    		listaMesi.add(i);
    	}
    	
    	boxMese.getItems().addAll(listaMesi);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}

