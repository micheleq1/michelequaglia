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

@WebServlet("/ModificaServlet")
@MultipartConfig
public class ModificaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ModificaServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String descrizione = request.getParameter("descrizione");

        ProdottiDAO p = new ProdottiDAOimpl();
        Prodotto prodotto = p.getProductbyId(id);

        Part filePart = request.getPart("immagine");
        byte[] imageBytes = null;

        if (filePart != null && filePart.getSize() > 0) {
            // Carica la nuova immagine
            try (InputStream inputStream = filePart.getInputStream()) {
                imageBytes = inputStream.readAllBytes();
            }
        } else {
            // Mantieni l'immagine esistente
            imageBytes = prodotto.getImmagine();
        }

        prodotto.setNome(nome);
        prodotto.setPrezzo(prezzo);
        prodotto.setDescrizione(descrizione);
        prodotto.setImmagine(imageBytes);

        p.updateProduct(prodotto);

        request.setAttribute("modifica", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("modificaprodotto.jsp");
        dispatcher.forward(request, response);
    }
}

