package live.sidian.database.autoddl.constant;

/**
 * JDBC与Java类型的转换关系
 */
public enum  JDBCType {
    Boolean(java.lang.Boolean.class,"boolean"),
    Integer(java.lang.Integer.class,"int"),
    String(java.lang.String.class,"varchar");

    private final Class<?> javaType;
    private final String jdbcType;

    JDBCType(Class<?> javaType, String jdbcType) {
        this.javaType=javaType;
        this.jdbcType=jdbcType;
    }

}
