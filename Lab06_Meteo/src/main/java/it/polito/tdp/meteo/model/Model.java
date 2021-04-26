package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO meteoDao;
	private List<Citta> city; 
	private List<Citta> sequenzaMigliore;
	

	public Model() {
		meteoDao = new MeteoDAO ();
		city=meteoDao.getAllCitta();
	}

	// of course you can change the String output with what you think works best
	public Map<String,Double> getUmiditaMedia(int mese) {
		return meteoDao.getAvgRilevamentiLocalitaMese(mese);
	}
	
	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {
		
		List<Citta> parziale = new ArrayList<>();
		sequenzaMigliore = null;

		for (Citta c: city)
			c.setRilevamenti(meteoDao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		
		cerca(parziale,0);
		
		return sequenzaMigliore;
	}
	
	private void cerca(List<Citta> parziale, int livello) {
		
		if (livello==NUMERO_GIORNI_TOTALI) {
			//caso TERMINALE
			Double costo = calcolaCosto(parziale);
			if (sequenzaMigliore==null || costo<calcolaCosto(sequenzaMigliore)) {
				//inizializzo la sequenza migliore con la prima sequenza ottenuta, il cui costo verra' confrontato
				// con quello delle sequenze successive
				sequenzaMigliore=new ArrayList<>(parziale);
			}
		}
		else {
			//caso INTERMEDIO
				for (Citta prova: city) {
					if(aggiungi(prova, parziale)) {
						parziale.add(prova);
						cerca(parziale, livello+1);
						parziale.remove(parziale.size()-1);
					}
				}
			}
			
			
		}
			
	private boolean aggiungi(Citta prova, List<Citta> parziale) {
		
		int count=0;
		for (Citta presente:parziale) {
			if (presente.equals(prova))
				count++;
		}
		if (count>=NUMERO_GIORNI_CITTA_MAX)
				return false;
		
		
		if (parziale.size()==0) {// come prima scelta posso mettere qualsiasi città
			return true;
		}
		if (parziale.size()==1 || parziale.size()==2) {
			if (!(parziale.get(parziale.size()-1).equals(prova)))
				return false;
		}
		
		if (parziale.get(parziale.size()-1).equals(prova)) {
			return true; 
		}
		
		// cambiando città, devo assicurarmi che i tre giorni precedenti il tecnico è rimasto in un'altra città
		if (parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) 
			&& parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3))) {
				return true;
		}
		
		return false;
	}

	private Double calcolaCosto(List<Citta> parziale) {
		
		Double costo= 0.0;
		int count=0;
		
		for (int giorno =1; giorno<=NUMERO_GIORNI_TOTALI; giorno++) {
			
			Citta c= parziale.get(giorno-1);
			
			double umidita = c.getRilevamenti().get(giorno-1).getUmidita();
			costo+=umidita;
		}
		
		for (int giorno=2; giorno<=NUMERO_GIORNI_TOTALI;giorno++) {
			if (!parziale.get(giorno-1).equals(parziale.get(giorno-2)))
				count++;
		}
		
		costo += (count*COST);
		
		return costo;
	}
	

}
