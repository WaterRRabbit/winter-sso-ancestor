import org.winterframework.sso.util.DESUtils;

/**
 * @Author: YHG
 * @Date: 2019/8/18 21:57
 */
public class DESTest {
    public static void main(String[] args) throws Exception {
//        String source = "123123123213321";
//        System.out.println("原文: " + source);
//        String key = "A1B2C3D4E5F60708";
//        String encryptData = DESUtils.encrypt(source, key);
//        System.out.println("加密后: " + encryptData);
//        String decryptData = DESUtils.decrypt(encryptData, key);
//        System.out.println("解密后: " + decryptData);
        
        System.out.println(DESUtils.decrypt("123",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0"));
    }
}
