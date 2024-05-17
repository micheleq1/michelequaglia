<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="theme.css">
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
<%String status=(String)request.getAttribute("status");
if(status!=null)
if(status.equals("failed")){
%>
<div class="error-message">
<p>Email o password errata</p>
</div>
<%} %>
    <form method="post" action="LoginServlet">
        <label for="email">Email:</label>
        <input type="text" name="email" required><br><br>
        <label for="password">Password:</label>
        <input type="password" name="password" required><br><br>
        <button type="submit">Login</button>
        <label style="margin-left:auto; margin-top:20px;">Non hai un account?</label>
         <a href="registrazione.jsp" style="color: black;">Registrati</a>
    </form>

</body>
</html>
