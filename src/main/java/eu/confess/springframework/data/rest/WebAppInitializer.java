package eu.confess.springframework.data.rest;

import java.net.URI;
import java.net.UnknownHostException;
import javax.sql.DataSource;

import com.mongodb.Mongo;
import eu.confess.springframework.data.rest.domain.Customer;
import eu.confess.springframework.data.rest.domain.CustomerResourceProcessor;
import eu.confess.springframework.data.rest.domain.DataLoader;
import eu.confess.springframework.data.rest.domain.EmailValidator;
import eu.confess.springframework.data.rest.domain.OrderResourceProcessor;
import eu.confess.springframework.data.rest.domain.ShoppingCartResourceProcessor;
import eu.confess.springframework.data.rest.repository.CustomerRepository;
import eu.confess.springframework.data.rest.repository.OrderRepository;
import eu.confess.springframework.data.rest.repository.PaymentRepository;
import eu.confess.springframework.data.rest.repository.ShoppingCartRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.config.RepositoryRestConfiguration;
import org.springframework.data.rest.repository.context.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfig.class, RestConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected javax.servlet.Filter[] getServletFilters() {
		return new javax.servlet.Filter[]{
				new OpenEntityManagerInViewFilter(),
				new DelegatingFilterProxy("springSecurityFilterChain")
		};
	}

	@Configuration
	@EnableEntityLinks
	public static class WebConfig extends WebMvcConfigurationSupport {

		@Bean public PaymentController paymentController() {
			return new PaymentController();
		}

		@Override protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
			configurer.defaultContentType(MediaType.APPLICATION_JSON);
		}
	}

	@Configuration
	public static class RestConfig extends RepositoryRestMvcConfiguration {

		@Bean public CustomerResourceProcessor customerResourceProcessor() {
			return new CustomerResourceProcessor();
		}

		@Bean public ShoppingCartResourceProcessor shoppingCartResourceProcessor() {
			return new ShoppingCartResourceProcessor();
		}

		@Bean public OrderResourceProcessor orderResourceProcessor() {
			return new OrderResourceProcessor();
		}

		@Bean public MessageSource messageSource() {
			ReloadableResourceBundleMessageSource msgsrc = new ReloadableResourceBundleMessageSource();
			msgsrc.setBasenames("WEB-INF/messages/ValidationMessages");
			msgsrc.setFallbackToSystemLocale(false);
			return msgsrc;
		}

		@Bean public LocaleResolver localeResolver() {
			CookieLocaleResolver clr = new CookieLocaleResolver();
			clr.setCookieName("lang");
			return clr;
		}

		@Override
		protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			config.setBaseUri(URI.create("http://localhost:8080"));

			config.setResourceMappingForRepository(CustomerRepository.class)
			      .setPath("customers")
			      .setRel("customers")
			      .setExported(true);
			config.addResourceMappingForDomainType(Customer.class)
			      .addResourceMappingFor("email")
			      .setExported(false);
		}

		@Override
		protected void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
			EmailValidator ev = new EmailValidator();
			validatingListener.addValidator("beforeCreate", ev)
			                  .addValidator("beforeSave", ev);
		}

	}

	@Configuration
	@Import({JpaConfig.class, MongoConfig.class})
	@ImportResource("classpath:META-INF/spring/springSecurity.xml")
	public static class AppConfig {

		@Bean(initMethod = "loadData") public DataLoader dataLoader() {
			return new DataLoader();
		}

	}

	@Configuration
	@EnableJpaRepositories(excludeFilters = {
			@ComponentScan.Filter(value = {ShoppingCartRepository.class},
			                      type = FilterType.ASSIGNABLE_TYPE)
	})
	@EnableTransactionManagement
	public static class JpaConfig {

		@Bean
		public DataSource dataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.HSQL).build();
		}

		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			vendorAdapter.setDatabase(Database.HSQL);
			vendorAdapter.setGenerateDdl(true);

			LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
			factory.setJpaVendorAdapter(vendorAdapter);
			factory.setPackagesToScan(getClass().getPackage().getName());
			factory.setDataSource(dataSource());

			factory.afterPropertiesSet();

			return factory;
		}

		@Bean
		public JpaDialect jpaDialect() {
			return new HibernateJpaDialect();
		}

		@Bean
		public PlatformTransactionManager transactionManager() {
			JpaTransactionManager txManager = new JpaTransactionManager();
			txManager.setEntityManagerFactory(entityManagerFactory().getObject());
			return txManager;
		}

	}

	@Configuration
	@EnableMongoRepositories(excludeFilters = {
			@ComponentScan.Filter(value = {CustomerRepository.class, OrderRepository.class, PaymentRepository.class},
			                      type = FilterType.ASSIGNABLE_TYPE)
	})
	public static class MongoConfig {

		@Bean public MongoTemplate mongoTemplate() throws UnknownHostException {
			return new MongoTemplate(new Mongo("localhost"), "confess");
		}

	}

}
