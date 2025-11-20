<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="activePage" value="products" scope="request"/>
<%@ include file="header.jsp" %>
    <section class="products-header">
        <h2>Наши курсы</h2>
        <p>Выберите подходящий курс для повышения квалификации</p>

        <div class="products-info">
            <p>Показано <strong>${products.size()}</strong> из <strong>${totalProducts}</strong> курсов</p>
        </div>
    </section>

    <section class="products-section">
        <div class="products-grid">
            <c:choose>
                <c:when test="${not empty products}">
                    <c:forEach var="product" items="${products}">
                        <div class="product-card">
                            <h3 class="product-name"><c:out value="${product.name}"/></h3>
                            <p class="product-description"><c:out value="${product.description}"/></p>
                            <p class="product-price">
                                Цена: <strong><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="2"/> руб.</strong>
                            </p>
                            <form action="${pageContext.request.contextPath}/cart" method="post" class="add-to-cart-form">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="productId" value="${product.id}">
                                <button type="submit" class="btn btn-primary">Добавить в корзину</button>
                            </form>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-message">
                        <p>Курсы не найдены</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Пагинация -->
        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/products?page=${currentPage - 1}&size=${pageSize}"
                       class="pagination-link">← Назад</a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="pageNum">
                    <c:choose>
                        <c:when test="${pageNum == currentPage}">
                            <span class="pagination-current">${pageNum}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/products?page=${pageNum}&size=${pageSize}"
                               class="pagination-link">${pageNum}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/products?page=${currentPage + 1}&size=${pageSize}"
                       class="pagination-link">Вперед →</a>
                </c:if>
            </div>
        </c:if>
    </section>
<%@ include file="footer.jsp" %>