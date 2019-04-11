package carrot.demo.sso.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSA {
//    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
//        genKeyPair();
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        keyMap.put(0,"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC/Qiq3Dw5pZGJk7sYn94PrqfD3kIDWs3ksX4Dvs30uSAFTGSEXHNMpqNidb/WXB98KB5Z7Q3v/1juYgIUiNDV7Tx/dgDW4nWIsqdDQnt8yAxNGX59aWt4AxEEcT6r5N6SiwmpofgnhuN6RuHjXVkeNsoU5kIQ+aRFfzKmQFKGawIDAQAB");
        keyMap.put(1,"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIL9CKrcPDmlkYmTuxif3g+up8PeQgNazeSxfgO+zfS5IAVMZIRcc0ymo2J1v9Zc" +
                        "H3woHlntDe//WO5iAhSI0NXtPH92ANbidYiyp0NCe3zIDE0Zfn1pa3gDEQRxPqvk3pKLCamh+CeG43pG4eNdWR42yhTmQhD5pEV/MqZAUoZrA" +
                        "gMBAAECgYA8tNJghUMwIEir7xiF2AmNUQ+VWhtT/LBxbcUOLU68PVPWwMnRAhu5iX0tM48d5IH/A4TVfmS4NqpbiHaYV7QY11sb39Jeez4/ZJ" +
                        "gJ4ZC64CIcSQ+pS1h1jMIq3yjtNhPwg4mI03Sha0RuAW2EOo9VQFKVzSJMD1JisI9DFnCzWQJBALtdMs8HtvTZiH79HlEnIcAiKo9lgHYFtDl" +
                        "2RYhyTNMx/aZGUIv6/8hA48nTOJDNCfpS/qFutaSVEjBDPbMan+UCQQCy+PzXzljqsgTqKgShijGBMWUKBsM5QVzhtDycHf+mRsSwFZUJxaeF" +
                        "rSNY8e3DWvexDvwtLJ8tQY1rFkAFdAgPAkBzqx6x1vZpBF6S3xq3SOw6HB2gNy5uLilMDCrzCHVLMKKB4wjY0hTUgNLfhyl1o09UnaYa7SjwO" +
                        "SNobiqKBpbNAkAZRi42r65A3ojxJKPoyCI7k2UoaFITeNaMDS7uARKEQcQu9a1JZv9EUnIGcovYJbWK7InSO1XzRymYcNjzrDIjAkBy/uWt1q" +
                        "A3vxr5gCMuxrKZ/Rb5dYUsEmLenWPUl8yV7GAlghAXgmZVI/YyDoh4wkN+UV2WGNm0RDcAB1pZ5JJS");

        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = encrypt(message,keyMap.get(0));
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn,keyMap.get(1));
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static Map<Integer,String> genKeyPair() throws NoSuchAlgorithmException {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
        return keyMap;
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        privateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIL9CKrcPDmlkYmTuxif3g+up8PeQgNazeSxfgO+zfS5IAVMZIRcc0ymo2J1v9Zc" +
                "H3woHlntDe//WO5iAhSI0NXtPH92ANbidYiyp0NCe3zIDE0Zfn1pa3gDEQRxPqvk3pKLCamh+CeG43pG4eNdWR42yhTmQhD5pEV/MqZAUoZrA" +
                "gMBAAECgYA8tNJghUMwIEir7xiF2AmNUQ+VWhtT/LBxbcUOLU68PVPWwMnRAhu5iX0tM48d5IH/A4TVfmS4NqpbiHaYV7QY11sb39Jeez4/ZJ" +
                "gJ4ZC64CIcSQ+pS1h1jMIq3yjtNhPwg4mI03Sha0RuAW2EOo9VQFKVzSJMD1JisI9DFnCzWQJBALtdMs8HtvTZiH79HlEnIcAiKo9lgHYFtDl" +
                "2RYhyTNMx/aZGUIv6/8hA48nTOJDNCfpS/qFutaSVEjBDPbMan+UCQQCy+PzXzljqsgTqKgShijGBMWUKBsM5QVzhtDycHf+mRsSwFZUJxaeF" +
                "rSNY8e3DWvexDvwtLJ8tQY1rFkAFdAgPAkBzqx6x1vZpBF6S3xq3SOw6HB2gNy5uLilMDCrzCHVLMKKB4wjY0hTUgNLfhyl1o09UnaYa7SjwO" +
                "SNobiqKBpbNAkAZRi42r65A3ojxJKPoyCI7k2UoaFITeNaMDS7uARKEQcQu9a1JZv9EUnIGcovYJbWK7InSO1XzRymYcNjzrDIjAkBy/uWt1q" +
                "A3vxr5gCMuxrKZ/Rb5dYUsEmLenWPUl8yV7GAlghAXgmZVI/YyDoh4wkN+UV2WGNm0RDcAB1pZ5JJS";
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}

