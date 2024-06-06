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