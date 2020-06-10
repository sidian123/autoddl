package live.sidian.database.autoddl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 全局配置
 */
@Component
public class PropertyConfig {
    @Getter
    @Value("${autoddl.name-style}")
    String nameStyle;
}
