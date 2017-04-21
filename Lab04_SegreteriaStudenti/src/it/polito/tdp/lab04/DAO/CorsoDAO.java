package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM `corso`";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso c = new Corso(rs.getString("codins"), 
						Integer.parseInt(rs.getString("crediti")), 
						rs.getString("nome"), 
						Integer.parseInt(rs.getString("pd"))) ;
				
				corsi.add(c);
			}
			
			st.close();
			conn.close();
			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		// TODO
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
	
		final String sql = "SELECT * FROM studente, corso, iscrizione "
				+ "WHERE iscrizione.codins = corso.codins "
				+ "AND iscrizione.matricola = studente.matricola "
				+ "AND iscrizione.codins= ? ";

		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, corso.getCodice());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente s = new Studente(rs.getString("matricola"), 
						rs.getString("nome"), 
						rs.getString("cognome"), 
						rs.getString("CDS")) ;
				
				studenti.add(s);
			}
			st.close();
			conn.close();
			return studenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	
	}

	/*
	 * è iscritto al corso? 
	 */
	public boolean isIscritto(Studente studente, Corso corso) {

		final String sql = "SELECT * FROM `iscrizione` "
				+ "WHERE iscrizione.matricola=? "
				+ "AND iscrizione.codins=?";


		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, studente.getMatricola());
			st.setString(2, corso.getCodice());
			
			ResultSet rs = st.executeQuery();
			

			
			while (rs.next()) {
				st.close();
				conn.close();
				return true; 
			}
			st.close();
			conn.close();
			return false;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	/*
	 * Data una matricola ed il codice insegnamento,
	 * iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		final String sql = "INSERT INTO `iscrizione` (`matricola`, `codins`) VALUES (?, ?);";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, studente.getMatricola()) ; 
			st.setString(2, corso.getCodice() );
			
			int result = st.executeUpdate() ; 
						
			//System.out.println(result);
			
			st.close();
			conn.close();
			
			if (result == 1)
				return true;
			else
				return false; 

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
}
