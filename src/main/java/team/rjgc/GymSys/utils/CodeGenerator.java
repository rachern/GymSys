package team.rjgc.GymSys.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ReMidDream
 * @date 2019/1/10 10:29
 **/
public class CodeGenerator {

    public static void main(String[] args) {
        GlobalConfig config = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        config.setActiveRecord(true) // 是否支持AR模式
                .setOutputDir(projectPath + "/src/main/java") // 生成路径
                .setFileOverride(true) // 文件覆盖
                .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
                .setBaseResultMap(true)//生成基本的resultMap
                .setBaseColumnList(true);//生成基本的SQL片段

        //2. 数据源配置
        DataSourceConfig dsConfig  = new DataSourceConfig();
        dsConfig.setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/gymsys")
                .setUsername("root")
                .setPassword("admin");

        String[] table = {"match_info"};

        //3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                .setEntityLombokModel(true)
                // 控制实体类是否使用lombok注解，使用后将生成
                // @Data
                // @EqualsAndHashCode(callSuper = false)
                // @Accessors(chain = true)三个注解，不推荐使用
                .setInclude(table);  // 生成的表

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("team.rjgc.GymSys")
                .setMapper("mapper")//dao
                .setService("service")//servcie
                .setController("controller")//controller
                .setEntity("entity");


        // 5. mapper.xml位置配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        TemplateConfig templateConfig = new TemplateConfig();
        // 如果模板引擎是 velocity
        templateConfig.setXml(null);
//        String templatePath = "/templates/mapper.xml.ftl";
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        templateConfig.setController("templatesMybatis/controller.java.vm" );

        //6. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig)
                .setCfg(cfg)
                .setTemplate(templateConfig);

        //7. 执行
        ag.execute();
    }
}
