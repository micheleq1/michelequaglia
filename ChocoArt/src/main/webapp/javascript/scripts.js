document.addEventListener("DOMContentLoaded", function() {
    var userMenu = document.getElementById("user-menu");
    var dropdownContent = document.getElementById("dropdown-content");

    userMenu.addEventListener("mouseover", function() {
        dropdownContent.style.display = "block";
    });

    userMenu.addEventListener("mouseout", function() {
        dropdownContent.style.display = "none";
    });
});
document.addEventListener('DOMContentLoaded', function() {
    var hearts = document.querySelectorAll('.heart');
    hearts.forEach(function(heart) {
        heart.addEventListener('click', function(event) {
            event.preventDefault(); 

          
            this.classList.toggle('clicked');

           
            var productId = this.getAttribute('href').split('Id=')[1];

            // Chiamata AJAX per aggiungere o rimuovere dai preferiti
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'AggiungiAiPreferitiServlet?Id=' + productId, true);
            xhr.onload = function() {
                if (xhr.status === 200) {
                    console.log(xhr.responseText); 
                }
            };
            xhr.send();
        });
    });
});


    function searchProducts(searchQuery) {
        var products = document.querySelectorAll('.product');
        var searchValue = searchQuery.trim().toLowerCase();

        products.forEach(function(product) {
            var productName = product.querySelector('h2').textContent.toLowerCase();
            var isVisible = productName.includes(searchValue);
            product.style.display = isVisible ? 'block' : 'none';
        });
    }





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
                var price = parseFloat(item.querySelector('.product-price').textContent.split(' ')[1]); 
                var total = quantity * price;
                totalPrice += total; // Aggiorna il totale generale
                orderItemsHTML += '<p>' + productName + ' - Quantità: ' + quantity + '</p>';
            });

            orderItemsSummaryDiv.innerHTML = orderItemsHTML;
            totalSummaryDiv.querySelector('#total-price').textContent = totalPrice.toFixed(2); // Aggiorna il totale generale nel riepilogo dell'ordine
        }

        // Funzione per inviare il totale e le quantità dei prodotti al backend
        function sendOrderDetailsToBackend(callback) {
            var cartItems = document.querySelectorAll('.product-details');
            var xhr = new XMLHttpRequest();
            var url = "AcquistoCompletatoServlet";
            var params = "";

            cartItems.forEach(function(item, index) {
                var productId = item.querySelector('.product-id').textContent.split(' ')[1]; // Prendi solo il valore numerico dell'ID
                var quantity = item.querySelector('.quantity-input').value;
                params += "quantity_" + productId + "=" + quantity;
                if (index < cartItems.length - 1) {
                    params += "&";
                }
            });

            var totalPrice = document.getElementById("total-price").textContent;
            params += "&totalPrice=" + encodeURIComponent(totalPrice);

            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    console.log("Dati inviati con successo.");
                    if (callback) callback(); 
                }
            };
            xhr.send(params);
        }

       
        var quantityInputs = document.querySelectorAll('.quantity-input');
        quantityInputs.forEach(function(input) {
            input.addEventListener('change', updateOrderSummary);
        });

        
        window.onload = function() {
            updateOrderSummary();
        };

        // Funzione per gestire il click sul pulsante di acquisto
        document.getElementById("buy-btn").addEventListener("click", function() {
            sendOrderDetailsToBackend(function() {
                window.location.href = "AcquistaServlet"; 
            });
        });
    



     