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
<title>Elimina Prodotto</title>
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
    if (request.getAttribute("elimina") != null && (boolean) request.getAttribute("elimina")) {
    %>
    <div class="success-message">
    <p>Prodotto eliminato con successo</p>
    </div>
    <%
    } else if (request.getAttribute("elimina") != null && !(boolean) request.getAttribute("elimina")) {
    %>
     <div class="error-message">
        <p>Errore durante l'eliminazione del prodotto</p>
    </div>
    <%
    }
    %>
</body>

<div class="product-container">
    <% 
        ProdottiDAO prodottiDAO = new ProdottiDAOimpl();
        List<Prodotto> prodotti = prodottiDAO.getAllProducts();
        for (Prodotto prodotto : prodotti) {
    %>
        <div class="product">
        <form action="EliminaServlet?id=<%=prodotto.getId() %>" method="post">
    <img src="data:image/jpg;base64, <%= new String(prodotto.getImmagine()) %>" alt="<%= prodotto.getNome() %>">
    <h2><%= prodotto.getNome() %></h2>
    <label class="price"><%= prodotto.getPrezzo() %> euro</label>
    <button type="submit">Elimina</button>
    </form>
  
</div>
        
    <% } %>
    </div>
</html>