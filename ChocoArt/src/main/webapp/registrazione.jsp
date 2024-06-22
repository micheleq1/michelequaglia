<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrati</title>
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
                <li><a href="login.jsp">Login</a></li>
                <li><a href="index.jsp">Home</a></li>
            </ul>
        </nav>
    </div>
</header>

<% String status = (String) request.getAttribute("status");
if (status != null) {
    if (status.equals("email_exists")) { %>
        <div class="error-message">
            <p>Email o username già registrati, riprova o accedi</p>
        </div>
    <% }
    if (status.equals("invalid_email")) { %>
        <div class="error-message">
            <p>Inserisci un indirizzo email valido</p>
        </div>
    <% }
    if (status.equals("success")) { %>
        <p style="background-color: #DFF0D8; color: #3C763D; padding: 10px; border-radius: 4px;">Registrazione effettuata con successo</p>
    <% }
} %>

<form id="registrationForm" method="post" action="RegistrationServlet" onsubmit="return validateForm()">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="email">Email:</label>
    <input type="text" id="email" name="email" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="indirizzo">Indirizzo:</label>
    <input type="text" id="indirizzo" name="indirizzo" required><br><br>

    <button type="submit">Registrati</button>
</form>

<script>
    function validateForm() {
        var username = document.getElementById('username').value;
        var email = document.getElementById('email').value;
        var password = document.getElementById('password').value;
        var indirizzo = document.getElementById('indirizzo').value;
        var isValid = true;

        // Regular expression for email validation (simple one for demonstration)
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        // Example: Password must be at least 6 characters long
        if (password.length < 5) {
            displayErrorMessage('La password deve essere lunga almeno 6 caratteri');
            isValid = false;
        }

        // Validate email format using regex
        if (!emailRegex.test(email)) {
            displayErrorMessage('Inserisci un indirizzo email valido');
            isValid = false;
        }

        // Additional validation can be added here as needed

        // If all validations pass, submit the form
        return isValid;
    }

    function displayErrorMessage(message) {
        var errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.textContent = message;

        // Remove any existing error messages
        var existingError = document.querySelector('.error-message');
        if (existingError) {
            existingError.remove();
        }

        // Insert the new error message before the form
        var form = document.getElementById('registrationForm');
        form.parentNode.insertBefore(errorDiv, form);
    }

    // Set focus on the first input field when the page loads
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('username').focus();
    });
</script>

</body>
</html>
