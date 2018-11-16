package io.github.wilson.swagger.properties;

import lombok.Setter;
import lombok.ToString;
import io.github.wilson.swagger.properties.security.ApiKeyProperties;
import io.github.wilson.swagger.properties.security.OAuthProperties;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityScheme;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger {@link springfox.documentation.service.SecurityScheme} implements class list
 *
 * @author Wilson
 * @date 2018/11/10
 */
@Setter
@ToString
class SecuritySchemeProperties {
    /**
     * {@link BasicAuth}.name list
     */
    private List<String> basicAuthList = new ArrayList<>();
    /**
     * {@link OAuth} properties list
     */
    private List<OAuthProperties> oauthList = new ArrayList<>();
    /**
     * {@link ApiKey} properties list
     */
    private List<ApiKeyProperties> apiKeyList = new ArrayList<>();

    public List<SecurityScheme> toSecuritySchemes() {
        List<SecurityScheme> list = new ArrayList<>(basicAuthList.size() + oauthList.size() + apiKeyList.size());
        basicAuthList.forEach(e -> list.add(new BasicAuth(e)));
        list.addAll(oauthList.stream().map(OAuthProperties::toOAuth).collect(Collectors.toList()));
        list.addAll(apiKeyList.stream().map(ApiKeyProperties::toApiKey).collect(Collectors.toList()));
        return list;
    }
}
