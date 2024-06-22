package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProdottiDAO;
import model.ProdottiDAOimpl;
import model.Prodotto;

/**
 * Servlet implementation class RimuoviDaiPreferitiServlet
 */
@WebServlet("/RimuoviDaiPreferitiServlet")
public class RimuoviDaiPreferitiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RimuoviDaiPreferitiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  HttpSession session = request.getSession();
	        ProdottiDAO prodottiDAO = new ProdottiDAOimpl();
	        String id = request.getParameter("Id");
	        Prodotto prodotto = prodottiDAO.getProductbyId(Integer.parseInt(id));
	        

	
	        ArrayList<Prodotto> preferiti = (ArrayList<Prodotto>) session.getAttribute("preferiti");
	        for (int i = 0; i < preferiti.size(); i++) {
                if (preferiti.get(i).getId() == prodotto.getId()) {
                    preferiti.remove(i);
                    break;
                }
            }
	        RequestDispatcher dispatcher = request.getRequestDispatcher("preferiti.jsp");
	        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
