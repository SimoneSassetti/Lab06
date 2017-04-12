package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.bean.CittaMedia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {
	Model model;
	public void setModel(Model model){
		this.model=model;	
	}
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Integer> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		if(boxMese.getValue()==null){
			txtResult.appendText("Selezionare un mese.\n");
			return;
		}
		int mese=boxMese.getValue();
		String output=model.trovaSequenza(mese);
		
		txtResult.appendText(output+"\n");
		
	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		if(boxMese.getValue()==null){
			txtResult.appendText("Selezionare un mese.\n");
			return;
		}
		int mese=boxMese.getValue();
		
		for(CittaMedia c:model.getUmiditaMedia(mese)){
			txtResult.appendText(c.getNome()+" "+c.getUmiditaMedia()+"\n");
		}
	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
		
		boxMese.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
	}

}
