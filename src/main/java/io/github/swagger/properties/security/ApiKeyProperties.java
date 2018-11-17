package io.github.swagger.properties.security;

import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.ApiKey;

/**
 * Swagger {@link ApiKey} properties converter
 *
 * @author Wilson
 */
@Setter
@ToString
public class ApiKeyProperties {
    private String name;
    private String keyName;
    private String passAs;

    public ApiKey toApiKey() {
        return new ApiKey(name, keyName, passAs);
    }
}
