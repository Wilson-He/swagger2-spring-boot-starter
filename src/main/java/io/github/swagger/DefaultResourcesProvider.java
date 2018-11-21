package io.github.swagger;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * DefaultResourcesProvider
 *
 * @author Wilson
 * @date 2018/11/20
 */
@Component("resourcesProvider")
@Primary
@ConfigurationProperties("swagger.resources-provider")
public class DefaultResourcesProvider implements SwaggerResourcesProvider, ApplicationContextAware {
    private List<SwaggerResource> swaggerResources;
    /**
     * Bean factory for this context
     */
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @PostConstruct
    public void init() {
        if(swaggerResources == null || swaggerResources.isEmpty() && defaultListableBeanFactory != null){
            defaultListableBeanFactory.destroySingleton("resourcesProvider");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof GenericApplicationContext) {
            defaultListableBeanFactory = ((GenericApplicationContext) applicationContext).getDefaultListableBeanFactory();
        }
    }

    @Override
    public List<SwaggerResource> get() {
        return swaggerResources;
    }

    public void setSwaggerResources(List<SwaggerResource> swaggerResources) {
        this.swaggerResources = swaggerResources;
    }
}
