/*
 * Copyright (C) 2016 alchemystar, Inc. All Rights Reserved.
 */
package live.sidian.database.autoddl.model;

import live.sidian.database.autoddl.constant.IndexType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Index {

    /**
     * 与索引关联的列名
     */
    @Builder.Default
    private List<String> columns = new ArrayList<>();

    /**
     * 索引名
     */
    private String name;
    /**
     * 索引类型
     */
    private IndexType type;
    /**
     * 索引优先级, 数字越小, 优先级越高.
     * 打印或执行SQL时会用到
     */
    @Builder.Default
    private int priority = 1;

    /**
     * 生成索引名.
     * 主键名无需使用该方法, 可直接设置为PRIMARY
     *
     * @param tableName 表名
     * @param fieldName 字段名
     */
    public void generateName(String tableName, String fieldName) {
        assert type != IndexType.PRIMARY;
        this.name = String.format(
                "tb_%s_%s_%s",
                tableName,
                fieldName,
                type == IndexType.INDEX ? "index" : "uindex"
        );
    }

}
