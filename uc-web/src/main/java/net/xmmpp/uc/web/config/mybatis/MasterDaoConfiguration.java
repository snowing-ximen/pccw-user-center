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

        // ????????????????????????
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

        //sql??????
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
        statFilter.setLogSlowSql(true); //slowSqlMillis????????????SQL?????????????????????????????????slowSqlMillis???????????????
        statFilter.setMergeSql(true); //SQL????????????
        statFilter.setSlowSqlMillis(1000);//slowSqlMillis???????????????3000????????????3??????
        return statFilter;
    }

    /**
     * sql?????????
     * <p>
     * selectWhereAlwayTrueCheck true ??????SELECT?????????WHERE?????????????????????????????????
     * <p>
     * selectHavingAlwayTrueCheck true ??????SELECT?????????HAVING?????????????????????????????????
     * <p>
     * deleteWhereAlwayTrueCheck true ??????DELETE?????????WHERE?????????????????????????????????
     * <p>
     * deleteWhereNoneCheck false ??????DELETE???????????????where???????????????????????????????????????SQL?????????????????????
     * <p>
     * updateWhereAlayTrueCheck true ??????UPDATE?????????WHERE?????????????????????????????????
     * <p>
     * updateWhereNoneCheck false ??????UPDATE???????????????where???????????????????????????????????????SQL?????????????????????
     * <p>
     * conditionAndAlwayTrueAllow false ??????????????????(WHERE/HAVING??????)???????????????AND????????????
     * <p>
     * conditionAndAlwayFalseAllow false ??????????????????(WHERE/HAVING??????)???????????????AND????????????
     * <p>
     * conditionLikeTrueAllow true ??????????????????(WHERE/HAVING??????)???????????????LIKE????????????
     *
     * @return
     */
    @Bean(name = "masterWallFilter")
    @Primary
    public WallFilter wallFilter() {

        WallFilter wallFilter = new WallFilter();
        wallFilter.setLogViolation(true);//????????????????????????SQL??????LOG.error??????
        wallFilter.setThrowException(true);//????????????????????????SQL??????SQLExcepton

        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);//??????????????????SQL
        config.setDeleteWhereNoneCheck(true);//????????????where??????????????????
        config.setUpdateWhereNoneCheck(true);//????????????where??????????????????

        wallFilter.setConfig(config);
        return wallFilter;
    }

    @Bean(name = "masterDruidStatViewServlet")
    @Primary
    public ServletRegistrationBean druidStatViewServlet() {

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //?????????
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");

        //IP????????? (??????????????????deny?????????allow) : ????????????deny????????????:Sorry, you are not permitted to view this page
        //servletRegistrationBean.addInitParameter("deny","127.0.0.1");

        //?????????????????????????????????
//        servletRegistrationBean.addInitParameter("loginUsername", "nicetuan");
//        servletRegistrationBean.addInitParameter("loginPassword", "Nice&Tuan$1dsa");

        //????????????????????????
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean(name = "masterDruidStatFilter")
    @Primary
    public FilterRegistrationBean druidStatFilter() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //??????????????????
        filterRegistrationBean.addUrlPatterns("/*");

        //????????????????????????????????????
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}