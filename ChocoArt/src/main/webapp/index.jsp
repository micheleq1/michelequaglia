<%@page import="java.util.Base64"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ProdottiDAO" %>
<%@ page import="model.ProdottiDAOimpl" %>
<%@ page import="model.Prodotto" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ChocoArt</title>
    <link rel="stylesheet" type="text/css" href="theme.css">
</head>
<body>
    <header>
        <div class="container">
            <h1 class="logo">ChocoArt</h1>
            <nav>
                <ul>
                    <% if(session.getAttribute("name") == null) { %> 
                        <li><a href="login.jsp">Login</a></li>
                    <% } else { %>
                        <li><a href="LogoutServlet">Logout</a></li>
                        <li style="color:#D2B48C; font-weight:bold">Ciao <%=session.getAttribute("name") %></li>
                        <li><a href="#">Preferiti</a></li>
                   <% } 
                    ArrayList<Prodotto> cart = (ArrayList<Prodotto>) session.getAttribute("cart");
                    int elementi = 0;
                    if(cart != null) {
                        elementi = cart.size();
                    } %>
                    <li><a href="carrello.jsp">Carrello <% if (elementi > 0) { %><span><%= elementi %></span><% } %></a></li>
                </ul>
            </nav>
        </div>
    </header>
    <div class="filtri">
        <input type="text" placeholder="Cerca...">
        <select>
            <option value="">Filtra per...</option>
            <option value="pasqua">Prezzo Crescente</option>
            <option value="sanvalentino">Prezzo Decrescente</option>
        </select>
        <button>OFFERTE PASQUA</button>
        <button>OFFERTE S.VALENTINO</button>
    </div>
    <div class="product-container">
    <% 
        ProdottiDAO prodottiDAO = new ProdottiDAOimpl();
        List<Prodotto> prodotti = prodottiDAO.getAllProducts();
        for (Prodotto prodotto : prodotti) {
    %>
        <div class="product">
    <img src="data:image/jpg;base64, <%= new String(prodotto.getImmagine()) %>" alt="<%= prodotto.getNome() %>">
    <h2><%= prodotto.getNome() %></h2>
    <label class="price"><%= prodotto.getPrezzo() %> euro</label>
    <a href="AggiungiAlCarrelloServlet?Id=<%= prodotto.getId() %>">Aggiungi al carrello</a>
    <!-- Aggiungi qui altri dettagli del prodotto o pulsanti -->
</div>
        
    <% } %>
    </div>
</body>
</html>
