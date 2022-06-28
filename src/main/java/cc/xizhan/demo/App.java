package cc.xizhan.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    /**
     * 用 EasyExcel读取Excel
     * 以表名做key，Table 做值
     *
     * @param in
     * @return
     */
    public Map<String, cc.xizhan.demo.Table> read(InputStream in) {

        Map<String, cc.xizhan.demo.Table> map = new LinkedHashMap<>();

        EasyExcel.read(in, cc.xizhan.demo.Column.class, new PageReadListener<cc.xizhan.demo.Column>(columnList -> {
            for (cc.xizhan.demo.Column column : columnList) {
                String tableName = column.getTableName();
                if (!map.containsKey(tableName)) {
                    cc.xizhan.demo.Table table = new cc.xizhan.demo.Table();
                    table.setTableName(tableName);
                    table.setTableComment(column.getTableComment());
                    map.put(tableName, table);
                    System.out.println("table name: " + tableName);
                }
                map.get(tableName).addColumn(column);
            }
        })).sheet().doRead();
        logger.info("done:{}", map.size());
        return map;
    }
    /**
     * 用 EasyExcel读取Excel
     * 以表名做key，Table 做值
     *
     * @param columns 列定义
     * @return
     */
    public Map<String, cc.xizhan.demo.Table> parse(List<Column> columns) {

        Map<String, cc.xizhan.demo.Table> map = new LinkedHashMap<>();
        for (cc.xizhan.demo.Column column : columns) {
            String tableName = column.getTableName();
            if (!map.containsKey(tableName)) {
                cc.xizhan.demo.Table table = new cc.xizhan.demo.Table();
                table.setTableName(tableName);
                table.setTableComment(column.getTableComment());
                map.put(tableName, table);
                table.setSeq(map.size());
                System.out.println("table name: " + tableName);
            }
            map.get(tableName).addColumn(column);
        }
        logger.info("done:{}", map.size());
        return map;
    }
    /**
     * 复制sheet页
     */
    public InputStream cloneSheet(InputStream in, List<String> sheetNames) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        workbook.setSheetName(0,"目录");
        workbook.setSheetName(1, sheetNames.get(0));
        for (int i = 1; i < sheetNames.size(); i++) {
            workbook.cloneSheet(1, sheetNames.get(i));
        }
        workbook.write(bos);
        InputStream in2 = new ByteArrayInputStream(bos.toByteArray());
        return in2;
    }
    /**
     * 实际导出
     */
    public void export(Map<String, Table> tables , InputStream template, File output){

        ExcelWriter excelWriter = EasyExcel.write(output, Column.class)
                .withTemplate(template)
                .build();
        // 写入
        int i = 0;
        for (String s : tables.keySet()) {
            List<Column> data = tables.get(s).getColumns();
            WriteSheet sheet = EasyExcel.writerSheet(++i)
                    .needHead(false)
                    .build();
            excelWriter.fill(data, sheet);
        }
        // 目录
        WriteSheet content = EasyExcel.writerSheet(0)
                .needHead(false)
                .build();

        excelWriter.fill(tables.values(), content);

        excelWriter.finish();
    }
}
