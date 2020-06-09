package live.sidian.database.autoddl.service.impl;

import live.sidian.database.autoddl.model.Column;
import live.sidian.database.autoddl.model.Index;
import live.sidian.database.autoddl.model.Table;
import live.sidian.database.autoddl.service.ModelMetaInitialization;
import live.sidian.database.autoddl.utils.BeanUtils;
import live.sidian.database.autoddl.utils.ClassUtils;
import live.sidian.database.autoddl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        // TODO 生成建表语句
        return table;
    }

    /**
     * 解析所有字段
     */
    private void resolveAllFields(Class<?> aClass, Table table) {
        // 获取所有字段
        Field[] beanFields = BeanUtils.getBeanFields(aClass);
        // 解析主键 (联合主键有多个字段)
        List<Field> primaryKeyFileds = Arrays.stream(beanFields)
                .filter(field -> field.getAnnotation(Id.class) != null)
                .collect(Collectors.toList());
        table.getIndexes().put("PRIMARY", resolvePrimaryKey(primaryKeyFileds));
        // 解析索引
        List<Field> indexFields = Arrays.stream(beanFields)
                .filter(field -> field.getAnnotation(live.sidian.database.autoddl.annotation.Index.class) != null)
                .collect(Collectors.toList()); // 筛选
        indexFields.forEach(field -> { // 遍历处理
            Index temp = resolveIndex(field);
            temp.generateName(table.getName(), field.getName());
            table.getIndexes().put(temp.getName(), temp);
        });
        // 解析字段
        List<Field> fields = Arrays.stream(beanFields)
                .filter(field -> field.getAnnotation(Transient.class) == null) // 不是忽略的属性
                .collect(Collectors.toList());
        fields.forEach(field -> {
            Column column = resolveField(field);
            table.getColumns().put(column.getName(), column);
        });
    }

    private Index resolveIndex(Field field) {
        return null;
    }

    private Index resolvePrimaryKey(List<Field> fields) {
        return null;
    }

    /**
     * 解析单个字段
     */
    private Column resolveField(Field field) {
        return null;
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
