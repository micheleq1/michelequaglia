package model;

import model.Ordine;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

public interface OrdiniDAO {
   
    boolean aggiungiOrdine(Ordine ordine);
    List<Ordine> tuttiOrdini();
    public int getIdUtenteFromSession(String nomeutente);
    public String getIndirizzoUtenteFromSession(String nomeUtente);
    public List<Ordine> getOrdiniUtenteFromSession(String nomeUtente);
   
}
