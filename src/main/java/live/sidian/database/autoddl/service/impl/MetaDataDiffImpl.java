package live.sidian.database.autoddl.service.impl;

import live.sidian.database.autoddl.model.PatchSQL;
import live.sidian.database.autoddl.model.Table;
import live.sidian.database.autoddl.service.MetaDataDiff;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sidian
 * @date 2020/6/8 20:58
 */
@Component
public class MetaDataDiffImpl implements MetaDataDiff {
    /**
     * 找出target相对于source的差异部分
     *
     * @param source 源元数据
     * @param target 目标元数据
     * @return target相对于source的差分表
     */
    @Override
    public PatchSQL diff(Map<String, Table> source, Map<String, Table> target) {
        PatchSQL patchSQL = new PatchSQL();
        return patchSQL;
    }
}
