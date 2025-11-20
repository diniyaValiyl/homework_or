<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${pageTitle}" default="Стоматологическая клиника Вильдан"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>Стоматологическая клиника "Вильдан"</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/index"
                           class="nav-link <c:if test="${param.activePage == 'index'}">active</c:if>">Главная</a></li>
                    <li><a href="${pageContext.request.contextPath}/products"
                           class="nav-link <c:if test="${param.activePage == 'products'}">active</c:if>">Курсы</a></li>
                    <li><a href="${pageContext.request.contextPath}/cart"
                           class="nav-link <c:if test="${param.activePage == 'cart'}">active</c:if>">
                           Корзина
                           <c:if test="${not empty cart}">
                               <span class="cart-counter">${cart.size()}</span>
                           </c:if>
                       </a></li>
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <li><span class="nav-user">Привет, ${sessionScope.user.username}</span></li>
                            <li><a href="${pageContext.request.contextPath}/logout"
                                   class="nav-link" onclick="logout(); return false;">Выйти</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/login"
                                   class="nav-link <c:if test="${param.activePage == 'login'}">active</c:if>">Вход</a></li>
                            <li><a href="${pageContext.request.contextPath}/registration"
                                   class="nav-link <c:if test="${param.activePage == 'registration'}">active</c:if>">Регистрация</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div>
    </header>
    <main class="container">
</body>
</html>