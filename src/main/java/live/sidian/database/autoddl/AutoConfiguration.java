package live.sidian.database.autoddl;

import live.sidian.database.autoddl.model.PatchSQL;
import live.sidian.database.autoddl.model.Table;
import live.sidian.database.autoddl.service.DatabaseMetaInitialization;
import live.sidian.database.autoddl.service.MetaDataDiff;
import live.sidian.database.autoddl.service.ModelMetaInitialization;
import live.sidian.database.autoddl.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author sidian
 * @date 2020/6/8 19:10
 */
@Configuration
@ComponentScan("live.sidian.database.autoddl")
public class AutoConfiguration {
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    @Autowired
    ModelMetaInitialization modelMetaInitialization;
    @Autowired
    DatabaseMetaInitialization databaseMetaInitialization;
    @Autowired
    MetaDataDiff metaDataDiff;

    @PostConstruct
    public void init() throws SQLException {
        // 打开数据库连接
        SqlUtil.open(url, username, password);
        // 初始化元数据
        Map<String, Table> source = modelMetaInitialization.init(new String[]{"live.sidian.database.autoddl.testModel"});
        Map<String, Table> target = databaseMetaInitialization.init();
        // 获取差分
        PatchSQL diff = metaDataDiff.diff(source, target);
        //打印
        System.out.println(diff.toString());
        // 关闭数据库连接
        SqlUtil.close();
    }
}
