package com.babyTalk.main.generate;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerate {

        //生成路径，从项目根目录开始
        private static final String PACKAGE_PATH = "/core/src/main/java";
        //作者
        private static final String AUTHOR = "cst";

        private static final String DB_URL = "jdbc:mysql://1.117.147.236:3306/baby_talk?useSSL=false";
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/test?useSSL=false";

        private static final String DB_USER = "root";

        private static final String DB_PWD = "111111";

        public static void main(String[] args) {
            // 1、创建代码生成器
            AutoGenerator mpg = new AutoGenerator();

            // 2、全局配置
            GlobalConfig gc = new GlobalConfig();
            String projectPath = System.getProperty("user.dir");
            gc.setOutputDir(projectPath + PACKAGE_PATH );//生成路径
            gc.setAuthor(AUTHOR);
            gc.setOpen(false); //生成后是否打开资源管理器

            gc.setFileOverride(false); //重新生成时文件是否覆盖
            gc.setServiceName("%sService");    //去掉Service接口的首字母I
            gc.setIdType(IdType.AUTO); //主键策略
            gc.setDateType(DateType.SQL_PACK);//定义生成的实体类中日期类型
            gc.setSwagger2(true);//开启Swagger2模式
            gc.setSwagger2(false);

            mpg.setGlobalConfig(gc);

            // 3、数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl(DB_URL);//√
            dsc.setDriverName("com.mysql.cj.jdbc.Driver");
            dsc.setUsername(DB_USER);//√
            dsc.setPassword(DB_PWD);//√
            dsc.setDbType(DbType.MYSQL);
            mpg.setDataSource(dsc);

            // 4、包配置
            PackageConfig pc = new PackageConfig();
            pc.setModuleName("feedback"); //模块名//√
            pc.setParent("com.babyTalk.core");//√

            pc.setController("controller");
            pc.setEntity("entity");
            pc.setService("service");
            pc.setMapper("mapper");
            mpg.setPackageInfo(pc);

            // 5、策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setInclude("feedback");//表名称//√
//        strategy.setInclude("edu_teacher","");//表名称//√
            strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
            strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

            strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
            strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

            strategy.setRestControllerStyle(true); //restful api风格控制器
            strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

            mpg.setStrategy(strategy);


            // 6、执行
            mpg.execute();
        }

    }
