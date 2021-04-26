package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			rs.close();
			st.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		String sql = "SELECT s.localita, s.data, s.umidita "
				+"FROM Situazione s "
				+"where MONTH(Data) = ? AND localita=? "
				+"group by s.localita, s.data, s.umidita";
		
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();
			
			
			while (rs.next()) {
				
				Rilevamento r = new Rilevamento (rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}
			
			conn.close();
			rs.close();
			st.close();
			
			
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

		return rilevamenti;
	}

	public Map<String,Double> getAvgRilevamentiLocalitaMese(int mese) {
		
		String sql = "SELECT s.localita, AVG (umidita) AS umiditaMedia "
				+"FROM Situazione s "
				+"where MONTH(Data) = ? "
				+"group by s.localita";
		
		Map<String,Double> umidita = new HashMap<String,Double>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();
			
		
			
			while (rs.next()) {
				
				umidita.put(rs.getString("s.localita"),rs.getDouble("umiditaMedia"));
				
				
			}
			
			conn.close();
			rs.close();
			st.close();
			
			
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return umidita;
	}
	
	public List<Citta> getAllCitta () {
		
		String sql = "SELECT DISTINCT Localita From situazione";
		
		List<Citta> listaCitta = new ArrayList<Citta> ();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Citta c = new Citta(rs.getString("Localita"));
				listaCitta.add(c);
			}
			conn.close();
			rs.close();
			st.close();
			
			
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
			return listaCitta;
		}


}
