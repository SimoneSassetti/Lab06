package it.polito.tdp.meteo;

import java.util.*;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.CittaMedia;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 50;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private double punteggioTemp;
	List<Citta> listaCitta;
	List<Rilevamento> listaRilevamenti;
	List<SimpleCity> completa = new ArrayList<SimpleCity>();
	int giorno;

	public Model() {
		punteggioTemp = 10000000.0;
	}

	public List<CittaMedia> getUmiditaMedia(int mese) {
		MeteoDAO dao = new MeteoDAO();
		return dao.getUmiditaMedia(mese);
	}

	public String trovaSequenza(int mese) {
		MeteoDAO dao = new MeteoDAO();
		listaCitta = dao.getCitta();
		for (Citta c : listaCitta) {
			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		}
		List<SimpleCity> parziale = new ArrayList<SimpleCity>();
		giorno = 0;
		recursive(parziale, giorno);
		
		String temp="";
		for(SimpleCity s: completa){
			temp+=""+s.getNome()+" - ";
		}
		
		return temp;
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {
		SimpleCity temp=soluzioneCandidata.get(0);
		double score = temp.getCosto();
		for(SimpleCity s: soluzioneCandidata){
			if(!temp.equals(s)){
				score+=100.0+COST*s.getCosto();
			}
			else{
				score+=COST*s.getCosto();
			}
			temp=s;
		}
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {
		if(parziale.size()>0){
			SimpleCity citta = parziale.get(0);
			int i = 0;
				for(SimpleCity s : parziale) {
					if (citta.equals(s)) {
						i++;
					} else {
						if (i < 3)
							return false;
						i = 1;
						citta = s;
					}
				}
				return true;
		}
		return true;
	}

	private void recursive(List<SimpleCity> parziale, int giorno) {

		if (giorno == 15) {
			if (punteggioSoluzione(parziale) < punteggioTemp && this.soluzioneCompleta(parziale) && this.controlloCittaFinali(parziale)) {
				punteggioTemp = punteggioSoluzione(parziale);
				completa.clear();
				completa.addAll(parziale);
				return;
			}
		}
		for (Citta c : listaCitta) {
			if (c.getCounter()<6 && this.controllaParziale(parziale)) {
				SimpleCity s = new SimpleCity(c.getNome(), c.getRilevamenti().get(giorno).getUmidita());
				parziale.add(s);
				c.increaseCounter();
				recursive(parziale, giorno + 1);
				parziale.remove(giorno);
				c.decreseCounter();
			}

		}

	}

	private boolean controlloCittaFinali(List<SimpleCity> parziale) {
		if(parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) && parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-3)))
			return true;

		return false;
	}

	private boolean soluzioneCompleta(List<SimpleCity> parziale) {
		for (Citta c : listaCitta) {
			SimpleCity s=new SimpleCity(c.getNome());
			if (!parziale.contains(s))
				return false;
		}
		return true;
	}

}
