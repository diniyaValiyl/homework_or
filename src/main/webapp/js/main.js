// main.js - общие функции для всех страниц

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    initializeVisitCounter();
    loadUserPreferences();
});

// Счетчик посещений
function initializeVisitCounter() {
    let visitCount = localStorage.getItem('visitCount') || 0;
    visitCount = parseInt(visitCount) + 1;
    localStorage.setItem('visitCount', visitCount);

    const visitCounter = document.getElementById('visit-counter');
    if (visitCounter) {
        visitCounter.textContent = `Вы посетили эту страницу ${visitCount} раз`;
    }
}

// Загрузка предпочтений пользователя из Cookie
function loadUserPreferences() {
    // Можно добавить тему, язык и т.д.
    const theme = getCookie('theme') || 'light';
    setTheme(theme);
}

// Работа с Cookie
function setCookie(name, value, days) {
    const expires = new Date();
    expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
    document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`;
}

function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

// Смена темы
function setTheme(theme) {
    document.documentElement.setAttribute('data-theme', theme);
    setCookie('theme', theme, 30);
}

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'light' ? 'dark' : 'light';
    setTheme(newTheme);
}

// Выход из системы
function logout() {
    fetch('/logout', { method: 'POST' })
        .then(() => {
            window.location.href = '/login';
        });
}

// Уведомления
function showNotification(message, type = 'success') {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background: ${type === 'success' ? '#4CAF50' : '#f44336'};
        color: white;
        padding: 15px;
        border-radius: 5px;
        z-index: 1000;
    `;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 3000);
}