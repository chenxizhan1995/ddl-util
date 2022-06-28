package cc.xizhan.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class AppTest {
    private App app = new App();
    @Test
    public void hello(){
        System.out.println("hello");
    }

    /**
     * 复制sheet页
     *
     * @throws IOException
     */
    @Test
    public void testClone() throws IOException {
        InputStream originTemplateIn = this.getClass().getResourceAsStream("/template.xlsx");
        List<String> names = Arrays.asList("hello", "world", "测试");
        InputStream inputStream = app.cloneSheet(originTemplateIn, names);
        StreamUtils.copy(inputStream, new FileOutputStream(new File("target/cloned.xlsx")));
    }

    @Test
    public void test2() throws IOException {
        InputStream file = this.getClass().getResourceAsStream("/column.xlsx");

        InputStream originTemplate = this.getClass().getResourceAsStream("/template.xlsx");

        Map<String, Table> tables = app.read(file);

        // 每个表的中文名字,做sheet页的名字
        List<String> sheetNames = tables.entrySet()
                .stream()
                .map(v -> v.getValue().getTableComment())
                .collect(Collectors.toList());

        // 复制表的模板
        InputStream in2 = app.cloneSheet(originTemplate, sheetNames);
        // 导出的Excel文件的输出路径
        File output = Paths.get("target", "餐饮管理-表结构-" + time() + ".xlsx").toFile();

        ExcelWriter excelWriter = EasyExcel.write(output, Column.class)
                .withTemplate(in2)
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

        excelWriter.finish();
        System.out.println(file.toString());
    }
    @Resource
    private DbService dbService;
    @Test
    public void test3() throws IOException {

        InputStream originTemplate = this.getClass().getResourceAsStream("/template.xlsx");

        Map<String, Table> tables = app.parse(dbService.queryTableDefinition());

        // 每个表的中文名字,做sheet页的名字
        List<String> sheetNames = tables.values()
                .stream()
                .map(v -> v.getTableComment().split("[,，]")[0])
                .collect(Collectors.toList());

        // 复制表的模板
        InputStream in2 = app.cloneSheet(originTemplate, sheetNames);
        // 导出的Excel文件的输出路径
        File output = Paths.get("target", "餐饮管理-表结构-" + time() + ".xlsx").toFile();
        app.export(tables, in2, output);

        System.out.println(output.toString());
    }

    private static String time() {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(new Date());
    }
}