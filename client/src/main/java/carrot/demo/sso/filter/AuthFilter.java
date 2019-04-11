package carrot.demo.sso.filter;

import carrot.demo.sso.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import redis.clients.jedis.Jedis;
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

@WebFilter
public class AuthFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList("/login", "/logout",  "/login.html")));


    @Autowired
    private JedisPool jedisPool;

    @Value(value = "${sso.loginUrl}")
    private String loginUrl;

    @Value(value = "${sso.callbackUrl}")
    private String callbackUrl;

    @Value(value = "${app.name}")
    private String appName;

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
                    Jedis jedis = jedisPool.getResource();
                    String session = jedis.get(cookie.getValue());
                    if(session==null){
                        isLogin=false;
                    }else{
                        jedis.setex(cookie.getValue(), ConstantUtil.LOGIN_TIMEOUT_SECOND,session);
                        isLogin=true;
                    }
                    jedis.close();
                }
            }
        }
        if(isLogin){
            chain.doFilter(request, response);
            return;
        }else{
            StringBuffer returnUrl = httpReq.getRequestURL();
            byte[] returnUrlBytes = new String(returnUrl).getBytes("utf-8");
            String redirectUrl=loginUrl+"?callbackUrl="+callbackUrl+"&returnUrl="+ returnUrl.toString() +"&app="+appName;
            httpResp.sendRedirect(redirectUrl);
        }
    }

    public void destroy() {

    }
}
