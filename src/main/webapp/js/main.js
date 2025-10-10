// Данные о товарах
var products = {
    1: { name: "Хлеб", price: 50 },
    2: { name: "Молоко", price: 80 },
    3: { name: "Яйца", price: 120 },
    4: { name: "Сыр", price: 200 },
    5: { name: "Яблоки", price: 100 },
    6: { name: "Кофе", price: 300 }
};

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    updateCartCounter();
    loadCartFromServer();
});

// Добавление товара в корзину
function addToCart(productId) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '../cart', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            updateCartCounter();
            loadCartFromServer();
        }
    };
    xhr.send('id=' + productId + '&action=add');
}

// Обновление счетчика корзины
function updateCartCounter() {
    var cartJson = sessionStorage.getItem('cart');
    var cart = cartJson ? JSON.parse(cartJson) : {};
    var totalItems = 0;

    for (var productId in cart) {
        if (cart.hasOwnProperty(productId)) {
            totalItems += cart[productId];
        }
    }

    document.getElementById('count').textContent = totalItems;
}

// Загрузка корзины с сервера
function loadCartFromServer() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '../cart', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    var cart = JSON.parse(xhr.responseText);
                    sessionStorage.setItem('cart', JSON.stringify(cart));
                    updateCartCounter();
                } catch (e) {
                    console.error('Ошибка парсинга корзины:', e);
                    sessionStorage.setItem('cart', '{}');
                }
            } else {
                console.error('Ошибка загрузки корзины:', xhr.status);
                sessionStorage.setItem('cart', '{}');
            }
        }
    };
    xhr.send();
}