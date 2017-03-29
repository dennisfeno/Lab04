package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	Model m ;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboCorso;

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnCercaNome;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	Corso j = comboCorso.getValue();
    	String matricola = txtMatricola.getText() ;

    	if (matricola.length()!=6 || !matricola.matches("[0-9]*")){
    		txtResult.appendText("Matricola non valida. \n");
    	}
    	
    	else if (j.getCodice() == "" && matricola.length()==6 && matricola.matches("[0-9]*")) { // punto 4
    		
    		Studente s = m.vediStudente(matricola);
    		
    		if(s==null) {
    			txtResult.appendText("Matricola non esistente. \n");
    		}
    		else {
    			txtResult.appendText("Matricola trovata. \n");
    			txtNome.setText(s.getNome());
    			txtCognome.setText(s.getCognome());
    			
    			List<Corso> c = m.corsiStudente(s) ;
    			
    			if (c.size()>0){
    			
	    			Iterator it = c.iterator();
	    			
	    			while (it.hasNext()){
	    				
	    				Corso co = (Corso) it.next() ;
	    				
	        			txtResult.appendText(String.format("%s \t\t %d crediti \t\t %s \t\t %d periodo\n", co.getCodice(), co.getCrediti(), co.getNome(), co.getPd()));
	    				
	    			}
    			}
    			else{
    				txtResult.appendText("Studente non iscritto ad alcun corso.\n");
    			}
    			
    		}    		
    		
    	}
    	else{ // punto 5
    		Studente s = m.vediStudente(matricola);
	    	if(s!=null){
    			if (m.isIscritto(s, j)){
					txtResult.appendText("Studente iscritto a questo corso.\n");
	    		}
	    		else{
					txtResult.appendText("Studente non iscritto a questo corso.\n");
	    		}
	    	}
	    	else{
				txtResult.appendText("Matricola non trovata.\n");
    		}
    	}
    	
    	

    	
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {

    	Corso j = comboCorso.getValue();
    	
    	if (j.getCodice() == "") {
    		txtResult.appendText("Seleziona un corso. \n");    		
    	}
    	else {
    		
    		List<Studente> s = m.iscrittiCorso(j);

    		if(s.size()>0){
    			txtResult.appendText("Gli studenti iscritti al corso "+ j.toString() + " sono: \n");
    			Iterator it = s.iterator() ;
	    		while(it.hasNext()) {
	    			
	    			Studente k = (Studente) it.next() ; 
	    			txtResult.appendText(k.toString()+" \n");
	    			
	    		}
    		}
    		else
    			txtResult.appendText("Nessun iscritto al corso. \n");

    	}
    	
    }

    @FXML
    void doCercaNome(ActionEvent event) {

    	String matricola = txtMatricola.getText() ;
    	
    	if (matricola.length()!=6 || !matricola.matches("[0-9]*")){
    		txtResult.appendText("Matricola non valida. \n");
    	}
    	else{
    		Studente s = m.vediStudente(matricola);
    		
    		if(s==null) {
    			txtResult.appendText("Matricola non esistente. \n");
    		}
    		else {
    			txtResult.appendText("Matricola trovata. \n");
    			txtNome.setText(s.getNome());
    			txtCognome.setText(s.getCognome());
    		}
    		
    	} 
    	
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	Corso j = comboCorso.getValue();
    	String matricola = txtMatricola.getText() ;

    	if (matricola.length()!=6 || !matricola.matches("[0-9]*")){
    		txtResult.appendText("Matricola non valida. \n");
    	}
    	else if (j.getCodice() != "" && matricola.length()==6 && matricola.matches("[0-9]*")) { // punto 4
    		
    		Studente s = m.vediStudente(matricola);
    	
    		if (s==null){
        		txtResult.appendText("Matricola Non Trovata. \n");
    		}
    		else{
    			boolean res = false;
    			if(!m.isIscritto(s, j))
    				 res = m.inscriviStudenteACorso(s, j);
    			else{
            		txtResult.appendText("Studente già iscritto. \n");
        		}
    			
    			if(res){
    				txtResult.appendText("Studente iscritto. \n");
    			}
    		}
    		
    	}
    	
    	
    }

    @FXML
    void doReset(ActionEvent event) {

    	txtMatricola.setText("");
    	txtNome.setText("");
    	txtCognome.setText("");
    	txtResult.setText("");

    	
    }

    @FXML
    void initialize() {
        assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaNome != null : "fx:id=\"btnCercaNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        
    }

	public void setModel(Model m2) {
		// TODO Auto-generated method stub
		this.m = m2 ; 
		
		
		
        comboCorso.getItems().add(new Corso("",0,"Corsi",0));
        if(comboCorso.getItems().size() > 0)
        	comboCorso.setValue(comboCorso.getItems().get(0));
        
       	comboCorso.getItems().addAll(m.getCorsi());
		
	}
}
