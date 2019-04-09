package carrot.demo.sso.controller;

import carrot.demo.sso.bean.User;
import carrot.demo.sso.dto.request.LoginRequest;
import carrot.demo.sso.dto.request.RegisterRequest;
import carrot.demo.sso.dto.response.Response;
import carrot.demo.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response login(HttpServletResponse response, @RequestBody LoginRequest loginRequest) throws UnsupportedEncodingException {
        Response  r = userService.login(loginRequest,response);
        return r;
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Response login(@RequestBody RegisterRequest registerRequest){
        Response  r = userService.register(registerRequest);
        return r;
    }
}

