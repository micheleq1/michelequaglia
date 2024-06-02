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
import model.Prodotto;

/**
 * Servlet implementation class ModificaServlet
 */
@WebServlet("/ModificaServlet")
public class ModificaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaServlet() {
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
		
		    int id = Integer.parseInt(request.getParameter("id")); // Recupera l'id dal corpo della richiesta POST
		    String nome = request.getParameter("nome");
		    double prezzo = Double.parseDouble(request.getParameter("prezzo"));
		    String descrizione = request.getParameter("descrizione");
		    
		    ProdottiDAO p = new ProdottiDAOimpl();
		    Prodotto prodotto = p.getProductbyId(id);
		    prodotto.setNome(nome);
		    prodotto.setPrezzo(prezzo);
		    prodotto.setDescrizione(descrizione);
		    
		    p.updateProduct(prodotto);
		    request.setAttribute("modifica", true);
		    RequestDispatcher dispatcher=request.getRequestDispatcher("modificaprodotto.jsp");
	        dispatcher.forward(request, response);
		    
		}

	}


