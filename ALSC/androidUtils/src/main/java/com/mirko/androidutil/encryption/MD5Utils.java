package com.mirko.androidutil.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Description:主要功能:MD5加密 不可逆（Message Digest，消息摘要算法）
 * @Prject: CommonUtilLibrary
 * @Package: com.jingewenku.abrahamcaijin.commonutil.encryption
 * @author: AbrahamCaiJin
 * @date: 2017年05月16日 15:56
 * @Copyright: 个人版权所有
 * @Company:
 * @version: 1.0.0
 */

public class MD5Utils {
    /**
     * 不可逆加密算法:不可逆加密算法的特征是加密过程中不需要使用密钥，输入明文后由系统直接经过加密算法处理成密文，这种加密后的数据是无法被解密的，
     * 只有重新输入明文，并再次经过同样不可逆的加密算法处理，得到相同的加密密文并被系统重新识别后，才能真正解密。显然，在这类加密过程中，加密是自
     * 己，解密还得是自己，而所谓解密，实际上就是重新加一次密，所应用的“密码”也就是输入的明文。不可逆加密算法不存在密钥保管和分发问题，非常适合在
     * 分布式网络系统上使用，但因加密计算复杂，工作量相当繁重，通常只在数据量有限的情形下使用，如广泛应用在计算机系统中的口令加密，利用的就是不可
     * 逆加密算法。近年来，随着计算机系统性能的不断提高，不可逆加密的应用领域正在逐渐增大。在计算机网络中应用较多不可逆加密算法的有RSA公司发明的
     * MD5算法和由美国国家标准局建议的不可逆加密标准SHS(Secure Hash Standard:安全杂乱信息标准)等
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("加密后："+encryptMD5("cg"));
    }

    private MD5Utils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * MD5加密
     */
    public static String encryptMD5(String securityStr) {
        byte[] data = securityStr.getBytes();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] resultBytes = md5.digest();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < resultBytes.length; i++) {
            if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
                builder.append("0").append(
                    Integer.toHexString(0xFF & resultBytes[i]));
            } else {
                builder.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
        }
        return builder.toString();
    }
    /**
     * 获取经过MD5加密后的字符串
     * @param plainText 要加密的字符串
     * @param iterations 迭代加密的次数，0表示不加密，1表示md5(plainText), 2表示md5(md5(plainText))...
     * @return 经过MD5算法加密的字符串形式的32位16进制,如果参数表示的字符串为空，返回null
     */
    public static String getMD5Code(String plainText, int iterations) {
        if (iterations > 0 && plainText != null) {
            iterations--;
            String result = getMD5Code(plainText);
            if (iterations > 0) {
                result = getMD5Code(result, iterations);
            }
            return result;
        }
        return null;
    }

    /**
     * 获取经过MD5加密后的字符串
     * @param plainText 要加密的字符串
     * @return 经过MD5算法加密的字符串形式的32位16进制,如果参数表示的字符串为空，返回null
     */
    public static String getMD5Code(String plainText) {
        String plainTextN = "alsc" + plainText;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(plainTextN.getBytes());
            String bi = new BigInteger(1, md5.digest()).toString(16);
            return fillMD5(bi);
        } catch (Exception e) {
            return "";
        }
    }

    public static String fillMD5(String md5){
        return md5.length()==32?md5:fillMD5("0"+md5);
    }

    /**
     * 将MD5码一段字符串替换成随机16进制字符
     * @param md5Code 需要替换的MD5码
     * @param offset 偏移量，即从第几个字符开始替换
     * @param len 要替换的字符数
     * @return 替换后的新字符串，如果参数表示的MD5码为空，则返回null
     */
    public static String replaceCharacter(String md5Code, int offset, int len) {
        if (md5Code!=null) {
            char[] charArr = "1234567890abcdef".toCharArray();
            char[] md5Arr = md5Code.toCharArray();
            int n = charArr.length;
            Random random = new Random();
            for (int i = offset; i < offset+len; i++) {
                char randomChar = charArr[random.nextInt(n)];
                md5Arr[i] = randomChar;
            }
            return new String(md5Arr, 0, md5Arr.length);
        }
        return null;
    }

    /**
     * 获取文件的md5值
     * @param path 文件的路径
     * @return md5值，文件不存在返回null
     */
    public static String getFileMd5Code(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            return getFileMd5Code(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件的md5值
     * @param file 文件
     * @return md5值，文件不存在返回null
     */
    public static String getFileMd5Code(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return getFileMd5Code(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFileMd5Code(InputStream in) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                messageDigest.update(buf, 0, len);
            }
            in.close();
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
