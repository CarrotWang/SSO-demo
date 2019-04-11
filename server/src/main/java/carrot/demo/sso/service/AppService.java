package carrot.demo.sso.service;


import carrot.demo.sso.bean.RSAKeyPair;
import carrot.demo.sso.common.dto.response.Response;
import carrot.demo.sso.mapper.RSAKeyPairXMapper;
import carrot.demo.sso.dto.request.AppCreateRequest;
import carrot.demo.sso.util.RSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class AppService {

    @Autowired
    RSAKeyPairXMapper rsaKeyPairXMapper;

    public Response createApp(AppCreateRequest appCreateRequest) throws NoSuchAlgorithmException {

        Map<Integer, String> keyPair = RSA.genKeyPair();
        RSAKeyPair pair=new RSAKeyPair();
        pair.setApp(appCreateRequest.getAppName());
        pair.setPublicKey(keyPair.get(0));
        pair.setPrivateKey(keyPair.get(1));
        if(1==rsaKeyPairXMapper.insert(pair)){
            return Response.success("create success!");
        }else{
            return Response.fail("create fail!");
        }
    }
}
