package io.github.swagger;


import io.github.swagger.properties.DocketProperties;
import io.github.swagger.properties.SecurityConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * SwaggerAutoConfiguration - swagger-spring-boot自动化配置类
 *
 * @author Wilson
 */
@Configuration
@EnableSwagger2
@Slf4j
@ConfigurationProperties("swagger")
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true")
public class SwaggerAutoConfiguration implements ApplicationContextAware {

    /**
     * Bean factory for this context
     */
    private ConfigurableListableBeanFactory beanFactory;
    /**
     * Bean factory for this context
     */
    private DefaultListableBeanFactory defaultListableBeanFactory;
    private String swaggerUrl;
    @Value("${server.servlet.context-path:/}")
    private String contextPath;
    @Value("${server.port:8080}")
    private String port;
    private DocketProperties docket;
    private Map<String, DocketProperties> dockets;
    private SecurityConfigurationProperties securityConfiguration;
    private Boolean printInit = false;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof AbstractRefreshableApplicationContext) {
            beanFactory = ((AbstractRefreshableApplicationContext) applicationContext).getBeanFactory();
        } else {
            beanFactory = ((GenericApplicationContext) applicationContext).getBeanFactory();
            defaultListableBeanFactory = ((GenericApplicationContext) applicationContext).getDefaultListableBeanFactory();
        }
    }

    @PostConstruct
    public void init() throws NoSuchFieldException, IllegalAccessException {
        if (securityConfiguration != null) {
            SecurityConfiguration configuration = securityConfiguration.toSecurityConfiguration();
            beanFactory.registerSingleton("swaggerSecurityConfiguration", configuration);
            String[] names = beanFactory.getBeanNamesForType(ApiResourceController.class);
            if (names.length == 1 && defaultListableBeanFactory != null) {
                ApiResourceController apiResourceController = (ApiResourceController) defaultListableBeanFactory.getSingleton(names[0]);
                defaultListableBeanFactory.destroySingleton(names[0]);
                Field field = ApiResourceController.class.getDeclaredField("securityConfiguration");
                field.setAccessible(true);
                field.set(apiResourceController, configuration);
                defaultListableBeanFactory.registerSingleton(names[0], apiResourceController);
            }
        }
        List<String> beanNameList = new ArrayList<>();
        if (dockets != null && dockets.size() > 0) {
            dockets.forEach((beanName, properties) -> registerDocket(beanName, properties, beanNameList));
        }
        if (docket != null) {
            registerDocket(DocketProperties.DEFAULT_DOCKET, docket, beanNameList);
        }
        log.info("{} initialization completed, swagger url: {}", beanNameList.toString(), swaggerUrl);
    }

    /**
     * 注册docket到spring容器
     *
     * @param beanName
     * @param properties
     * @param beanNameList
     */
    private void registerDocket(String beanName, DocketProperties properties, List<String> beanNameList) {
        if (printInit) {
            log.info(properties.toString().replaceFirst(DocketProperties.class.getSimpleName(), beanName)
                    .replaceAll("Properties", ""));
        }
        beanFactory.registerSingleton(beanName, properties.toDocket(beanName, contextPath, port));
        beanNameList.add(beanName);
        swaggerUrl = "http://" + (properties.getHost() + ":" + port + contextPath + "/swagger-ui.html").replaceAll("//", "/");
    }

    public void setDocket(DocketProperties docket) {
        this.docket = docket;
    }

    public void setDockets(Map<String, DocketProperties> dockets) {
        this.dockets = dockets;
    }

    public void setSecurityConfiguration(SecurityConfigurationProperties securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    public void setPrintInit(Boolean printInit) {
        this.printInit = printInit;
    }
}
