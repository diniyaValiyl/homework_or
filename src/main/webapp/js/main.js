// main.js - общие функции
document.addEventListener('DOMContentLoaded', function() {
    initializeVisitCounter();
});

function initializeVisitCounter() {
    let visitCount = localStorage.getItem('visitCount') || 0;
    visitCount = parseInt(visitCount) + 1;
    localStorage.setItem('visitCount', visitCount);

    const visitCounter = document.getElementById('visit-counter');
    if (visitCounter) {
        visitCounter.textContent = `Посещений: ${visitCount}`;
    }
}

function logout() {
    fetch('/logout', { method: 'POST' })
        .then(() => {
            window.location.href = '/login';
        });
}