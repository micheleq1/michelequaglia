package control;

import java.io.IOException;
import java.io.InputStream;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String descrizione = request.getParameter("descrizione");

        ProdottiDAO p = new ProdottiDAOimpl();
        Prodotto prodotto = p.getProductbyId(id);

        Part filePart = request.getPart("immagine");
        byte[] imageBytes = null;

        try {
            if (filePart != null && filePart.getSize() > 0) {
              
                String fileName = extractFileName(filePart);
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if (!isValidImageExtension(fileExtension)) {
                    request.setAttribute("modifica", false);
                    request.setAttribute("message", "Il file caricato non è un'immagine valida.");
                    forwardToPage("modificaprodotto.jsp", request, response);
                    return;
                }

              
                try (InputStream inputStream = filePart.getInputStream()) {
                    imageBytes = inputStream.readAllBytes();
                } catch (IOException e) {
                    request.setAttribute("modifica", false);
                    request.setAttribute("message", "Errore durante il caricamento dell'immagine.");
                    forwardToPage("modificaprodotto.jsp", request, response);
                    return;
                }
            } else {
              
                imageBytes = prodotto.getImmagine();
            }

   
            prodotto.setNome(nome);
            prodotto.setPrezzo(prezzo);
            prodotto.setDescrizione(descrizione);
            prodotto.setImmagine(imageBytes);

            p.updateProduct(prodotto);

            request.setAttribute("modifica", true);
            forwardToPage("modificaprodotto.jsp", request, response);

        } catch (Exception e) {
            request.setAttribute("modifica", false);
            request.setAttribute("message", "Errore durante l'aggiornamento del prodotto.");
            forwardToPage("modificaprodotto.jsp", request, response);
        }
    }

  
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }


    private boolean isValidImageExtension(String fileExtension) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp"};
        for (String extension : imageExtensions) {
            if (extension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }

  
    private void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
