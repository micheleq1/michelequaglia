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
       HttpSession session=request.getSession();
           
            OrdiniDAO aggiungi=new OrdiniDAOimpl();
            Ordine ordine=new Ordine();
            ordine.setTotale(Double.parseDouble((String)session.getAttribute("totale")));
            int idUtente = aggiungi.getIdUtenteFromSession((String) session.getAttribute("name"));
           
            ordine.setIdUtente(idUtente);
            aggiungi.aggiungiOrdine(ordine);
        }
    


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
