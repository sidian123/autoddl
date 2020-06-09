package live.sidian.database.autoddl.service;

import live.sidian.database.autoddl.model.Table;

import java.util.Map;

/**
 * Java Model信息索引初始化
 *
 * @author sidian
 * @date 2020/6/8 19:13
 */
public interface ModelMetaInitialization {

    /**
     * Java Model信息索引
     *
     * @param basePackages 要扫描的包
     * @return 代表Java Model信息的MetaData对象
     */
    Map<String, Table> init(String[] basePackages);
}
