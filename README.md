本项目支持`javax.persistence`和`tk.mybatis`(通用Mapper)的注解标注实体类

* `Table` 标注表
* `Column` 标注列
* `Transient` 标注忽略的字段
* `Id` 标注主键(支持联合主键)
* `KeySql` 主键生成策略, 仅用到了`useGeneratedKeys`属性. (通用Mapper的)
* `Index` 标注索引(不用于主键). (该项目提供)
