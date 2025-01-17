package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ProdottiDAOimpl implements ProdottiDAO {
	
	private Connection getConnection() throws SQLException {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC driver non trovato");
        }
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/chocoart", "root", "michelequaglia17");
	}

	@Override
	public Prodotto getProductbyId(int id) {
		 Prodotto product = null;
	        try (Connection conn = getConnection();
	             PreparedStatement ps = conn.prepareStatement("SELECT * FROM prodotti WHERE id = ?")) {
	            ps.setInt(1, id);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                product = new Prodotto();
	                product.setNome(rs.getString("nome"));
	                product.setId(rs.getInt("id"));
	                product.setDescrizione(rs.getString("descrizione"));
	                product.setPrezzo(rs.getDouble("prezzo"));
	                product.setImmagine(rs.getBytes("immagine"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return product;
	}

	@Override
	public List<Prodotto> getAllProducts() {
	    List<Prodotto> productList = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        conn = getConnection();
	        ps = conn.prepareStatement("SELECT * FROM prodotti");
	        rs = ps.executeQuery();
	        
	       while (rs.next()) {
	            int id = rs.getInt("id");
	            String nome = rs.getString("nome");
	            double prezzo = rs.getDouble("prezzo");
	            String descrizione = rs.getString("descrizione");
	            byte[] immagine = rs.getBytes("immagine"); 
	            Prodotto prodotto = new Prodotto();
	            prodotto.setNome(nome);
	            prodotto.setId(id);
	            prodotto.setDescrizione(descrizione);
	            prodotto.setPrezzo(prezzo);
	            prodotto.setImmagine(immagine); 
	            productList.add(prodotto);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	       
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (ps != null) {
	            try {
	                ps.close();
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
	    return productList;
	}

	
	

	@Override
	public void addProduct(Prodotto prodotto) {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    try {
	        conn = getConnection();
	        ps = conn.prepareStatement("INSERT INTO prodotti (nome, prezzo, descrizione, immagine) VALUES (?, ?, ?, ?)");
	        ps.setString(1, prodotto.getNome());
	        ps.setDouble(2, prodotto.getPrezzo());
	        ps.setString(3, prodotto.getDescrizione());
	        
	       
	        String immagineBase64 = Base64.getEncoder().encodeToString(prodotto.getImmagine());
	        
	        
	        ps.setString(4, immagineBase64); 
	        
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (ps != null) {
	            try {
	                ps.close();
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
	}


	@Override
	public void updateProduct(Prodotto prodotto) {
	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement("UPDATE prodotti SET nome = ?, prezzo = ?, descrizione = ?, immagine = ? WHERE id = ?")) {
	        
	        ps.setString(1, prodotto.getNome());
	        ps.setDouble(2, prodotto.getPrezzo());
	        ps.setString(3, prodotto.getDescrizione());
	        ps.setInt(5, prodotto.getId());

	        byte[] immagine = prodotto.getImmagine();
	        if (immagine != null && immagine.length > 0) {
	           
	            ps.setBytes(4, immagine);
	        } else {
	           
	            Prodotto existingProduct = getProductbyId(prodotto.getId());
	            ps.setBytes(4, existingProduct.getImmagine());
	        }

	    
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void deleteProduct(int id) {
		 try (Connection conn = getConnection();
		         PreparedStatement ps = conn.prepareStatement("DELETE FROM prodotti WHERE id = ?")) {
		        ps.setInt(1, id);
		        
		
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

}}
