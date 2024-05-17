package control;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;

import model.ProdottiDAO;
import model.ProdottiDAOimpl;
import model.Prodotto;

import org.apache.commons.io.FilenameUtils;

@WebServlet("/AggiungiServlet")
public class AggiungiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    RequestDispatcher dispatcher=null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                
                String nome = null;
                String desc = null;
                double prezzo = 0;
                byte[] immagine = null;

                for (FileItem item : items) {
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("nome")) {
                            nome = item.getString();
                        } else if (item.getFieldName().equals("descrizione")) {
                            desc = item.getString();
                        } else if (item.getFieldName().equals("prezzo")) {
                            prezzo = Double.parseDouble(item.getString());
                        }
                    } else {
                        // Verifica che l'elemento sia effettivamente un file
                        if (!item.isFormField()) {
                            // Ottieni il nome del file e l'estensione
                            String fileName = item.getName();
                            String fileExtension = FilenameUtils.getExtension(fileName);

                            // Verifica se il file è un'immagine
                            if (!isImage(fileExtension)) {
                                // Se il file non è un'immagine, imposta un messaggio di errore e reindirizza alla stessa pagina
                                request.setAttribute("aggiunto", false);
                                dispatcher = request.getRequestDispatcher("aggiungiprodotto.jsp");
                                dispatcher.forward(request, response);
                                return; // Termina il metodo qui per evitare l'esecuzione del codice successivo in caso di errore
                            }

                            // Leggi il contenuto del file in un array di byte
                            InputStream inputStream = item.getInputStream();
                            byte[] fileContent = new byte[(int) item.getSize()];
                            inputStream.read(fileContent);
                            inputStream.close();
                            immagine = fileContent;
                        }
                    }
                }

                // Verifica se tutti i campi obbligatori sono stati compilati
                if (nome == null || nome.isEmpty() || desc == null || desc.isEmpty() || prezzo == 0 || immagine == null) {
                    // Se un campo obbligatorio è vuoto, imposta un messaggio di errore e reindirizza alla stessa pagina
                    request.setAttribute("aggiunto", false);
                    dispatcher = request.getRequestDispatcher("aggiungiprodotto.jsp");
                    dispatcher.forward(request, response);
                    return; // Termina il metodo qui per evitare l'esecuzione del codice successivo in caso di errore
                }

                Prodotto prodotto = new Prodotto();
                prodotto.setNome(nome);
                prodotto.setDescrizione(desc);
                prodotto.setPrezzo(prezzo);
                prodotto.setImmagine(immagine);

                ProdottiDAO prodottiDAO = new ProdottiDAOimpl();
                
                // Aggiungi il prodotto al database
                prodottiDAO.addProduct(prodotto);

                // Se l'aggiunta ha avuto successo, imposta l'attributo "aggiunto" a true
                request.setAttribute("aggiunto", true);
                
            } catch (Exception e) {
                // In caso di errore durante l'aggiunta, imposta l'attributo "aggiunto" a false
                request.setAttribute("aggiunto", false);
                e.printStackTrace();
            }
        } else {
            request.setAttribute("aggiunto", false);
        }
        
        // Reindirizza alla pagina JSP
        dispatcher = request.getRequestDispatcher("aggiungiprodotto.jsp");
        dispatcher.forward(request, response);
    }

    // Metodo per verificare se l'estensione del file corrisponde a un formato di immagine
    private boolean isImage(String fileExtension) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp"};
        for (String extension : imageExtensions) {
            if (extension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }
}
