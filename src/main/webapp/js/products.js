// products.js - функционал страницы товаров

let allProducts = [];

// Загрузка товаров при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    loadProducts();

    // Поиск товаров
    const searchInput = document.getElementById('search-input');
    if (searchInput) {
        searchInput.addEventListener('input', filterProducts);
    }

    // Сортировка товаров
    const sortSelect = document.getElementById('sort-select');
    if (sortSelect) {
        sortSelect.addEventListener('change', sortProducts);
    }
});

// Загрузка товаров с сервера
function loadProducts() {
    fetch('/api/products')
        .then(response => response.json())
        .then(products => {
            allProducts = products;
            displayProducts(products);
        })
        .catch(error => {
            console.error('Ошибка загрузки товаров:', error);
            showNotification('Ошибка загрузки товаров', 'error');
        });
}

// Отображение товаров
function displayProducts(products) {
    const container = document.getElementById('products-container');
    if (!container) return;

    container.innerHTML = '';

    if (products.length === 0) {
        container.innerHTML = '<p class="empty-message">Товары не найдены</p>';
        return;
    }

    products.forEach(product => {
        const productCard = createProductCard(product);
        container.appendChild(productCard);
    });
}

// Создание карточки товара
function createProductCard(product) {
    const card = document.createElement('div');
    card.className = 'product-card';
    card.innerHTML = `
        <h3 class="product-name">${escapeHtml(product.name)}</h3>
        <p class="product-description">${escapeHtml(product.description)}</p>
        <p class="product-price">Цена: <strong>${product.price} руб.</strong></p>
        <button class="btn add-to-cart" onclick="addToCart(${product.id})">
            Добавить в корзину
        </button>
    `;
    return card;
}

// Добавление в корзину
function addToCart(productId) {
    fetch('/api/cart?action=add&productId=' + productId, {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            showNotification('Товар добавлен в корзину!');
            updateCartCounter();
        } else {
            showNotification('Ошибка добавления в корзину', 'error');
        }
    })
    .catch(error => {
        console.error('Ошибка:', error);
        showNotification('Ошибка соединения', 'error');
    });
}

// Фильтрация товаров
function filterProducts() {
    const searchTerm = this.value.toLowerCase();
    const filteredProducts = allProducts.filter(product =>
        product.name.toLowerCase().includes(searchTerm) ||
        product.description.toLowerCase().includes(searchTerm)
    );
    displayProducts(filteredProducts);
}

// Сортировка товаров
function sortProducts() {
    const sortBy = this.value;
    const sortedProducts = [...allProducts];

    switch (sortBy) {
        case 'price_asc':
            sortedProducts.sort((a, b) => a.price - b.price);
            break;
        case 'price_desc':
            sortedProducts.sort((a, b) => b.price - a.price);
            break;
        case 'name_asc':
            sortedProducts.sort((a, b) => a.name.localeCompare(b.name));
            break;
        case 'name_desc':
            sortedProducts.sort((a, b) => b.name.localeCompare(a.name));
            break;
    }

    displayProducts(sortedProducts);
}

// Обновление счетчика корзины
function updateCartCounter() {
    // Можно добавить отображение количества товаров в корзине
    const cartCounter = document.getElementById('cart-counter');
    if (cartCounter) {
        // Логика обновления счетчика
    }
}

// Экранирование HTML
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}