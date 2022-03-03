package com.gitee.ylx.util;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

class RSAEncrypt {
    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
        String message = "12345678";
        String messageEn = encrypt(message,"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDekzOGvPnMXNz3y3+RPhaG1kkZg29SttD15GM7VKq/5ng6xkoFmBnx1JK5pgjr9isa8ax27mZrg7yE1We7UFtva7Tp+KivHVQjgGxOW0fUPjtsaU0ik7XOggCj8LY2qXOmMkWkOU4fq0QrqjsMTxr3jVX6PRyMqWnZayAXh57IQIDAQAB");
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn+"1","MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIN6TM4a8+cxc3PfLf5E+FobWSRmDb1K20PXkYztUqr/meDrGSgWYGfHUkrmmCOv2KxrxrHbuZmuDvITVZ7tQW29rtOn4qK8dVCOAbE5bR9Q+O2xpTSKTtc6CAKPwtjapc6YyRaQ5Th+rRCuqOwxPGveNVfo9HIypadlrIBeHnshAgMBAAECgYB21DJWlUWbzrXqI+wNw4gy6ETJAPOqAd3qkwqZtGTi5+DLx7AU6yR2p09ZItCecHAEZeRPG6fDne0kHRVlbkmS9BXDjM4wb5j0CfbKYf4TdX24WMGZ3iaFg8pfMrPBa2rgDomOCyohLFFrCr4HzUi/qfCqdVWWqr2E+cKUpJjtgQJBAN3r31DDFN4c6oS9r0sADHOsTV3PtURvA7LJn3qRtmmE86i+4AcJFelfVWSDvyxP2VsFUL64VtDXpTa/GtNPCecCQQCXquz/xunZmjcjfx0R7vyDU+WFPAm3SBdkC8FHuqjgaNygMnljTpjfxNkuB8Z8wwZ36hi0H5CGF2E8T2REN4G3AkBczxhFnlBL06wAd5Onug17EsQIR6V3STYyvz+C8w1mc8Oy/qCSUKppPwVnU/HPhlVQS5jJKi6kiIr/qkx9ssBhAkBjVgQcEoAfka4DhcHYeRrSOSTe+s+yR3lqgXh6LntlX8kA844lyNi/1wIX7+fE10dpSt4bO4FBEhcRlT4qn+BJAkEAqeMsG08FiphNf7r48oyMk3VSJat6iat7IUtcve2tq6IN7LRh90Fv6rDtgBjxTim+eGraid7I76cpVTLIJjikfA==");
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encode(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encode((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        System.out.println("公钥:\n"+publicKeyString);
        System.out.println("私钥:\n"+privateKeyString);
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
        byte[] decoded = Base64.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encode(cipher.doFinal(str.getBytes("UTF-8")));
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
    public static String decrypt(String str, String privateKey){
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decode(str);
            //base64编码的私钥
            byte[] decoded = Base64.decode(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        }catch (Exception e){//解密出错
            return null;
        }
    }

}

