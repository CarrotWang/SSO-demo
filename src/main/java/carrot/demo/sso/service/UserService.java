package carrot.demo.sso.service;

import carrot.demo.sso.bean.User;
import carrot.demo.sso.dto.request.LoginRequest;
import carrot.demo.sso.dto.request.RegisterRequest;
import carrot.demo.sso.dto.response.Response;
import carrot.demo.sso.mapper.UserXMapper;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

@Service
public class UserService {

    @Autowired
    UserXMapper userXMapper;

    public User getUser(String name){
        return userXMapper.select(name);
    }

    public Response login(LoginRequest loginRequest) {
        User u = userXMapper.select(loginRequest.getName());
        //注意空指针（NPE）判断
        if(u!=null){
            if(u.getPasswd().equals(MD5Encoder.encode(loginRequest.getPasswd().getBytes()))){
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
            newUser.setPasswd(MD5Encoder.encode(registerRequest.getPasswd().getBytes()));
            if(userXMapper.insert(newUser)==1){
                return Response.success("注册成功");
            }
            return  Response.success("注册失败");
        }
    }

}
