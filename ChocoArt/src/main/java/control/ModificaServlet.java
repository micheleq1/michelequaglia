package control;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.ProdottiDAO;
import model.ProdottiDAOimpl;
import model.Prodotto;

/**
 * Servlet implementation class ModificaServlet
 */
@WebServlet("/ModificaServlet")
@MultipartConfig
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
        // Gestisci la richiesta GET se necessario
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

        Part filePart = request.getPart("immagine");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String fileExtension = getFileExtension(fileName);
            if (isImage(fileExtension)) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    byte[] imageBytes = inputStream.readAllBytes();
                    prodotto.setImmagine(imageBytes);
                }
            } else {
                request.setAttribute("modifica", false);
                request.setAttribute("errore", "Il file caricato non è un'immagine. Si prega di caricare solo immagini.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("modificaprodotto.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        p.updateProduct(prodotto);
        request.setAttribute("modifica", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("modificaprodotto.jsp");
        dispatcher.forward(request, response);
    }

    private boolean isImage(String fileExtension) {
        List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");
        return imageExtensions.contains(fileExtension.toLowerCase());
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf(".") > 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}
