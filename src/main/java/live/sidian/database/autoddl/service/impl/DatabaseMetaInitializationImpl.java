package live.sidian.database.autoddl.service.impl;

import live.sidian.database.autoddl.model.Table;
import live.sidian.database.autoddl.service.DatabaseMetaInitialization;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sidian
 * @date 2020/6/8 20:56
 */
@Component
public class DatabaseMetaInitializationImpl implements DatabaseMetaInitialization {
    @Override
    public Map<String, Table> init() {
        return null;
    }
}
