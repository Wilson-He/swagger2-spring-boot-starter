package io.github.swagger.properties;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpMethod;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link SecurityContext} properties converter
 *
 * @author Wilson
 */
@Setter
@ToString
public class SecurityContextProperties {
    private List<SecurityReferenceProperties> securityReferences;
    /**
     * 相或
     */
    private List<String> pathSelectors = new ArrayList<>();
    /**
     * http请求方法,or数组相或,[GET,PUT,POST,DELETE,HEAD...],{@link org.springframework.http.HttpMethod}
     */
    private List<HttpMethod> methodSelectors;

    public SecurityContext toSecurityContext() {
        Predicate<HttpMethod> httpMethodPredicate = methodSelectors.stream()
                .map(Predicates::equalTo)
                .reduce(Predicates::or)
                .orElse(Predicates.alwaysTrue());
        Predicate<String> str = Predicates.or();
        Predicate selectorPredicate = pathSelectors.isEmpty() ? Predicates.alwaysTrue()
                : pathSelectors.stream()
                .map(e -> Predicates.or(str, Predicates.containsPattern(e)))
                .reduce(Predicates::or)
                .orElse(Predicates.alwaysTrue());
        List<SecurityReference> referenceList = securityReferences.stream()
                .map(SecurityReferenceProperties::toSecurityReference)
                .collect(Collectors.toList());
        return new SecurityContext(referenceList, selectorPredicate, httpMethodPredicate);
    }
}
