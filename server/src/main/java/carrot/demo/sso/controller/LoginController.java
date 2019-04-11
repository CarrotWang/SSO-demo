package carrot.demo.sso.controller;

import carrot.demo.sso.dto.request.LoginRequest;
import carrot.demo.sso.dto.request.RegisterRequest;
import carrot.demo.sso.common.dto.response.Response;
import carrot.demo.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response login(HttpServletResponse response, @RequestBody LoginRequest loginRequest,
                          @RequestParam("callbackUrl") String callbackUrl,
                          @RequestParam("returnUrl") String returnUrl, @RequestParam("app") String app) throws Exception {
        Response  r = userService.login(loginRequest,response,callbackUrl,returnUrl,app);
        return r;
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Response login(@RequestBody RegisterRequest registerRequest){
        Response  r = userService.register(registerRequest);
        return r;
    }
}

