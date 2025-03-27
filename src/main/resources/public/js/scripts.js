function expandMenu() {
    var myLinks = document.getElementById("myLinks");
    var menuButton = document.getElementById("menu-button");
    var dropdownButton = document.getElementById("dropdown-button");
    myLinks.classList.toggle("show");
    menuButton.classList.toggle("show");
    dropdownButton.classList.toggle("show");
}