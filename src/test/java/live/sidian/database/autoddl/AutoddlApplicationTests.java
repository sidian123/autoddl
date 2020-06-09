package live.sidian.database.autoddl;

import live.sidian.database.autoddl.service.impl.ModelMetaInitializationImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutoddlApplicationTests {

    @Autowired
    ModelMetaInitializationImpl modelMetaInitialization;

    @Test
    void contextLoads() {
//        modelMetaInitialization.init(new String[]{"live.sidian.database.autoddl.testModel"});
    }

}
