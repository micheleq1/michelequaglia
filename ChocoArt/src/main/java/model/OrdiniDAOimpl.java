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
	    String sql = "INSERT INTO ordini (id_utente, totale, indirizzo) VALUES (?, ?, ?)";
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, ordine.getIdUtente());
	        stmt.setDouble(2, ordine.getTotale());
	        stmt.setString(3, ordine.getIndirizzo());
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
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
        // Ottieni il nome utente dalla sessione
        

        // Ottieni l'ID utente utilizzando il nome utente
        int idUtente = getIdUtenteFromSession(nomeUtente);

        // Lista per memorizzare gli ordini dell'utente
        List<Ordine> ordiniUtente = new ArrayList<>();

        // Se l'ID utente è valido, esegui la query per ottenere gli ordini dell'utente
        if (idUtente != -1) {
            String sql = "SELECT * FROM ordini WHERE id_utente = ?";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idUtente);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Ordine ordine = new Ordine();
                        ordine.setId(rs.getInt("id"));
                        ordine.setIdUtente(rs.getInt("id_utente"));
                        ordine.setDataOrdine(rs.getTimestamp("data_ordine"));
                        ordine.setTotale(rs.getDouble("totale"));
                        ordine.setIndirizzo(rs.getString("indirizzo"));
                        ordiniUtente.add(ordine);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ordiniUtente;
    }


   
}
