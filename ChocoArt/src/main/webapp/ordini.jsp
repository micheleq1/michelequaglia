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
    
    <%String nomeUtente = (String) session.getAttribute("name");
    OrdiniDAO ordini=new OrdiniDAOimpl();
   List<Ordine> ordiniUtente=ordini.getOrdiniUtenteFromSession(nomeUtente);
   for(Ordine ordine:ordiniUtente){
	   %>
	  <div class="order-info">
    <label>Id ordine:</label>
    <div class="info info-detail"><%= ordine.getId() %></div>
    <label>Data ordine:</label>
    <div class="info info-detail"><%= ordine.getDataOrdine() %></div>
    <label>Indirizzo:</label>
    <div class="info info-detail"><%= ordine.getIndirizzo() %></div>
    <label>Totale:</label>
    <div class="info info-detail"><%= ordine.getTotale() %></div>
</div>
   <% }%>
</body>
</html>