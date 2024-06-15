<!DOCTYPE html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" type="text/css" href="theme.css"> <!-- Link al file CSS -->
    <script src="javascript/carrello.js"></script>
</head>
<body>
    <header>
        <div class="container">
            <h1 class="logo">ChocoArt</h1>
            <nav>
                <ul>
                    <% if(session.getAttribute("name") == null) { %> 
                        <li><a href="login.jsp">Login</a></li>
                    <% } else { %>
                        <li><a href="LogoutServlet">Logout</a></li>
                        <li style="color:#D2B48C; font-weight:bold">Ciao <%=session.getAttribute("name") %></li>
                        <li><a href="#">Preferiti</a></li>
                    <% } %>
                    <li><a href="index.jsp">Home</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <div class="cart-container">
        <h2>Prodotti nel carrello:</h2>
        <ul>
            <% 
            ArrayList<Prodotto> cart = (ArrayList<Prodotto>) session.getAttribute("cart");
            if (cart != null) {
                for (Prodotto product : cart) { %>
                    <li>
                        <div class="product-container">
                            <img class="product-image" src="data:image/jpg;base64, <%= new String(product.getImmagine()) %>" alt="<%= product.getNome() %>">
                            <div class="product-details">
                                <p class="product-name"><%= product.getNome() %></p>
                                <p class="product-price">Prezzo: <%= product.getPrezzo() %></p>
                                <p class="product-id">ID: <%= product.getId() %></p>
                                <label for="quantity_<%= product.getId() %>">Quantità:</label>
                                <input type="number" id="quantity_<%= product.getId() %>" name="quantity_<%= product.getId() %>" value="1" min="1" class="quantity-input"> <br>
                                <div class="product">
                                <a href="RimuoviDalCarrelloServlet?Id=<%= product.getId() %>"> Elimina</a>
                                </div>
                            </div>
                        </div>
                    </li>
                <% }
                } else { %>
                    <li class="empty-cart-message">Il carrello è vuoto.</li>
            <% } %>
        </ul>
    </div>
    <div class="order-summary" id="order-summary">
        <h2>Riepilogo dell'ordine</h2>
        <div id="order-items-summary">
            <!-- Qui verranno aggiunti dinamicamente i nomi dei prodotti con le loro quantità -->
        </div>
        <div id="total-summary">
            <h3>Totale:</h3>
            <h3 id="total-price">0.00</h3>
            <button id="buy-btn">Acquista</button>
        </div>
    </div>

    <script>
        // Funzione per aggiornare il riepilogo dell'ordine e il totale finale
        function updateOrderSummary() {
            var cartItems = document.querySelectorAll('.product-details');
            var orderItemsSummaryDiv = document.getElementById('order-items-summary');
            var totalSummaryDiv = document.getElementById('total-summary');
            var totalPrice = 0; // Totale generale dei prodotti nel carrello

            var orderItemsHTML = ''; // HTML per i nomi dei prodotti con le loro quantità

            cartItems.forEach(function(item) {
                var productName = item.querySelector('.product-name').textContent;
                var quantity = item.querySelector('.quantity-input').value;
                var price = parseFloat(item.querySelector('.product-price').textContent.split(' ')[1]); // Prendi solo il valore numerico del prezzo
                var total = quantity * price;
                totalPrice += total; // Aggiorna il totale generale
                orderItemsHTML += '<p>' + productName + ' - Quantità: ' + quantity + '</p>';
            });

            orderItemsSummaryDiv.innerHTML = orderItemsHTML;
            totalSummaryDiv.querySelector('#total-price').textContent = totalPrice.toFixed(2); // Aggiorna il totale generale nel riepilogo dell'ordine
        }

        // Aggiungi un listener agli input delle quantità per aggiornare il riepilogo dell'ordine
        var quantityInputs = document.querySelectorAll('.quantity-input');
        quantityInputs.forEach(function(input) {
            input.addEventListener('change', updateOrderSummary);
        });

        // Chiama la funzione di aggiornamento al caricamento della pagina
        window.onload = function() {
            updateOrderSummary();
        };

        // Funzione per inviare il totale tramite AJAX
        document.getElementById("buy-btn").addEventListener("click", function() {
            var totalPrice = document.getElementById("total-price").innerText;

            var xhr = new XMLHttpRequest();
            var url = "PrezzoTotaleServlet?totalPrice=" + encodeURIComponent(totalPrice);
            xhr.open("GET", url, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        console.log("Valore salvato con successo nella sessione.");
                        window.location.href = "AcquistaServlet"; // Reindirizza alla servlet AcquistaServlet
                    } else {
                        console.error("Si è verificato un errore durante il salvataggio del valore nella sessione.");
                    }
                }
            };
            xhr.send();
        });
        
        
    </script>
    
</body>
</html>
