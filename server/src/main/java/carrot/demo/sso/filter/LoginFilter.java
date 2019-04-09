package carrot.demo.sso.filter;

import carrot.demo.sso.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.JedisPool;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static carrot.demo.sso.service.UserService.LOGIN_TIMEOUT_SECOND;

/*
    关于filter和interceptor
    https://www.cnblogs.com/rayallenbj/p/8484276.html
*/
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Autowired
    JedisPool jedisPool;

    @Value(value = "${loginUrl}")
    public String loginUrl;

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList("/login", "/logout",  "/login.html")));

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpServletResponse httpResp = (HttpServletResponse)response;
        boolean isLogin = false;

        String reqUri = httpReq.getRequestURI();
        if (ALLOWED_PATHS.contains(reqUri) || reqUri.endsWith(".css") || reqUri.endsWith(".js") || reqUri.endsWith(".ico")
                || reqUri.endsWith(".png") || reqUri.endsWith(".map")) {
            chain.doFilter(request, response);
            return;
        }
        Cookie[] cookies = httpReq.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ConstantUtil.USER_SESSION_KEY)) {
                    String session = jedisPool.getResource().get(cookie.getValue());
                    if(session==null){
                        session="{}";
                    }
                    jedisPool.getResource().setex("",LOGIN_TIMEOUT_SECOND,session);
                    isLogin=true;
                }
            }
        }
        if(isLogin){
            chain.doFilter(request, response);
            return;
        }else{
            httpResp.sendRedirect(loginUrl);
        }


    }

    public void destroy() {

    }

}
