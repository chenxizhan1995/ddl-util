package cc.xizhan.demo;

import java.util.ArrayList;
import java.util.List;

public class Table {
    Integer seq;
    String tableName;
    String tableComment;

    List<cc.xizhan.demo.Column> columns;
    public Table(){
        this.columns = new ArrayList<>();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public cc.xizhan.demo.Column addColumn(cc.xizhan.demo.Column column){
        this.columns.add(column);
        column.setSeq(columns.size());
        return column;
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<cc.xizhan.demo.Column> getColumns() {
        return columns;
    }

    public void setColumns(List<cc.xizhan.demo.Column> columns) {
        this.columns = columns;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}
