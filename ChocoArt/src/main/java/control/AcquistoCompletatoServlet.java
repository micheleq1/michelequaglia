package control;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Ordine;
import model.OrdiniDAO;
import model.OrdiniDAOimpl;
import model.Prodotto;

@WebServlet("/AcquistoCompletatoServlet")
public class AcquistoCompletatoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AcquistoCompletatoServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        OrdiniDAO aggiungi = new OrdiniDAOimpl();
        Ordine ordine = new Ordine();

        ArrayList<Prodotto> cart = (ArrayList<Prodotto>) session.getAttribute("cart");
        
      
        if (cart != null) {
            
            for (Prodotto product : cart) {
                String quantityStr = request.getParameter("quantity_" + product.getId());
                if (quantityStr != null) {
                    int quantity = Integer.parseInt(quantityStr);
                    product.setQuantità(quantity);
                }
                ordine.getProdotti().add(product);
            }
        }

        
        String totalPriceStr = request.getParameter("totalPrice");
        double totalPrice = 0.0;

        if (totalPriceStr != null && !totalPriceStr.isEmpty()) {
            try {
                totalPrice = Double.parseDouble(totalPriceStr);
            } catch (NumberFormatException e) {
                System.err.println("Errore nel parsing del totale: " + e.getMessage());
            }
        } else {
            System.err.println("Errore: totale non presente nella richiesta o valore non valido.");
        }

        ordine.setTotale(totalPrice);

        int idUtente = aggiungi.getIdUtenteFromSession((String) session.getAttribute("name"));
        ordine.setIdUtente(idUtente);
        String indirizzo= aggiungi.getIndirizzoUtenteFromSession((String)session.getAttribute("name"));
        
        ordine.setIndirizzo(indirizzo);

        aggiungi.aggiungiOrdine(ordine);

        response.sendRedirect("acquistocompletato.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
