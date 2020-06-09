package live.sidian.database.autoddl.utils;


import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Java Bean 的工具类
 *
 * @author Sidian.Luo
 * @date 2019/10/10 12:36
 */
public class BeanUtils {


    /**
     * 获取Bean的所有字段, 包括继承来的.
     *
     * @param aClass Bean的类
     */
    static public Field[] getBeanFields(Class<?> aClass) {
        return Arrays
                .stream(PropertyUtils.getPropertyDescriptors(aClass))
                .map(propertyDescriptor -> ClassUtils.getField(aClass, propertyDescriptor.getName()))
                .collect(Collectors.toList()).toArray(new Field[]{});
    }


}
