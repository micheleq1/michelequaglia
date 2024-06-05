<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aggiungi Prodotto</title>
    <link rel="stylesheet" type="text/css" href="theme.css">
</head>
<body>
    <header>
        <div class="container">
            <h1 class="logo">ChocoArt</h1>
            <nav>
                <ul>
                    <li><a href="admin.jsp">Annulla</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <%
    if (request.getAttribute("aggiunto") != null && (boolean) request.getAttribute("aggiunto")) {
    %>
    <div class="success-message">
    <p>Prodotto aggiunto con successo</p>
    </div>
    <%
    } else if (request.getAttribute("aggiunto") != null && !(boolean) request.getAttribute("aggiunto")) {
    %>
    <div class="error-message">
        <p>Errore durante l'aggiunta del prodotto</p>
    </div>
    <%
    }
    %>
    <form method="post" action="AggiungiServlet" enctype="multipart/form-data">
        <label for="nome">Nome:</label>
        <input type="text" name="nome" required><br><br>
        <label for="descrizione">Descrizione:</label>
        <input type="text" name="descrizione" required><br><br>
        <label for="prezzo">Prezzo:</label>
        <input type="number" name="prezzo" min="0" step="any">
        <label for="immagine">Immagine:</label>
        <input type="file" name="immagine" accept="image/*"><br><br>
        <button type="submit">Aggiungi</button>
    </form>
</body>
</html>
