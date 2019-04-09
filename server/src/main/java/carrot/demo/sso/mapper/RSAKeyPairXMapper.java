package carrot.demo.sso.mapper;

import carrot.demo.sso.bean.RSAKeyPair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface RSAKeyPairXMapper {

    @Select("select public_key from t_rsa_key_pair where app=#{app} ")
    public String selectPublicKeyByApp(String app);
}
