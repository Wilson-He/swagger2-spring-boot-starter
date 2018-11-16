package io.github.wilson.swagger.properties.security;

import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Swagger {@link OAuth} properties converter
 *
 * @author Wilson
 * @date 2018/11/10
 */
@Setter
@ToString
public class OAuthProperties {
    private String name;
    private Map<String, String> scopes;
    private List<String> grantTypes;

    public OAuth toOAuth() {
        List<AuthorizationScope> securityScopes = new ArrayList<>(scopes.size());
        scopes.forEach((scope, desc) -> securityScopes.add(new AuthorizationScope(scope, desc)));
        return new OAuth(name, securityScopes, grantTypes.stream().map(GrantType::new).collect(Collectors.toList()));
    }
}
