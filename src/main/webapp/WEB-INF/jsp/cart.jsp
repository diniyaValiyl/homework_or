<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="activePage" value="cart" scope="request"/>
<%@ include file="header.jsp" %>
    <section class="cart-section">
        <h2>Ваша корзина</h2>

        <c:choose>
            <c:when test="${not empty cartProducts}">
                <div class="cart-items">
                    <c:forEach var="product" items="${cartProducts}">
                        <div class="cart-item">
                            <div class="item-info">
                                <h3><c:out value="${product.name}"/></h3>
                                <p><c:out value="${product.description}"/></p>
                                <p class="item-price">
                                    <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="2"/> руб.
                                    × ${cart[product.id]} =
                                    <strong>
                                        <fmt:formatNumber value="${product.price * cart[product.id]}" type="number" maxFractionDigits="2"/> руб.
                                    </strong>
                                </p>
                            </div>
                            <div class="item-actions">
                                <form action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="productId" value="${product.id}">
                                    <button type="submit" class="btn btn-danger">Удалить</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="cart-total">
                    <h3>Итого: <fmt:formatNumber value="${total}" type="number" maxFractionDigits="2"/> руб.</h3>
                </div>

                <div class="cart-actions">
                    <form action="${pageContext.request.contextPath}/order" method="post">
                        <button type="submit" class="btn btn-primary">Оформить заказ</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary">Продолжить покупки</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-cart">
                    <p>Корзина пуста</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Вернуться к курсам</a>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
<%@ include file="footer.jsp" %>