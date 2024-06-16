<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.Prodotto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="theme.css">
<title>Preferiti</title>
</head>
<body>
<header>
    <div class="container">
        <h1 class="logo">ChocoArt</h1>
        <nav>
            <ul>
                <li><a href="index.jsp">Home</a></li>
            </ul>
        </nav>
    </div>
</header>
<div class="product-container">
    <%
        ArrayList<Prodotto> preferiti = (ArrayList<Prodotto>) session.getAttribute("preferiti");
        if (preferiti != null && !preferiti.isEmpty()) {
            for (Prodotto prodotto : preferiti) {
    %>
                <div class="product">
                    <img src="data:image/jpg;base64, <%= new String(prodotto.getImmagine()) %>" alt="<%= prodotto.getNome() %>">
                    <div class="preferiti">
      <a href="RimuoviDaiPreferitiServlet?Id=<%= prodotto.getId() %>" data-id="<%= prodotto.getId() %>">Rimuovi</a>
      
    </div>
                    <h2><%= prodotto.getNome() %></h2>
                    <label class="price"><%= prodotto.getPrezzo() %> euro</label>
                    <a href="AggiungiAlCarrelloServlet?Id=<%= prodotto.getId() %>">Aggiungi al carrello</a>
                </div>
    <%
            }
        } else {
    %>
            <p>Nessun prodotto nei preferiti.</p>
    <%
        }
    %>
</div>
</body>
</html>
