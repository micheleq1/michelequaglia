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

@WebServlet("/AggiungiAiPreferitiServlet")
public class AggiungiAiPreferitiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AggiungiAiPreferitiServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProdottiDAO prodottiDAO = new ProdottiDAOimpl();
        String id = request.getParameter("Id");
        Prodotto prodotto = prodottiDAO.getProductbyId(Integer.parseInt(id));

        ArrayList<Prodotto> preferiti = (ArrayList<Prodotto>) session.getAttribute("preferiti");
        if (preferiti == null) {
            preferiti = new ArrayList<>();
            session.setAttribute("preferiti", preferiti);
        }

        boolean isProductInFavorites = preferiti.stream().anyMatch(p -> p.getId() == prodotto.getId());

        if (!isProductInFavorites) {
            preferiti.add(prodotto);
        } else {
            preferiti.removeIf(p -> p.getId() == prodotto.getId());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
