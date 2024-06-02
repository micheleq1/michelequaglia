package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Prodotto;

/**
 * Servlet implementation class AcquistoCompletatoServlet
 */
@WebServlet("/AcquistoCompletatoServlet")
public class AcquistoCompletatoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcquistoCompletatoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String nome = (String) session.getAttribute("name");
        ArrayList<Prodotto> cart = (ArrayList<Prodotto>) session.getAttribute("cart");

        if (nome != null && cart != null && !cart.isEmpty()) {
            // Calcola il totale dell'ordine
            double totale = 0;
            for (Prodotto prodotto : cart) {
                // Ottieni il valore di "quantity" dalla query string dell'URL
                String quantityParam = request.getParameter("quantity_" + prodotto.getId());
                // Verifica se il parametro è stato fornito e se è un numero valido
                if (quantityParam != null && !quantityParam.isEmpty()) {
                    int quantity = Integer.parseInt(quantityParam);
                    totale += prodotto.getPrezzo() * quantity;
                }
            }
            System.out.println("Totale: " + totale);
        }
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
