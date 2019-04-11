package carrot.demo.sso.controller;

import carrot.demo.sso.common.dto.response.Response;
import carrot.demo.sso.dto.request.AppCreateRequest;
import carrot.demo.sso.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AppController {

    @Autowired
    AppService appService;

    @RequestMapping(value = "createApp", method = RequestMethod.POST)
    public Response createApp(HttpServletResponse response, @RequestBody AppCreateRequest appCreateRequest) throws Exception {
        Response r = appService.createApp(appCreateRequest);
        return r;
    }
}
