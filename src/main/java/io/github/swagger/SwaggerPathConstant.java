package io.github.swagger;

import java.util.Arrays;
import java.util.List;

/**
 * @author Wilson
 */
public interface SwaggerPathConstant {
    String[] PATHS = new String[]{
            "/error",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/swagger-resources",
            "/v3/api-docs"};
    List<String> PATH_LIST = Arrays.asList(
            "/error",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/swagger-resources",
            "/v3/api-docs");
}
