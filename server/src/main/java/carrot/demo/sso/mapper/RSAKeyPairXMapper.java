package carrot.demo.sso.mapper;

import carrot.demo.sso.bean.RSAKeyPair;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface RSAKeyPairXMapper {

    @Select("select public_key from t_rsa_key_pair where app=#{app} ")
    public String selectPublicKeyByApp(String app);

    @Select("select private_key from t_rsa_key_pair where app=#{app} ")
    public String selectPrivateKeyByApp(String app);

    @Insert("INSERT INTO `t_rsa_key_pair` (`app`, `public_key`, `private_key`) VALUES (#{pair.app}, #{pair.publicKey}, #{pair.privateKey})")
    public int insert(RSAKeyPair pair);
}
