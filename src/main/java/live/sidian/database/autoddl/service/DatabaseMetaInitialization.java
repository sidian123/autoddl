package live.sidian.database.autoddl.service;

import live.sidian.database.autoddl.model.Table;

import java.util.Map;

/**
 * 数据库信息索引初始化
 *
 * @author sidian
 * @date 2020/6/8 19:15
 */
public interface DatabaseMetaInitialization {

    Map<String, Table> init();

}
