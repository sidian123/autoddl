package live.sidian.database.autoddl.service;

import live.sidian.database.autoddl.model.PatchSQL;
import live.sidian.database.autoddl.model.Table;

import java.util.Map;

/**
 * 元数据差分器
 *
 * @author sidian
 * @date 2020/6/8 19:33
 */
public interface MetaDataDiff {
    /**
     * 找出target相对于source的差异部分
     *
     * @param source 源元数据
     * @param target 目标元数据
     * @return target相对于source的差分表
     */
    PatchSQL diff(Map<String, Table> source, Map<String, Table> target);
}
