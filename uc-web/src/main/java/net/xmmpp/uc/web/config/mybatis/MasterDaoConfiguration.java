package net.xmmpp.uc.web.config.mybatis;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.ArrayList;
import java.util.List;


@Configuration
@MapperScan(basePackages = {
        "net.xmmpp.uc.dao"
}, sqlSessionFactoryRef = "mastSqlSessionFactory")
public class MasterDaoConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.connection-init-sqls}")
    private List<String> connectionInitSqls;

    @Bean(name = "mastSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setVfs(SpringBootVFS.class);

/*
        DialectInterceptor interceptor = new DialectInterceptor();
        interceptor.setDialect(new MysqlDialect());
        bean.setPlugins(new Interceptor[]{interceptor});
*/

        // 配置映射文件目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setConfigLocation(resolver.getResource("classpath:mybatis.xml"));
            Resource[] mybatisRootMapperXml = resolver.getResources("classpath*:*Mapper.xml");
            Resource[] mybatisMapperXml = resolver.getResources("classpath*:/**/*Mapper.xml");
            bean.setMapperLocations(ArrayUtils.addAll(mybatisMapperXml, mybatisRootMapperXml));
            return bean.getObject();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Bean(name = "mastTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());

        return transactionManager;
    }

    @Bean(name = "masterDataSource", initMethod = "init", destroyMethod = "close")
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);

        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);

        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setValidationQuery(validationQuery);

        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setConnectionInitSqls(connectionInitSqls);

        //sql监控
        List<Filter> filters = new ArrayList<>();
        filters.add(statFilter());
        filters.add(wallFilter());
        datasource.setProxyFilters(filters);

        return datasource;
    }

    @Bean(name = "masterStatFilter")
    @Primary
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true); //slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢。
        statFilter.setMergeSql(true); //SQL合并配置
        statFilter.setSlowSqlMillis(1000);//slowSqlMillis的缺省值为3000，也就是3秒。
        return statFilter;
    }

    /**
     * sql防火墙
     * <p>
     * selectWhereAlwayTrueCheck true 检查SELECT语句的WHERE子句是否是一个永真条件
     * <p>
     * selectHavingAlwayTrueCheck true 检查SELECT语句的HAVING子句是否是一个永真条件
     * <p>
     * deleteWhereAlwayTrueCheck true 检查DELETE语句的WHERE子句是否是一个永真条件
     * <p>
     * deleteWhereNoneCheck false 检查DELETE语句是否无where条件，这是有风险的，但不是SQL注入类型的风险
     * <p>
     * updateWhereAlayTrueCheck true 检查UPDATE语句的WHERE子句是否是一个永真条件
     * <p>
     * updateWhereNoneCheck false 检查UPDATE语句是否无where条件，这是有风险的，但不是SQL注入类型的风险
     * <p>
     * conditionAndAlwayTrueAllow false 检查查询条件(WHERE/HAVING子句)中是否包含AND永真条件
     * <p>
     * conditionAndAlwayFalseAllow false 检查查询条件(WHERE/HAVING子句)中是否包含AND永假条件
     * <p>
     * conditionLikeTrueAllow true 检查查询条件(WHERE/HAVING子句)中是否包含LIKE永真条件
     *
     * @return
     */
    @Bean(name = "masterWallFilter")
    @Primary
    public WallFilter wallFilter() {

        WallFilter wallFilter = new WallFilter();
        wallFilter.setLogViolation(true);//对被认为是攻击的SQL进行LOG.error输出
        wallFilter.setThrowException(true);//对被认为是攻击的SQL抛出SQLExcepton

        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);//允许执行多条SQL
        config.setDeleteWhereNoneCheck(true);//不允许无where删除语句执行
        config.setUpdateWhereNoneCheck(true);//不允许无where更新语句执行

        wallFilter.setConfig(config);
        return wallFilter;
    }

    @Bean(name = "masterDruidStatViewServlet")
    @Primary
    public ServletRegistrationBean druidStatViewServlet() {

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");

        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page
        //servletRegistrationBean.addInitParameter("deny","127.0.0.1");

        //登录查看信息的账号密码
//        servletRegistrationBean.addInitParameter("loginUsername", "nicetuan");
//        servletRegistrationBean.addInitParameter("loginPassword", "Nice&Tuan$1dsa");

        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean(name = "masterDruidStatFilter")
    @Primary
    public FilterRegistrationBean druidStatFilter() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");

        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}