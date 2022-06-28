package cc.xizhan.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class DbService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    public void hello(){
        String result = jdbcTemplate.queryForObject("select user() from dual", String.class);
        System.out.println(result);
    }
    /**
     * 查询表结构
     */
    public List<Column> queryTableDefinition() throws IOException {
        String sql = StreamUtils.copyToString(new ClassPathResource("/sql/query.sql").getInputStream(),
                StandardCharsets.UTF_8);
        List<Column> columns = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<Column>(Column.class));
        return columns;
    }
}
