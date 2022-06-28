# 根据表定义，生成表结构Excel文档

对 MySQL 有效。

## 使用方式（推荐）
1. 更改 src/test/resources/query.sql，填写要处理的表名
2. 更改 F:\nwork\ddl-util\springboot-app-2.7.1\src\main\resources\application.properties
   设置数据库连接信息
3. 执行测试用例 cc.xizhan.demo.AppTest.test3()，就能在 target/ 下看到生成的 餐饮管理-表结构-yyyyMMddTHHmmss.xlsx 文件。

## 使用方式（后备）
第一种使用方式不可行时（比如，不方便连接数据库），使用这种方式。
1. 使用 DataGrip 连接数据库
2. 执行 使用 src/main/resources/sql/query.sql 文件中的语句（针对MySQL），该语句有两个查询
    1. 查询表清单
    2. 查询字段清单
3. 将执行结果，选择“下载”按钮，把表结构下载为两个Excle文件，命名为 table.xlsx和 column.xlsx,
   把这两个文件放在 src/test/resources/ 下
4. 执行测试用 cc.xizhan.demo.AppTest.test2()，就能在 target/ 下看到生成的 餐饮管理-表结构-yyyyMMddTHHmmss.xlsx 文件。
