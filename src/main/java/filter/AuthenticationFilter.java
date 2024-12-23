package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@WebFilter("/*") // 모든 요청에 필터 적용
public class AuthenticationFilter implements Filter {
    // 허용된 URI를 정규 표현식으로 변환
    private static final List<Pattern> ALLOWED_PATTERNS = Arrays.asList(
            Pattern.compile("^/$"),
            Pattern.compile("^/login(/.*)?$"),
            Pattern.compile("^/register(/.*)?$")
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String requestPath = uri.substring(contextPath.length());

        // 세션에서 로그인 여부 확인
        HttpSession session = httpRequest.getSession(true);  // 세션이 없으면 새로 생성
        Object user = session.getAttribute("user");
        String userType = (String) session.getAttribute("userType");

        // 로그인 여부 및 허용된 URI 확인
        boolean isLoggedIn = (user != null && userType != null);

        if (isAllowedUri(requestPath) || isLoggedIn) {
            chain.doFilter(request, response);  // 로그인된 경우나 허용된 URI에 대해서는 계속 진행
        } else {
            String loginPage = contextPath + "/login/form?alert=true";  // 파라미터 추가
            httpResponse.sendRedirect(loginPage);  // 로그인 페이지로 리다이렉트
        }
    }

    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter destroyed");
    }

    // 허용된 URI와 정규식 매칭 확인
    private boolean isAllowedUri(String requestPath) {
        return ALLOWED_PATTERNS.stream().anyMatch(pattern -> pattern.matcher(requestPath).matches());
    }
}
