package io.github.wilson.swagger.properties;

import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link SecurityReference} properties converter
 *
 * @author Wilson
 * @date 2018/9/29
 */
@Setter
@ToString
public class SecurityReferenceProperties {
    private String reference;
    /**
     * key-{@link AuthorizationScope}.scope,value-{@link AuthorizationScope}.description
     */
    private Map<String, String> scopes;

    public SecurityReference toSecurityReference() {
        List<AuthorizationScope> list = new ArrayList<>(scopes.size());
        scopes.forEach((scope, desc) -> list.add(new AuthorizationScope(scope, desc)));
        return new SecurityReference(reference, list.toArray(new AuthorizationScope[0]));
    }
}
