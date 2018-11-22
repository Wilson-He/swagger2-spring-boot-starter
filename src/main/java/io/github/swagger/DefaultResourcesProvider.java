package io.github.swagger;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 * DefaultResourcesProvider
 *
 * @author Wilson
 */
public class DefaultResourcesProvider implements SwaggerResourcesProvider {
    private List<SwaggerResource> swaggerResources;

    @Override
    public List<SwaggerResource> get() {
        return swaggerResources;
    }

    public void setSwaggerResources(List<SwaggerResource> swaggerResources) {
        this.swaggerResources = swaggerResources;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("swaggerResources{");
        for (SwaggerResource each : swaggerResources) {
            builder.append("[")
                    .append("name=")
                    .append(each.getName())
                    .append(",url=")
                    .append(each.getUrl())
                    .append(",swaggerVersion:")
                    .append(each.getSwaggerVersion())
                    .append("],");
        }
        return builder.deleteCharAt(builder.lastIndexOf(",")).append("}").toString();
    }
}
