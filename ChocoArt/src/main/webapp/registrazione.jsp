<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registrati</title>
<link rel="stylesheet" type="text/css" href="theme.css">
</head>
<body>
<header>
    <div class="container">
        <h1 class="logo">ChocoArt</h1>
        <nav>
            <ul>
            	<li><a href="login.jsp">Login</a></li>
                <li><a href="index.jsp">Home</a></li>
                
                
            </ul>
        </nav>
    </div>
    
</header>
<%String status=(String)request.getAttribute("status");
if(status!=null){
if(status.equals("email_exists")){
%>
<div class="error-message">
<p>email o username gia registrati, riprova o accedi</p>
</div>
<%}
if(status.equals("invalid_email")){ %>
<div class="error-message">
<p>Inserisci una email del tipo: prova@example.it</p>
</div> 
<%}
if(status.equals("success")){%>
<p style="background-color: #DFF0D8; color: #3C763D; padding: 10px; border-radius: 4px;">Registrazione effettuata con successo</p>
<%}} %>
  <form method="post" action="RegistrationServlet">
        <label for="username">Username:</label>
        <input type="text" name="username" required><br><br>
        <label for="email">Email:</label>
        <input type="text" name="email" required><br><br>
        <label for="password">Password:</label>
        <input type="password" name="password" required><br><br>
        <label for="indirizzo">Indirizzo:</label>
        <input type="text" name="indirizzo" required><br><br>
        <button type="submit">Registrati</button>
        </form>
</body>
</html>