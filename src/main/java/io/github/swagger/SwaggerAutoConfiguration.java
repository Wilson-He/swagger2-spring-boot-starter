package io.github.swagger;


import io.github.swagger.properties.DocketProperties;
import io.github.swagger.properties.SecurityConfigurationProperties;
import lombok.Setter;
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
import org.springframework.util.CollectionUtils;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;


/**
 * SwaggerAutoConfiguration - swagger-spring-boot自动化配置类
 *
 * @author Wilson
 */
@Configuration
@EnableSwagger2
@Slf4j
@ConfigurationProperties("swagger")
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true", matchIfMissing = true)
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
    @Setter
    private List<String> profiles;
    private DocketProperties docket;
    private Map<String, DocketProperties> dockets;
    private SecurityConfigurationProperties securityConfiguration;
    private Boolean printInit = false;
    private DefaultResourcesProvider resourcesProvider;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        if (applicationContext instanceof AbstractRefreshableApplicationContext) {
            beanFactory = ((AbstractRefreshableApplicationContext) applicationContext).getBeanFactory();
        } else {
            beanFactory = ((GenericApplicationContext) applicationContext).getBeanFactory();
            defaultListableBeanFactory = ((GenericApplicationContext) applicationContext).getDefaultListableBeanFactory();
        }
    }

    @PostConstruct
    public void init() throws NoSuchFieldException, IllegalAccessException {
        // 若配置了swagger.profiles且与实际运行环境不一致则不初始化,不配则默认初始化
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        if (!CollectionUtils.isEmpty(profiles) && !CollectionUtils.containsAny(profiles, Arrays.asList(activeProfiles))) {
            return;
        }
        if (hasDocket()) {
            return;
        }
        registerSecurityConfiguration();
        registerResourcesProvider();
        registerDocket();
    }

    /**
     * 判断是否已含Docket bean
     *
     * @return
     */
    private boolean hasDocket() {
        if (beanFactory.containsBean(DocketProperties.DEFAULT_DOCKET)) {
            return true;
        }
        if (dockets == null || dockets.isEmpty()) {
            return false;
        }
        Set<String> docketNames = dockets.keySet();
        for (String name : docketNames) {
            if (beanFactory.containsBean(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 注册Docket Bean
     */
    private void registerDocket() {
        List<String> beanNameList = new ArrayList<>();
        if (dockets != null && dockets.size() > 0) {
            dockets.forEach((beanName, properties) -> registerDocket(beanName, properties, beanNameList));
        } else if (!beanFactory.containsBean(DocketProperties.DEFAULT_DOCKET)) {
            registerDocket(DocketProperties.DEFAULT_DOCKET, docket != null ? docket : new DocketProperties(), beanNameList);
            if (docket != null) {
                beanFactory.destroyBean(docket);
            }
        }
        log.info(String.format("%s init completed, url: %s",
                beanNameList.isEmpty() ? "" : beanNameList.toString() + " ", swaggerUrl));
    }

    /**
     * 注册Swagger SecurityConfiguration安全配置bean
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void registerSecurityConfiguration() throws NoSuchFieldException, IllegalAccessException {
        if (securityConfiguration != null) {
            SecurityConfiguration configuration = securityConfiguration.toSecurityConfiguration();
            beanFactory.registerSingleton("swaggerSecurityConfiguration", configuration);
            apiResourceControllerConfig("securityConfiguration", configuration);
        }
    }


    /**
     * 注册SwaggerResourcesProvider bean
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void registerResourcesProvider() throws NoSuchFieldException, IllegalAccessException {
        if (resourcesProvider != null) {
            apiResourceControllerConfig("swaggerResources", resourcesProvider);
            log.info(resourcesProvider.toString());
            swaggerUrl = "http://localhost:" + port + (contextPath + "/swagger-ui.html").replaceAll("//", "/");
        }
    }

    /**
     * ApiResourceController bean属性配置
     *
     * @param fieldName
     * @param fieldValue
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void apiResourceControllerConfig(String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        String[] names = beanFactory.getBeanNamesForType(ApiResourceController.class);
        if (names.length == 1 && defaultListableBeanFactory != null) {
            ApiResourceController apiResourceController = (ApiResourceController) defaultListableBeanFactory.getSingleton(names[0]);
            defaultListableBeanFactory.destroySingleton(names[0]);
            Field field = ApiResourceController.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(apiResourceController, fieldValue);
            defaultListableBeanFactory.registerSingleton(names[0], apiResourceController);
        }
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
        beanFactory.registerSingleton(beanName, properties.toDocket(beanName, contextPath));
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

    public void setResourcesProvider(DefaultResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
    }
}
