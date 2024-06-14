package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.Driver;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    String indirizzo = request.getParameter("indirizzo");
	    RequestDispatcher disp = request.getRequestDispatcher("registrazione.jsp");
	    Connection con = null;
	    try {
	        if (email.contains("@")) {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chocoart", "root", "michelequaglia17");
	            if (isEmailUnique(con, email) && isUsernameUnique(con, username)) {
	                String hashedPassword = hashPassword(password); // Hash della password
	                PreparedStatement ps = con.prepareStatement("insert into utenti(username,email,password,indirizzo) values(?,?,?,?)");
	                ps.setString(1, username);
	                ps.setString(2, email);
	                ps.setString(3, hashedPassword); // Inserisce la password hashata
	                ps.setString(4, indirizzo);

	                int rowcount = ps.executeUpdate();
	                disp = request.getRequestDispatcher("registrazione.jsp");
	                if (rowcount > 0)
	                    request.setAttribute("status", "success");
	                else {
	                    request.setAttribute("status", "failed");
	                }
	            } else {
	                request.setAttribute("status", "email_exists");
	            }
	        } else {
	            request.setAttribute("status", "invalid_email");
	        }
	        disp.forward(request, response);

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (con != null)
	                con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	private String hashPassword(String password) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hashedBytes = md.digest(password.getBytes());

	        // Converte i byte in una rappresentazione esadecimale
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashedBytes) {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	 private boolean isEmailUnique(Connection con, String email) throws SQLException {
		    // Query per controllare se l'email esiste già nel database
		    String query = "SELECT COUNT(*) FROM utenti WHERE email = ?";
		    PreparedStatement ps = con.prepareStatement(query);
		    ps.setString(1, email);
		    ResultSet rs = ps.executeQuery();
		    rs.next();
		    int count = rs.getInt(1);
		    return count == 0; // Restituisce true se l'email è univoca, false altrimenti
		}
	 private boolean isUsernameUnique(Connection con, String username) throws SQLException {
		    // Query per controllare se lo username esiste già nel database
		    String query = "SELECT COUNT(*) FROM utenti WHERE username = ?";
		    PreparedStatement ps = con.prepareStatement(query);
		    ps.setString(1, username);
		    ResultSet rs = ps.executeQuery();
		    rs.next();
		    int count = rs.getInt(1);
		    return count == 0; // Restituisce true se lo username è univoco, false altrimenti
		}


}
