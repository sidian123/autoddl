package live.sidian.database.autoddl.annotation;

import live.sidian.database.autoddl.constant.IndexType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标注索引信息.
 * 标注主键, 请用Id注解; 暂不支持聚簇索引
 *
 * @author sidian
 * @date 2020/6/9 21:16
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Index {
    /**
     * 索引名
     * 默认索引名格式: tb_<表名>_<字段名>_<索引类型标识>
     */
    String name();

    /**
     * 索引类型
     */
    IndexType type() default IndexType.INDEX;
}
