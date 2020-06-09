package live.sidian.database.autoddl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * 差分之后的补丁
 * 数据格式:
 * {
 * deletedTable:[...],
 * createdTable:[...],
 * modifiedTable:[
 * {
 * deletedIndices:[...],
 * createdIndices:[...],
 * deletedColumns:[...],
 * createdColumns:[...]
 * },
 * ...
 * ]
 * }
 *
 * @author sidian
 * @date 2020/3/22 16:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchSQL {

    /**
     * 待删除的表的补丁
     */
    private List<String> deletedTables = new LinkedList<>();

    /**
     * 待新增的表的补丁
     */
    private List<String> createdTables = new LinkedList<>();

    /**
     * 待修改的表的补丁
     */
    private List<ModifiedTable> modifiedTables = new LinkedList<>();

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("#-------------表删除-----------\n");
        deletedTables.forEach(s -> str.append(s).append(";\n"));
        str.append("#-------------表新增-----------\n");
        createdTables.forEach(s -> str.append(s).append(";\n"));
        str.append("#-------------表修改-----------\n");
        modifiedTables.forEach(modifiedTable -> {
            modifiedTable.getBeforeSQL().forEach(s -> str.append(s).append(";\n"));
            modifiedTable.getDeletedIndices().forEach(s -> str.append(s).append(";\n"));
            modifiedTable.getDeletedColumns().forEach(s -> str.append(s).append(";\n"));
            modifiedTable.getCreatedColumns().forEach(s -> str.append(s).append(";\n"));
            modifiedTable.getModifiedColumns().forEach(s -> str.append(s).append(";\n"));
            modifiedTable.getCreatedIndices().forEach(s -> str.append(s).append(";\n"));
            modifiedTable.getAfterSQL().forEach(s -> str.append(s).append(";\n"));
        });
        return str.toString();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifiedTable {
        private List<String> deletedIndices = new LinkedList<>();
        private List<String> createdIndices = new LinkedList<>();
        private List<String> deletedColumns = new LinkedList<>();
        private List<String> modifiedColumns = new LinkedList<>();
        private List<String> createdColumns = new LinkedList<>();
        /**
         * 该表中必须最后才能执行的
         */
        private List<String> afterSQL = new LinkedList<>();
        /**
         * 该表中必须再之前执行的
         */
        private List<String> beforeSQL = new LinkedList<>();

        public boolean isEmpty() {
            return deletedIndices.isEmpty()
                    && createdIndices.isEmpty()
                    && deletedColumns.isEmpty()
                    && modifiedColumns.isEmpty()
                    && createdColumns.isEmpty()
                    && afterSQL.isEmpty()
                    && beforeSQL.isEmpty();
        }
    }
}
