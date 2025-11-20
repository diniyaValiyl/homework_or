package oris.other;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthorisationFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) req).getRequestURI();

        if (requestURI.equals("/index") || requestURI.equals("/") ||
                requestURI.equals("/registration") || requestURI.equals("/login") ||
                requestURI.equals("/logout") || requestURI.startsWith("/css/") ||
                requestURI.startsWith("/js/") || requestURI.startsWith("/html/") ||
                requestURI.startsWith("/api/")) {
            chain.doFilter(req, resp);
        } else {
            HttpSession session = ((HttpServletRequest) req).getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                ((HttpServletResponse) resp).sendRedirect("/login");
            } else {
                chain.doFilter(req, resp);
            }
        }
    }
}