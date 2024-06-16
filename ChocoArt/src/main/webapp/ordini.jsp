<%@page import="model.Prodotto"%>
<%@page import="model.Ordine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.OrdiniDAOimpl"%>
<%@page import="model.OrdiniDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    
<link rel="stylesheet" type="text/css" href="theme.css"> 
<title>I Miei Ordini</title>
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
<div class="container">
    <div class="orders-container">
        <h2>I Miei Ordini</h2>
        <%
            String nomeUtente = (String) session.getAttribute("name");
            OrdiniDAO ordiniDAO = new OrdiniDAOimpl();
            List<Ordine> ordiniUtente = ordiniDAO.getOrdiniUtenteFromSession(nomeUtente);
            for (Ordine ordine : ordiniUtente) {
        %>
        <div class="order-info">
            <div class="order-details">
                <label>Id ordine:</label>
                <div class="info info-detail"><%= ordine.getId() %></div>
                <label>Data ordine:</label>
                <div class="info info-detail"><%= ordine.getDataOrdine() %></div>
                <label>Indirizzo:</label>
                <div class="info info-detail"><%= ordine.getIndirizzo() %></div>
                <label>Totale:</label>
                <div class="info info-detail"><%= ordine.getTotale() %></div>
            </div>
            <div class="product-list">
                <h3>Prodotti:</h3>
                <ul>
                    <%
                        for (Prodotto prodotto : ordine.getProdotti()) {
                    %>
                    <li>
                        <label>Nome prodotto:</label>
                        <span><%= prodotto.getNome() %></span>
                        <label>Prezzo:</label>
                        <span><%= prodotto.getPrezzo() %></span>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </div>
        </div>
        <%
            }
        %>
        <%if(ordiniUtente.isEmpty()) %>
        <h4>Non hai ancora effettuato ordini</h4>
    </div>
    
</div>
</body>
</html>
