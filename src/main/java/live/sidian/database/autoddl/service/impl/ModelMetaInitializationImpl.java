package live.sidian.database.autoddl.service.impl;

import live.sidian.database.autoddl.model.Table;
import live.sidian.database.autoddl.service.ModelMetaInitialization;
import live.sidian.database.autoddl.utils.BeanUtils;
import live.sidian.database.autoddl.utils.ClassUtils;
import live.sidian.database.autoddl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sidian
 * @date 2020/6/8 20:10
 */
@Component
public class ModelMetaInitializationImpl implements ModelMetaInitialization {

    @Value("${autoddl.name-style}")
    String nameStyle;

    /**
     * Java Model信息索引
     *
     * @param basePackages 要扫描的包
     * @return 代表Java Model信息的MetaData对象
     */
    @Override
    public Map<String, Table> init(String[] basePackages) {
        Map<String, Table> metaData = new LinkedHashMap<>();
        for (String basePackage : basePackages) { // 对于每个包
            //索引该包信息
            Map<String, Table> temp = null;
            try {
                temp = indexPackage(basePackage);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            assert temp != null;
            metaData.putAll(temp);
        }
        return metaData;
    }

    /**
     * 索引包
     */
    private Map<String, Table> indexPackage(String basePackage) throws IOException, ClassNotFoundException {
        LinkedHashMap<String, Table> tableInfo = new LinkedHashMap<>();
        // 获取包下所有类
        Class<?>[] classes = ClassUtils.getClasses(basePackage);
        // 索引类信息
        for (Class<?> aClass : classes) {
            Table table = indexModel(aClass);
            tableInfo.put(table.getName(), table);
        }
        return tableInfo;
    }

    /**
     * 索引Model, 获取表信息
     */
    private Table indexModel(Class<?> aClass) {
        // 解析Model上Table注解
        Table table = resolveTableInfo(aClass);
        // 解析所有字段
        resolveAllFields(aClass, table);
        // 生成建表语句
        return table;
    }

    /**
     * 解析所有字段
     */
    private void resolveAllFields(Class<?> aClass, Table table) {
        // 获取所有字段
        Field[] beanFields = BeanUtils.getBeanFields(aClass);
        // 解析字段
        for (Field beanField : beanFields) {
            resolveField(beanField, table);
        }
    }

    /**
     * TODO 解析单个字段
     */
    private void resolveField(Field beanField, Table table) {
        // 索引
        // 字段
    }

    /**
     * 解析表信息
     */
    private Table resolveTableInfo(Class<?> aClass) {
        Table table = new Table();
        // 获取注解
        javax.persistence.Table tableAnnotation = aClass.getAnnotation(javax.persistence.Table.class);
        if (tableAnnotation == null || tableAnnotation.name().isEmpty()) { // 无注解, 或有注解, 但无表名
            // 设置表名, 使用类名+首字母小写
            table.setName(StringUtils.convertByStyle(aClass.getSimpleName(), nameStyle));
        } else { // 有注解且表名存在
            table.setName(tableAnnotation.name());
        }
        return table;
    }


}
