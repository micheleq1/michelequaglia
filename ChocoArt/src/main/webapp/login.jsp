<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="theme.css">
    <style>
        .error-message {
            color: red;
        }
    </style>
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

<% String status = (String) request.getAttribute("status");
if (status != null && status.equals("failed")) { %>
    <div class="error-message">
        <p>Email o password errata</p>
    </div>
<% } %>

<form id="loginForm" method="post" action="LoginServlet" onsubmit="return validateForm()">
    <label for="email">Email:</label>
    <input type="text" id="email" name="email" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Login</button>
    <label style="margin-left:auto; margin-top:20px;">Non hai un account?</label>
    <a href="registrazione.jsp" style="color: black;">Registrati</a>
</form>

<script>
    function validateForm() {
       
        var password = document.getElementById('password').value;
        var isValid = true;

        
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        
        
        if (password.length < 5) {
            displayErrorMessage('La password deve essere lunga almeno 6 caratteri');
            isValid = false;
        }

        
       

       
        return isValid;
    }

    function displayErrorMessage(message) {
        var errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.textContent = message;

       
        var existingError = document.querySelector('.error-message');
        if (existingError) {
            existingError.remove();
        }

        
        var form = document.getElementById('loginForm');
        form.parentNode.insertBefore(errorDiv, form);
    }

    
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('email').focus();
    });
</script>

</body>
</html>
