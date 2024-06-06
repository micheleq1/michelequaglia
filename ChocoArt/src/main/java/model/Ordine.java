package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Ordine {
    private int id;
    private int idUtente;
    private Timestamp dataOrdine;
    private Double totale;
    private String indirizzo;

    // Costruttore vuoto
    public Ordine() {}

    
   

    // Metodi getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public Timestamp getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Timestamp dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    // Metodo toString per la rappresentazione testuale dell'oggetto
    @Override
    public String toString() {
        return "Ordine [id=" + id + ", idUtente=" + idUtente + ", dataOrdine=" + dataOrdine + ", totale=" + totale + "]";
    }




	public String getIndirizzo() {
		return indirizzo;
	}




	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
}
