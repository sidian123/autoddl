package live.sidian.database.autoddl.constant;

/**
 * 名字转换的样式
 *
 * @author sidian
 * @date 2020/6/9 14:57
 */
public interface NameStyleConstant {
    String normal = "normal"; //原值
    String camelhump = "camelhump";//驼峰转下划线
    String uppercase = "uppercase";//转换为大写
    String lowercase = "lowercase";//转换为小写
    String camelhumpAndUppercase = "camelhumpAndUppercase";//驼峰转下划线大写形式
    String camelhumpAndLowercase = "camelhumpAndLowercase";//驼峰转下划线小写形式
}
