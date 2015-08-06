package ua.kiev.test.testwebmvc.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by lutay.d on 06.08.2015.
 */
@Configuration
@EnableJpaRepositories(basePackages={"ua.kiev.test.testwebmvc.repository"})
@EnableTransactionManagement
public class TestJpaConfig  implements DisposableBean {

    private EmbeddedDatabase ed;

    @Bean(name="dataSource")
    public EmbeddedDatabase hsqlInMemory() {

        if ( this.ed == null ) {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            this.ed = builder.setType(EmbeddedDatabaseType.HSQL)
                    .build();
        }

        return this.ed;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){

        LocalContainerEntityManagerFactoryBean lcemfb
                = new LocalContainerEntityManagerFactoryBean();

        lcemfb.setDataSource(this.hsqlInMemory());
        lcemfb.setPackagesToScan(new String[] {"ua.kiev.test.testwebmvc.model"});
        lcemfb.setPersistenceUnitName("MyPU");

        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        lcemfb.setJpaVendorAdapter(va);

        Properties ps = new Properties();
        ps.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        ps.put("hibernate.hbm2ddl.auto", "create");
        lcemfb.setJpaProperties(ps);

        lcemfb.afterPropertiesSet();

        return lcemfb;

    }

    @Bean
    public PlatformTransactionManager transactionManager(){

        JpaTransactionManager tm = new JpaTransactionManager();

        tm.setEntityManagerFactory(
                this.entityManagerFactory().getObject() );

        return tm;

    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Override
    public void destroy() {

        if ( this.ed != null ) {
            this.ed.shutdown();
        }

    }

}
