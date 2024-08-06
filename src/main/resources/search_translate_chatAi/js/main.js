// main.js
const port = 8080;

// Function to toggle the visibility of the menu
function toggleMenu() {
    const menu = document.getElementById('menu');
    menu.classList.toggle('show');
}

// Close the menu if clicked outside
document.addEventListener('click', function(event) {
    const menu = document.getElementById('menu');
    const hamburger = document.querySelector('.menu-hamburger');

    if (!menu.contains(event.target) && !hamburger.contains(event.target)) {
        menu.classList.remove('show');
    }
});