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
            event.preventDefault(); // Previene l'azione predefinita (navigazione alla servlet)

            // Cambia la classe del cuore
            this.classList.toggle('clicked');

            // ID del prodotto associato al cuore
            var productId = this.getAttribute('href').split('Id=')[1];

            // Chiamata AJAX per aggiungere o rimuovere dai preferiti
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'AggiungiAiPreferitiServlet?Id=' + productId, true);
            xhr.onload = function() {
                if (xhr.status === 200) {
                    console.log(xhr.responseText); // Puoi gestire una risposta se necessario
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


     