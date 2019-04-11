package carrot.demo.sso.controller;

import carrot.demo.sso.common.bean.User;
import carrot.demo.sso.dto.response.Response;
import carrot.demo.sso.util.RSA;
import carrot.demo.sso.util.ConstantUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;

import static carrot.demo.sso.util.ConstantUtil.USER_SESSION_KEY;

@RestController
public class LoginController {

    @Value(value = "sso.privateKey")
    private String privateKey;


    @Autowired
    JedisPool jedisPool;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public Response login(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam String token, @RequestParam String returnUrl) throws Exception {
        Response r = Response.success("");
        String result = RSA.decrypt(URLDecoder.decode(token).replace(" ", "+"), privateKey);
        JSONObject resultObj = JSONObject.parseObject(result);
        int userId = resultObj.getInteger("userId");
        User user = new User();
        user.setId(userId);
        user.setName(resultObj.getString("name"));
        user.setDatas(resultObj.getString("datas"));

        String sessionKey = "appName_" + userId;
        Jedis jedis = jedisPool.getResource();
        jedis.setex(sessionKey, ConstantUtil.LOGIN_TIMEOUT_SECOND, JSON.toJSONString(user));
        jedis.close();

        //设置cookie
        Cookie cookie = new Cookie(USER_SESSION_KEY, sessionKey);
        cookie.setMaxAge(ConstantUtil.LOGIN_TIMEOUT_SECOND);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect(returnUrl);
        return null;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public Response logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        String key;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(USER_SESSION_KEY)) {
                key = cookie.getValue();
                Jedis jedis = jedisPool.getResource();
                jedis.del(key);
                jedis.close();
            }
        }
        Cookie cookie = new Cookie(USER_SESSION_KEY, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/");
        return null;
    }

}
