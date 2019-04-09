package carrot.demo.sso.controller;

import carrot.demo.sso.bean.User;
import carrot.demo.sso.dto.request.LoginRequest;
import carrot.demo.sso.dto.request.RegisterRequest;
import carrot.demo.sso.dto.response.Response;
import carrot.demo.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response login(HttpServletResponse response, @RequestBody LoginRequest loginRequest,
                          @RequestParam("srcUrl") String srcUrl, @RequestParam("app") String app) throws Exception {
        Response  r = userService.login(loginRequest,response,srcUrl,app);
        return r;
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Response login(@RequestBody RegisterRequest registerRequest){
        Response  r = userService.register(registerRequest);
        return r;
    }
}

