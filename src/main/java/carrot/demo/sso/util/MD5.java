package carrot.demo.sso.util;


import org.springframework.util.DigestUtils;

public class MD5 {
    public static String md5(String text) {
        //加密后的字符串
        String encodeStr= DigestUtils.md5DigestAsHex(text.getBytes());
        return encodeStr;
    }
}
