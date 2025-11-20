// cart.js - функционал корзины

let cartItems = [];
let totalPrice = 0;

// Загрузка корзины при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    loadCart();
});

// Загрузка корзины с сервера
function loadCart() {
    fetch('/api/cart')
        .then(response => response.json())
        .then(data => {
            cartItems = data.items || [];
            totalPrice = data.total || 0;
            displayCart();
        })
        .catch(error => {
            console.error('Ошибка загрузки корзины:', error);
            showNotification('Ошибка загрузки корзины', 'error');
        });
}

// Отображение корзины
function displayCart() {
    const container = document.getElementById('cart-container');
    if (!container) return;

    if (cartItems.length === 0) {
        container.innerHTML = `
            <div class="empty-cart">
                <p>Корзина пуста</p>
                <a href="/products" class="btn">Вернуться к товарам</a>
            </div>
        `;
        return;
    }

    let html = `
        <div class="cart-items">
            <h2>Ваша корзина</h2>
    `;

    cartItems.forEach(item => {
        html += `
            <div class="cart-item" data-product-id="${item.product.id}">
                <div class="item-info">
                    <h3>${escapeHtml(item.product.name)}</h3>
                    <p>${escapeHtml(item.product.description)}</p>
                    <p class="item-price">${item.product.price} руб. × ${item.quantity} = ${item.itemTotal} руб.</p>
                </div>
                <div class="item-actions">
                    <button class="btn btn-danger" onclick="removeFromCart(${item.product.id})">
                        Удалить
                    </button>
                </div>
            </div>
        `;
    });

    html += `
            <div class="cart-total">
                <h3>Итого: ${totalPrice} руб.</h3>
            </div>
            <div class="cart-actions">
                <button class="btn btn-primary" onclick="makeOrder()">
                    Оформить заказ
                </button>
                <a href="/products" class="btn btn-secondary">
                    Продолжить покупки
                </a>
            </div>
        </div>
    `;

    container.innerHTML = html;
}

// Удаление из корзины
function removeFromCart(productId) {
    if (!confirm('Удалить товар из корзины?')) return;

    fetch('/api/cart?action=remove&productId=' + productId, {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            showNotification('Товар удален из корзины');
            loadCart(); // Перезагружаем корзину
        } else {
            showNotification('Ошибка удаления товара', 'error');
        }
    })
    .catch(error => {
        console.error('Ошибка:', error);
        showNotification('Ошибка соединения', 'error');
    });
}

// Оформление заказа
function makeOrder() {
    if (cartItems.length === 0) {
        showNotification('Корзина пуста', 'error');
        return;
    }

    fetch('/order', {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            showNotification('Заказ успешно оформлен!');
            setTimeout(() => {
                window.location.href = '/products';
            }, 2000);
        } else {
            showNotification('Ошибка оформления заказа', 'error');
        }
    })
    .catch(error => {
        console.error('Ошибка:', error);
        showNotification('Ошибка соединения', 'error');
    });
}

// Экранирование HTML
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}