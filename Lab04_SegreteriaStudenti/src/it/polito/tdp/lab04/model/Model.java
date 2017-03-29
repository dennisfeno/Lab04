package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	CorsoDAO corsoDao = new CorsoDAO() ;
	StudenteDAO studenteDao = new StudenteDAO() ; 
	
	public List<Corso> getCorsi(){
		
		List<Corso> corsi = new LinkedList<Corso>();

		corsi = corsoDao.getTuttiICorsi() ; 
				
		if (corsi != null ) 
			return corsi ;
		
		return null; 
		
	}
	
	/*
	 * Dato una matricola ottengo lo studente
	 */
	
	public Studente vediStudente (String matricola) {
		Studente s = studenteDao.getStudenteMat(matricola);
		
		if (s != null ) return s ;
		return null ; 
		
	}
	
	/*
	 * Dato un cod.corso  ottengo l'elenco degli studente
	 */
	
	public List<Studente> iscrittiCorso(Corso j){
		
		List<Studente> s = corsoDao.getStudentiIscrittiAlCorso(j);

		if (s != null ) return s ;
		return null ; 
		
	}
	
	public List<Corso> corsiStudente(Studente s){
		
		List<Corso> corsi = new LinkedList<Corso>();

		corsi = studenteDao.corsiStudente(s); 
				
		if (corsi != null ) 
			return corsi ;
		
		return null; 
		
	}
	
	public boolean isIscritto(Studente studente, Corso corso) {
	
		return corsoDao.isIscritto(studente, corso);
		
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {

		return corsoDao.inscriviStudenteACorso(studente, corso);
		
	}
	
}
