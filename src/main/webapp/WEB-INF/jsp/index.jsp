<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activePage" value="index" scope="request"/>
<%@ include file="header.jsp" %>
    <section class="hero">
        <div class="hero-content">
            <h2>Профессиональные курсы стоматологии</h2>
            <p class="hero-subtitle">Повышение квалификации для стоматологов и ортодонтов</p>
            <div class="hero-actions">
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Смотреть курсы</a>
                <c:if test="${empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">Войти в систему</a>
                </c:if>
            </div>
        </div>
    </section>

    <section class="features">
        <h2 class="section-title">Наши направления</h2>
        <div class="features-grid">
            <div class="feature-card">
                <div class="feature-icon">🦷</div>
                <h3>Ортодонтия</h3>
                <p>Курсы по исправлению прикуса и выравниванию зубов. Современные методики и оборудование.</p>
                <div class="feature-price">от 80 000 руб.</div>
            </div>

            <div class="feature-card">
                <div class="feature-icon">🔪</div>
                <h3>Хирургия</h3>
                <p>Хирургические вмешательства и имплантация. Практические занятия на современных фантомах.</p>
                <div class="feature-price">от 150 000 руб.</div>
            </div>

            <div class="feature-card">
                <div class="feature-icon">🦴</div>
                <h3>Ортопедия</h3>
                <p>Протезирование и восстановление зубов. Работа с керамикой и современными материалами.</p>
                <div class="feature-price">от 200 000 руб.</div>
            </div>
        </div>
    </section>

    <section class="stats">
        <div class="stat-item">
            <div class="stat-number">500+</div>
            <div class="stat-label">Выпускников</div>
        </div>
        <div class="stat-item">
            <div class="stat-number">3</div>
            <div class="stat-label">Направления</div>
        </div>
        <div class="stat-item">
            <div class="stat-number">95%</div>
            <div class="stat-label">Успешных случаев</div>
        </div>
    </section>
<%@ include file="footer.jsp" %>