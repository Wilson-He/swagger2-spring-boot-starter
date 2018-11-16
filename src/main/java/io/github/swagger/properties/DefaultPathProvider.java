package io.github.swagger.properties;

import springfox.documentation.spring.web.paths.AbstractPathProvider;

import javax.servlet.ServletContext;


/**
 * DefaultPathProvider-配置文档导出BasePath
 *
 * @author Wilson
 * @date 2018/10/3
 */
public class DefaultPathProvider extends AbstractPathProvider {

    private final String CONTEXT_PATH;

    DefaultPathProvider(String contextPath) {
        super();
        this.CONTEXT_PATH = contextPath;
    }

    public DefaultPathProvider(ServletContext servletContext) {
        super();
        String contextPath = servletContext.getContextPath();
        this.CONTEXT_PATH = contextPath == null || contextPath.isEmpty() ? "/"
                : servletContext.getContextPath();
    }

    @Override
    protected String applicationPath() {
        return CONTEXT_PATH;
    }

    @Override
    protected String getDocumentationPath() {
        return CONTEXT_PATH;
    }

}
