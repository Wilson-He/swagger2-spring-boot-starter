package io.github.wilson.swagger.properties;

import lombok.Setter;
import springfox.documentation.swagger.web.SecurityConfiguration;

import java.util.Map;

/**
 * Swagger {@link SecurityConfiguration} properties converter
 *
 * @author Wilson
 * @date 2018/11/10
 */
@Setter
public class SecurityConfigurationProperties {
    private String clientId;
    private String clientSecret;
    private String realm;
    private String appName;
    private String scopeSeparator;
    private Map<String, Object> additionalQueryStringParams;
    private Boolean useBasicAuthenticationWithAccessCodeGrant;

    public SecurityConfiguration toSecurityConfiguration() {
        return new SecurityConfiguration(clientId, clientSecret, realm, appName, scopeSeparator,
                additionalQueryStringParams, useBasicAuthenticationWithAccessCodeGrant);
    }
}
