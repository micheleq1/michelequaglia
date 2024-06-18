<%@page import="model.OrdiniDAOimpl"%>
<%@page import="model.OrdiniDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="theme.css">
<title>Acquista</title>
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
    <% session= request.getSession();
    String username= (String)session.getAttribute("name");
    OrdiniDAO ordini=new OrdiniDAOimpl();
    String indirizzo= ordini.getIndirizzoUtenteFromSession(username); %>
    <form method="post" action="acquistocompletato.jsp">
        <label for="carta">Dati Carta:</label>
        <input type="text" name="carta" id="carta" pattern="[0-9]*">
        <label for="indirizzo">Indirizzo:</label>
        <input type="text" name="indirizzo" value="<%=indirizzo %>">
        <button type="submit">Acquista</button>
        </form>
</body>
</html>