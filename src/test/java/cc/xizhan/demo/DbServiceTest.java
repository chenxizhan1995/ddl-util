package cc.xizhan.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbServiceTest {
    @Resource
    private DbService dbService;
    @Test
    public void helloTest() {
        dbService.hello();
    }

    @Test
    public void test2() throws IOException {
        List<Column> columns = dbService.queryTableDefinition();
        System.out.println(columns.size());
    }

}