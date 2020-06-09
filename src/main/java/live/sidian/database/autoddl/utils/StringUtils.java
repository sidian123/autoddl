package live.sidian.database.autoddl.utils;

import live.sidian.database.autoddl.constant.NameStyleConstant;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sidian.Luo
 * @date 2019/10/10 11:40
 */
public class StringUtils {
    private static final Pattern UNDERLINE_TO_CAMELHUMP_PATTERN = Pattern.compile("_[a-z]");


    /**
     * 是否为空, null安全
     *
     * @param string 字符串
     * @return string为null或空时返回true
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * 是否为空, null安全
     *
     * @param string 字符串
     * @return string为null或仅含空白字符时返回true
     * @since 11
     */
    public static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }

    /**
     * 字符串首字母大写
     */
    public static String firstLetterUppercase(String string) {
        if (string.isEmpty()) {
            return string;
        }
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    /**
     * 字符串首字母小写
     */
    public static String firstLetterLowercase(String string) {
        if (string.isEmpty()) {
            return string;
        }
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }

    /**
     * 获取uuid
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString();//v4版本的uuid
    }

    /**
     * str是否包含searchStr字符串, 忽略大小写
     */
    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(str, searchStr);
    }

    /**
     * 根据指定的样式进行转换
     */
    public static String convertByStyle(String str, String style) {
        switch (style) {
            case NameStyleConstant.camelhump:
                return camelHumpToUnderline(str);
            case NameStyleConstant.uppercase:
                return str.toUpperCase();
            case NameStyleConstant.lowercase:
                return str.toLowerCase();
            case NameStyleConstant.camelhumpAndLowercase:
                return camelHumpToUnderline(str).toLowerCase();
            case NameStyleConstant.camelhumpAndUppercase:
                return camelHumpToUnderline(str).toUpperCase();
            case NameStyleConstant.normal:
            default:
                return str;
        }
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelHumpToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder(
                (size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    /**
     * 将下划线风格替换为驼峰风格
     */
    public static String underlineToCamelHump(String str) {
        Matcher matcher = UNDERLINE_TO_CAMELHUMP_PATTERN.matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }
}
