package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		RequestDispatcher disp=null;
		Connection con=null;
		HttpSession session=request.getSession();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            con =DriverManager.getConnection("jdbc:mysql://localhost:3306/chocoart","root","michelequaglia17");
            PreparedStatement ps=con.prepareStatement("select * from utenti where email=? and password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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

            disp.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
