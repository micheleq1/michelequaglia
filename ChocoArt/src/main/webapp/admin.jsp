<%@page import="model.Ordine"%>
<%@page import="java.util.List"%>
<%@page import="model.OrdiniDAOimpl"%>
<%@page import="model.OrdiniDAO"%>
<%@page import="model.Prodotto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin</title>
<link rel="stylesheet" type="text/css" href="theme.css">
</head>
<body>
<header>
    <div class="container">
        <h1 class="logo">ChocoArt</h1>
        <nav>
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <% if (session.getAttribute("admin") != null && (Boolean) session.getAttribute("admin")) { %>
                    <li><a href="LogoutServlet">Logout</a></li>
                <% } %>
            </ul>
        </nav>
    </div>
</header>
<div class="filtri">
    <a href="aggiungiprodotto.jsp"><button>AGGIUNGI PRODOTTO</button></a>
    <a href="eliminaprodotto.jsp"><button>ELIMINA PRODOTTO</button></a>
    <a href="modificaprodotto.jsp"><button>MODIFICA PRODOTTO</button></a>
</div>
<div class="search-filters">
    <h2>Filtra Ordini</h2>
    <form action="admin.jsp" method="get">
        <label for="dataInizio">Data Inizio:</label>
        <input type="date" id="dataInizio" name="dataInizio">
        <label for="dataFine">Data Fine:</label>
        <input type="date" id="dataFine" name="dataFine">
        <label for="idUtente">ID Utente:</label>
        <input type="text" id="idUtente" name="idUtente">
        <button type="submit">Cerca</button>
    </form>
</div>

<%
    String dataInizio = request.getParameter("dataInizio");
    String dataFine = request.getParameter("dataFine");
    String idUtente = request.getParameter("idUtente");

    OrdiniDAO ordiniDAO = new OrdiniDAOimpl();
    List<Ordine> ordini;

    if (dataInizio != null && dataFine != null) {
        ordini = ordiniDAO.cercaOrdini(dataInizio, dataFine, idUtente);
    } else {
        ordini = ordiniDAO.tuttiOrdini();
    }

    for (Ordine ordine : ordini) {
%>
    <div class="order-info">
        <div class="order-details">
            <label>Id ordine:</label>
            <div class="info info-detail"><%= ordine.getId() %></div>
            <label>Id cliente:</label>
            <div class="info info-detail"><%= ordine.getIdUtente() %></div>
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
                    <label>Quantità:</label>
                    <span><%= prodotto.getQuantità()%></span>
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
</body>
</html>
