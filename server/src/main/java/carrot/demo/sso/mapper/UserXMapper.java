package carrot.demo.sso.mapper;

import carrot.demo.sso.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserXMapper {

    @Select("select * from user where name=#{name} ")
    public User select(String name);

    @Insert("INSERT INTO `user` ( `name`, `passwd`) VALUES ( #{name}, #{passwd});")
    public int insert(User user);
}
