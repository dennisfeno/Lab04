package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {



	/*
	 * Data una matricola ottengo lo studente
	 */
	public Studente getStudenteMat(String matricola) {

		final String sql = "SELECT * FROM `studente` WHERE `matricola`=?";
		Studente s = null ; 
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			

			int matr = Integer.parseInt(matricola);
			
			st.setLong(1, matr);
			
			ResultSet rs = st.executeQuery();

			
			while (rs.next()) {

						s = new Studente(rs.getString("matricola"), 
						rs.getString("nome"), 
						rs.getString("cognome"), 
						rs.getString("CDS")) ;
				
			}

			
			if (s != null ) return s ;
			else return null ;

			
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	/*
	 * Data una matricola ottengo tutti i corsi in cui è iscritto
	 */
	
	public List<Corso> corsiStudente(Studente s){
		
		final String sql = "SELECT * FROM `corso`,iscrizione WHERE corso.codins = iscrizione.codins AND iscrizione.matricola = ?";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, s.getMatricola());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso c = new Corso(rs.getString("codins"), 
						Integer.parseInt(rs.getString("crediti")), 
						rs.getString("nome"), 
						Integer.parseInt(rs.getString("pd"))) ;
				
				corsi.add(c);
			}

			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
}
