package model;

import model.Ordine;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class OrdiniDAOimpl implements OrdiniDAO {
	private Connection getConnection() throws SQLException {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC driver non trovato");
        }
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/chocoart", "root", "michelequaglia17");
	}

    // Costruttore che riceve una connessione
    

	@Override
	
	public boolean aggiungiOrdine(Ordine ordine) {
	    String sqlOrdine = "INSERT INTO ordini (id_utente, totale, indirizzo) VALUES (?, ?, ?)";
	    String sqlOrdineProdotti = "INSERT INTO ordine_prodotti (id_ordine, id_prodotto, prezzo_prodotto, quantità_prodotto) VALUES (?, ?, ?,?)";

	    try (Connection conn = getConnection();
	         PreparedStatement stmtOrdine = conn.prepareStatement(sqlOrdine, PreparedStatement.RETURN_GENERATED_KEYS)) {

	        // Inserisci l'ordine
	        stmtOrdine.setInt(1, ordine.getIdUtente());
	        stmtOrdine.setDouble(2, ordine.getTotale());
	        stmtOrdine.setString(3, ordine.getIndirizzo());
	        
	        int rowsAffected = stmtOrdine.executeUpdate();

	        if (rowsAffected > 0) {
	            // Ottieni l'ID dell'ordine generato
	            try (ResultSet generatedKeys = stmtOrdine.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    long ordineId = generatedKeys.getLong(1);

	                    // Inserisci i prodotti dell'ordine
	                    try (PreparedStatement stmtOrdineProdotti = conn.prepareStatement(sqlOrdineProdotti)) {
	                        for (Prodotto p:ordine.getProdotti()) {
	                            stmtOrdineProdotti.setLong(1, ordineId);
	                            stmtOrdineProdotti.setInt(2, p.getId());
	                            stmtOrdineProdotti.setDouble(3, p.getPrezzo());
	                            stmtOrdineProdotti.setInt(4, p.getQuantità());
	                            stmtOrdineProdotti.addBatch();
	                        }
	                        stmtOrdineProdotti.executeBatch();
	                    }
	                    return true;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}



    @Override
    public List<Ordine> tuttiOrdini() {
        List<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT * FROM ordini";
        try (Connection conn=getConnection();
        	PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setId(rs.getInt("id"));
                ordine.setIdUtente(rs.getInt("id_utente"));
                ordine.setDataOrdine(rs.getTimestamp("data_ordine"));
                ordine.setTotale(rs.getDouble("totale"));
                ordine.setIndirizzo(rs.getString("indirizzo"));
                ordini.add(ordine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordini;
    }
    @Override
    public int getIdUtenteFromSession(String nomeUtente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idUtente = -1; // Valore di default nel caso in cui non sia stato trovato l'ID dell'utente

        try {
            conn = getConnection();
            String query = "SELECT id FROM utenti WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nomeUtente);
            rs = stmt.executeQuery();

            if (rs.next()) {
                idUtente = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiudi tutte le risorse
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        

        return idUtente;
    }
    @Override
    public String getIndirizzoUtenteFromSession(String nomeUtente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String indirizzo = null; // Valore di default nel caso in cui non sia stato trovato l'ID dell'utente

        try {
            conn = getConnection();
            String query = "SELECT indirizzo FROM utenti WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nomeUtente);
            rs = stmt.executeQuery();

            if (rs.next()) {
                indirizzo = rs.getString("indirizzo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiudi tutte le risorse
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        

        return indirizzo;
    }
    
    @Override
    public List<Ordine> getOrdiniUtenteFromSession(String nomeUtente) {
        // Ottieni l'ID utente utilizzando il nome utente
        int idUtente = getIdUtenteFromSession(nomeUtente);

        // Lista per memorizzare gli ordini dell'utente
        List<Ordine> ordiniUtente = new ArrayList<>();

        // Se l'ID utente è valido, esegui la query per ottenere gli ordini dell'utente
        if (idUtente != -1) {
            String sql = "SELECT o.id AS id_ordine, o.id_utente, o.data_ordine, op.quantità_prodotto, o.totale, o.indirizzo, " +
                         "p.id AS id_prodotto, p.nome, op.prezzo_prodotto AS prezzo_prodotto " +
                         "FROM ordine_prodotti op " +
                         "JOIN ordini o ON op.id_ordine = o.id " +
                         "JOIN prodotti p ON op.id_prodotto = p.id " +
                         "WHERE o.id_utente = ?";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idUtente);
                try (ResultSet rs = stmt.executeQuery()) {
                    Ordine ordineCorrente = null;
                    int ultimoIdOrdine = -1;
                    while (rs.next()) {
                        int idOrdine = rs.getInt("id_ordine");
                        if (idOrdine != ultimoIdOrdine) {
                            if (ordineCorrente != null) {
                                ordiniUtente.add(ordineCorrente);
                            }
                            ordineCorrente = new Ordine();
                            ordineCorrente.setId(idOrdine);
                            ordineCorrente.setIdUtente(rs.getInt("id_utente"));
                            ordineCorrente.setDataOrdine(rs.getTimestamp("data_ordine"));
                            ordineCorrente.setTotale(rs.getDouble("totale"));
                            ordineCorrente.setIndirizzo(rs.getString("indirizzo"));
                            ultimoIdOrdine = idOrdine;
                        }
                        Prodotto prodotto = new Prodotto();
                        prodotto.setId(rs.getInt("id_prodotto"));
                        prodotto.setNome(rs.getString("nome"));
                        prodotto.setPrezzo(rs.getDouble("prezzo_prodotto"));
                        prodotto.setQuantità(rs.getInt("op.quantità_prodotto"));
                        ordineCorrente.getProdotti().add(prodotto);
                    }
                    if (ordineCorrente != null) {
                        ordiniUtente.add(ordineCorrente);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gestione dell'eccezione (registrazione, notifica, fallback)
            }
        }
        return ordiniUtente;
    }



   
}
