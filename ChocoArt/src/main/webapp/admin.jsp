<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="theme.css">
</head>
<body>
<header>
        <div class="container">
            <h1 class="logo">ChocoArt</h1>
            <nav>
                <ul>
                    <li><a href="index.jsp">Home</a></li>
                    <%if (session.getAttribute("admin") != null && (Boolean) session.getAttribute("admin")) {%>
                    <li><a href="LogoutServlet">Logout</a></li> <%} %>
                </ul>
            </nav>
        </div>
    </header>
</body>
<div class="filtri">
        <a href="aggiungiprodotto.jsp"><button>AGGIUNGI PRODOTTO</button></a>
        <button>ELIMINA PRODOTTO</button>
        <button>MODIFICA PRODOTTO</button>
    </div>
</html>