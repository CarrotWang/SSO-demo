package carrot.demo.sso.service;

import carrot.demo.sso.bean.User;
import carrot.demo.sso.dto.request.LoginRequest;
import carrot.demo.sso.dto.request.RegisterRequest;
import carrot.demo.sso.dto.response.Response;
import carrot.demo.sso.mapper.UserXMapper;
import carrot.demo.sso.util.ConstantUtil;
import carrot.demo.sso.util.MD5;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class UserService {

    public final static int LOGIN_TIMEOUT_SECOND = 60 * 60 * 24 * 7;

    @Autowired
    UserXMapper userXMapper;

    @Autowired
    JedisPool jedisPool;

    public User getUser(String name){
        return userXMapper.select(name);
    }

    public Response login(LoginRequest loginRequest, HttpServletResponse response) throws UnsupportedEncodingException {
        User u = userXMapper.select(loginRequest.getName());
        //注意空指针（NPE）判断
        if(u!=null){
            if(u.getPasswd().equals(MD5.md5(loginRequest.getPasswd()))){
                jedisPool.getResource().setex("session_"+u.getId() , LOGIN_TIMEOUT_SECOND,"Session Object Json String" );
                //设置cookie
                Cookie cookie = new Cookie(ConstantUtil.USER_SESSION_KEY, URLEncoder.encode(Long.toString(u.getId()), "UTF-8"));
                cookie.setMaxAge(LOGIN_TIMEOUT_SECOND);
                cookie.setPath("/");
                response.addCookie(cookie);

                return Response.success("登录成功");
            }else{
                return Response.fail("登录失败");
            }
        }else{
            return  Response.fail("登录失败");
        }
    }

    public Response register(RegisterRequest registerRequest) {
        User u = userXMapper.select(registerRequest.getName());
        //注意空指针（NPE）判断
        if(u!=null){
            return Response.fail("注册失败");
        }else{
            User newUser=new User();
            newUser.setName(registerRequest.getName());
            newUser.setPasswd(MD5.md5(registerRequest.getPasswd()));
            if(userXMapper.insert(newUser)==1){
                return Response.success("注册成功");
            }
            return  Response.success("注册失败");
        }
    }

}
