<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<%@ page import="model.ProdottiDAO" %>
<%@ page import="model.ProdottiDAOimpl" %>
<%@ page import="model.Prodotto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" type="text/css" href="theme.css">
<title>Modifica Prodotto</title>
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
     <div class="product-container">
    <% 
        ProdottiDAO prodottiDAO = new ProdottiDAOimpl();
        List<Prodotto> prodotti = prodottiDAO.getAllProducts();
        for (Prodotto prodotto : prodotti) {
    %>
    
    
    <%
    if (request.getAttribute("modifica") != null && (boolean) request.getAttribute("modifica")) {
    %>
    <p style="background-color: #DFF0D8; color: #3C763D; padding: 10px; border-radius: 4px;">Prodotto modificato con successo</p>
    <%
    } else if (request.getAttribute("modifica") != null && !(boolean) request.getAttribute("modifica")) {
    %>
     <div class="error-message">
        <p>Errore durante l'aggiunta del prodotto</p>
    </div>
    <%
    }
    %>
        <div class="product">
        <form action="ModificaServlet?id=<%=prodotto.getId() %>" method="post">
    <img src="data:image/jpg;base64, <%= new String(prodotto.getImmagine()) %>" alt="<%= prodotto.getNome() %>">
    <input type="text" name="nome" value="<%=prodotto.getNome() %>" required><br><br>
    <input type="text" name="prezzo" value="<%=prodotto.getPrezzo() %>" required><br><br>
    <input type="text" name="descrizione" value="<%=prodotto.getDescrizione() %>" required><br><br>
    <button type="submit">Modifica</button>
    </form>
</div>
        <!-- MANCA POTER MODIFICARE LA FOTO --> 
    <% } %>
    </div>
</body>
</html>