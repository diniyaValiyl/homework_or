<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activePage" value="registration" scope="request"/>
<%@ include file="header.jsp" %>
    <div class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <h2>Регистрация</h2>
                <p>Создайте новую учетную запись</p>
            </div>

            <form class="auth-form" method="post" action="${pageContext.request.contextPath}/registration">
                <div class="form-group">
                    <label for="login" class="form-label">Имя пользователя</label>
                    <input type="text" id="login" name="login" class="form-input"
                           placeholder="Придумайте логин" required>
                    <div class="form-hint">Минимум 3 символа</div>
                </div>

                <div class="form-group">
                    <label for="password" class="form-label">Пароль</label>
                    <input type="password" id="password" name="password" class="form-input"
                           placeholder="Придумайте пароль" required>
                    <div class="form-hint">Минимум 6 символов</div>
                </div>

                <button type="submit" class="btn btn-primary btn-block">Создать аккаунт</button>
            </form>

            <div class="auth-footer">
                <p>Уже есть аккаунт? <a href="${pageContext.request.contextPath}/login" class="auth-link">Войти в систему</a></p>
                <p><a href="${pageContext.request.contextPath}/index" class="auth-link">Вернуться на главную</a></p>
            </div>
        </div>
    </div>
<%@ include file="footer.jsp" %>