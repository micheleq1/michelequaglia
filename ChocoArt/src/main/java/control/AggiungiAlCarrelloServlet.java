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
 * Servlet implementation class AggiungiAlCarrelloServlet
 */
@WebServlet("/AggiungiAlCarrelloServlet")
public class AggiungiAlCarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiAlCarrelloServlet() {
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

      
        ArrayList<Prodotto> cart = (ArrayList<Prodotto>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        
        boolean isProductInCart = false;
        for (Prodotto item : cart) {
            if (item.getId() == prodotto.getId()) {
                isProductInCart = true;
                break;
            }
        }

       
        if (!isProductInCart) {
            cart.add(prodotto);
        }

        
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

   
}
