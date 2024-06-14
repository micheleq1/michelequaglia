package control;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        RequestDispatcher disp = null;
        Connection con = null;
        HttpSession session = request.getSession();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chocoart", "root", "michelequaglia17");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM utenti WHERE email=?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (checkPassword(password, storedHashedPassword)) {
                    String ruolo = rs.getString("ruolo");
                    if (ruolo != null && ruolo.equals("admin")) {
                        session.setAttribute("admin", true);
                        disp = request.getRequestDispatcher("admin.jsp");
                    } else {
                        session.setAttribute("admin", false);
                        session.setAttribute("name", rs.getString("username"));
                        disp = request.getRequestDispatcher("index.jsp");
                    }
                    request.setAttribute("status", "success");
                } else {
                    request.setAttribute("status", "failed");
                    disp = request.getRequestDispatcher("login.jsp");
                }
            } else {
                request.setAttribute("status", "failed");
                disp = request.getRequestDispatcher("login.jsp");
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

    private boolean checkPassword(String password, String storedHashedPassword) {
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(storedHashedPassword);
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
}

