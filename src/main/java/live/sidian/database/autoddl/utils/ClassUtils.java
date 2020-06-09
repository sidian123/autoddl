package live.sidian.database.autoddl.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author sidian
 * @date 2020/6/9 16:45
 */
public class ClassUtils {
    /**
     * 获取类的字段
     *
     * @param aClass B类
     * @param name   字段名
     * @return 字段, 未找到则null
     */
    static public Field getField(Class<?> aClass, String name) {
        // 查看当前类直接声明的字段, 不含继承的
        Field[] declaredFields = aClass.getDeclaredFields();
        // 遍历查找
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals(name)
                    && !Modifier.isStatic(declaredField.getModifiers())) { // 找到,且为实例属性
                return declaredField;
            } else { // 未找到
                // 找直接声明的父类(接口不用管, 接口无实例字段)
                Class<?> superclass = aClass.getSuperclass();
                if (superclass == null) return null;
                // 查找
                Field field;
                if ((field = getField(superclass, name)) != null) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 扫描package包及其子包下的所有类
     *
     * @param packageName 包名
     * @return 所有类的集合
     */
    public static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');

        // 找到所有满足条件的包(可能有多个)
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();// 包的路径集合
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        // 获取包下的类
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        //noinspection ToArrayCallWithZeroLengthArrayArgument
        return classes.toArray(new Class<?>[classes.size()]);
    }

    /**
     * 递归查找目录及子目录下的类
     *
     * @param directory   目录
     * @param packageName 目录代表的包名
     * @return class集合
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        assert directory.isDirectory();
        // 查找类
        File[] files = directory.listFiles(); // 获取目录下所有文件
        assert files != null;
        for (File file : files) { // 对于每个文件
            if (file.isDirectory()) { // 是目录
                // 查找目录下的所有类
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) { // 是class文件
                // 记录
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
