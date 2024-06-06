package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PrezzoTotaleServlet
 */
@WebServlet("/PrezzoTotaleServlet")
public class PrezzoTotaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrezzoTotaleServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String totalPrice = request.getParameter("totalPrice");
		HttpSession session = request.getSession();
        if (totalPrice != null) {
            // Imposta il totale nella sessione
            session.setAttribute("totale", totalPrice);
            System.out.println("Totale salvato nella sessione: " + session.getAttribute("totale"));
        } else {
            System.out.println("Parametro totalPrice non trovato nella richiesta.");
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
