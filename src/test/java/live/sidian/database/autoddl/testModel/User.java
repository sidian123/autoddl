package live.sidian.database.autoddl.testModel;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author sidian
 * @date 2020/6/8 19:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "test4")
public class User {
    @Column
    Integer id;
    @Column
    String name;
    @Column
    String password;
    Date createTime;
}
