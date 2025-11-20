<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </main>
    <footer>
        <div class="container">
            <div class="footer-content">
                <div class="footer-info">
                    <h3>Стоматологическая клиника "Вильдан"</h3>
                    <p>Профессиональное обучение стоматологов с 2010 года</p>
                </div>
                <div class="footer-links">
                    <a href="${pageContext.request.contextPath}/products">Курсы</a>
                    <a href="${pageContext.request.contextPath}/login">Вход</a>
                    <a href="${pageContext.request.contextPath}/registration">Регистрация</a>
                </div>
            </div>
            <p class="footer-copyright">&copy; 2024 Стоматологическая клиника "Вильдан". Все права защищены.</p>
        </div>
    </footer>

    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>