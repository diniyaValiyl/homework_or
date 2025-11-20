<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activePage" value="login" scope="request"/>
<%@ include file="header.jsp" %>
    <div class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <h2>Вход в систему</h2>
                <p>Введите ваши учетные данные</p>
            </div>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">
                    <c:out value="${errorMessage}"/>
                </div>
            </c:if>

            <form class="auth-form" method="post" action="${pageContext.request.contextPath}/login">
                <div class="form-group">
                    <label for="username" class="form-label">Имя пользователя</label>
                    <input type="text" id="username" name="username" class="form-input"
                           placeholder="Введите ваш логин" required>
                </div>

                <div class="form-group">
                    <label for="password" class="form-label">Пароль</label>
                    <input type="password" id="password" name="password" class="form-input"
                           placeholder="Введите ваш пароль" required>
                </div>

                <div class="form-options">
                    <label class="checkbox-label">
                        <input type="checkbox" name="remember" class="checkbox">
                        <span class="checkmark"></span>
                        Запомнить меня
                    </label>
                </div>

                <button type="submit" class="btn btn-primary btn-block">Войти в систему</button>
            </form>

            <div class="auth-footer">
                <p>Нет учетной записи? <a href="${pageContext.request.contextPath}/registration" class="auth-link">Зарегистрироваться</a></p>
                <p><a href="${pageContext.request.contextPath}/index" class="auth-link">Вернуться на главную</a></p>
            </div>
        </div>
    </div>
<%@ include file="footer.jsp" %>