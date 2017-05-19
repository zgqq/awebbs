package awebbs.util;

import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by zgqq.
 */
public class StrUtil {

    static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    static final Random random = new Random();

    /**
     * 随机指定长度的字符串
     *
     * @param length
     * @return
     */
    public static String randomString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int loop = 0; loop < length; ++loop) {
            sb.append(hexDigits[random.nextInt(hexDigits.length)]);
        }
        return sb.toString();
    }

    /**
     * 随机指定长度的数字
     *
     * @param length
     * @return
     */
    public static String randomNumber(int length) {
        StringBuffer sb = new StringBuffer();
        for (int loop = 0; loop < length; ++loop) {
            sb.append(digits[random.nextInt(digits.length)]);
        }
        return sb.toString();
    }

    /**
     * 检测是否是用户accessToken
     */
    public static boolean isUUID(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) {
            return false;
        } else {
            try {
                //noinspection ResultOfMethodCallIgnored
                UUID.fromString(accessToken);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
