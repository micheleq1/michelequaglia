package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProdottiDAO;
import model.ProdottiDAOimpl;

/**
 * Servlet implementation class EliminaServlet
 */
@WebServlet("/EliminaServlet")
public class EliminaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProdottiDAO p=new ProdottiDAOimpl();
		int id = Integer.parseInt(request.getParameter("id"));
		p.deleteProduct(id);
		request.setAttribute("elimina", true);
	    RequestDispatcher dispatcher=request.getRequestDispatcher("eliminaprodotto.jsp");
        dispatcher.forward(request, response);
	}

}
