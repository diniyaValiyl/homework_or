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
    loadCart();
    updateCartCounter();
});

// Загрузка и отображение корзины
function loadCart() {
    // Сначала пробуем из sessionStorage для быстрого отображения
    var clientCart = sessionStorage.getItem('cart');
    if (clientCart) {
        displayCart(JSON.parse(clientCart));
    }

    // Затем синхронизируем с сервером
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/test2/cart', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    var cart = JSON.parse(xhr.responseText);
                    displayCart(cart);
                    sessionStorage.setItem('cart', JSON.stringify(cart));
                    updateCartCounter();
                } catch (e) {
                    console.error('Ошибка парсинга корзины:', e);
                    displayCart({});
                }
            } else {
                console.error('Ошибка загрузки корзины:', xhr.status);
                displayCart({});
            }
        }
    };
    xhr.send();
}

// Отображение корзины
function displayCart(cart) {
    var container = document.getElementById('cart-container');

    if (!cart || getObjectLength(cart) === 0) {
        container.innerHTML = '<p>Корзина пуста</p>';
        return;
    }

    var html = '';
    var total = 0;

    for (var productId in cart) {
        if (cart.hasOwnProperty(productId)) {
            var quantity = cart[productId];
            var product = products[productId];
            if (product) {
                var itemTotal = product.price * quantity;
                total += itemTotal;

                html += '<div class="cart-item">' +
                        '<span>' + product.name + ' x' + quantity + '</span>' +
                        '<span>' + itemTotal + ' руб.</span>' +
                        '<button onclick="removeFromCart(' + productId + ')" style="background: #e74c3c; color: white; border: none; padding: 0.3rem 0.6rem; border-radius: 3px; cursor: pointer;">Удалить</button>' +
                        '</div>';
            }
        }
    }

    html += '<div class="cart-total"><strong>Итого: ' + total + ' руб.</strong></div>';
    container.innerHTML = html;
}

// Вспомогательная функция для получения длины объекта
function getObjectLength(obj) {
    var count = 0;
    for (var key in obj) {
        if (obj.hasOwnProperty(key)) {
            count++;
        }
    }
    return count;
}

// Удаление товара из корзины
function removeFromCart(productId) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/test2/cart', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            loadCart();
            updateCartCounter();
        }
    };
    xhr.send('id=' + productId + '&action=remove');
}

// Очистка корзины
function clearCart() {
    if (confirm('Вы уверены, что хотите очистить корзину?')) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/test2/cart', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                sessionStorage.setItem('cart', '{}');
                loadCart();
                updateCartCounter();
            }
        };
        xhr.send('action=clear');
    }
}

// Оформление заказа
function makeOrder() {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/test2/order', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                if (xhr.responseText === 'ORDER_SAVED') {
                    alert('Заказ успешно оформлен и сохранен!');
                    sessionStorage.setItem('cart', '{}');
                    loadCart();
                    updateCartCounter();

                    // Сохраняем в localStorage для истории
                    var ordersJson = localStorage.getItem('orders');
                    var orders = ordersJson ? JSON.parse(ordersJson) : [];
                    var currentCart = sessionStorage.getItem('cart');
                    orders.push({
                        date: new Date().toLocaleString(),
                        items: currentCart ? JSON.parse(currentCart) : {}
                    });
                    localStorage.setItem('orders', JSON.stringify(orders));
                } else {
                    alert('Ошибка: корзина пуста!');
                }
            } else {
                console.error('Ошибка:', xhr.status);
                alert('Ошибка при оформлении заказа');
            }
        }
    };
    xhr.send();
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