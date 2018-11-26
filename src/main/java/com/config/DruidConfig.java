package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;


@Configuration
public class DruidConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String passWord;

    @Value("${spring.datasource.tomcat.initial-size}")
    private Integer initialSize;

    @Value("${spring.datasource.tomcat.min-idle}")
    private Integer minIdle;

    @Value("${spring.datasource.tomcat.max-active}")
    private Integer maxActive;

    @Value("${spring.datasource.tomcat.max-wait}")
    private Integer maxWait;

    @Value("${spring.datasource.tomcat.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.tomcat.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${spring.datasource.tomcat.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.tomcat.test-while-idle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.tomcat.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.tomcat.test-on-return}")
    private boolean testOnReturn;

    @Value("${spring.datasource.tomcat.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.filters}")
    private String filters;

    @Value("${druid.login.enabled}")
    private boolean druidLoginEnabled;

    @Value("${druid.login.username}")
    private String druidLoginUsername;

    @Value("${druid.login.password}")
    private String druidLoginPassword;




    @Bean
    public DruidDataSource  getDruidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(passWord);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenConnectErrorMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        try {
            druidDataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }



    @Bean
    public ServletRegistrationBean getServletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        servletRegistrationBean.addInitParameter("deny","192.168.0.1");
        if(druidLoginEnabled){
            servletRegistrationBean.addInitParameter("loginUsername",druidLoginUsername);
            servletRegistrationBean.addInitParameter("loginPassword",druidLoginPassword);
        }
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        /**
         * 添加过滤规则
         */
        filterRegistrationBean.addUrlPatterns("/*");
        /**
         *  添加不需要忽略的格式信息.
         */
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}

