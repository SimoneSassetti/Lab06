package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.CittaMedia;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		String sql="SELECT localita, data, umidita from situazione where MONTH(data)=? AND localita=?";
		List<Rilevamento> lista=new LinkedList<Rilevamento>();
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("localita"),rs.getDate("data"), rs.getInt("umidita"));
				lista.add(r);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {

		return 0.0;
	}

	public List<CittaMedia> getUmiditaMedia(int mese) {
		
		String sql="SELECT localita, AVG(umidita) FROM situazione WHERE MONTH(data)=? group by localita;";
		List<CittaMedia> lista=new LinkedList<CittaMedia>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				CittaMedia c = new CittaMedia(rs.getString("localita"),mese, rs.getFloat("AVG(umidita)"));
				lista.add(c);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}

	public List<Citta> getCitta() {
		
		String sql="SELECT localita from situazione group by localita;";
		List<Citta> lista=new LinkedList<Citta>();
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Citta c = new Citta(rs.getString("localita"));
				lista.add(c);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

}
