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

import model.ProdottiDAOimpl;
import model.ProdottiDAO;

import model.Prodotto;

/**
 * Servlet implementation class RimuoviDalCarrelloServlet
 */
@WebServlet("/RimuoviDalCarrelloServlet")
public class RimuoviDalCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RimuoviDalCarrelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = request.getParameter("Id");
        
        
       
        int productIdToRemove = Integer.parseInt(id);
        
   
        ArrayList<Prodotto> cart = (ArrayList<Prodotto>) session.getAttribute("cart");

        
        if (cart != null) {
            
            for (int i = 0; i < cart.size(); i++) {
                Prodotto product = cart.get(i);
                if (product.getId() == productIdToRemove) {
                    cart.remove(i);
                    break;
                }
            }
           
            session.setAttribute("cart", cart);
        }

     
        RequestDispatcher dispatcher = request.getRequestDispatcher("carrello.jsp");
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
