package io.github.swagger.properties;

import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.ApiInfo;

import java.util.Collections;

/**
 * Swagger {@link ApiInfo} properties converter
 *
 * @author Wilson
 */
@Setter
@ToString
public class ApiInfoProperties {

    private String version;
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private ContactProperties contact = new ContactProperties();

    ApiInfo toApiInfo() {
        return new ApiInfo(title, description, version, termsOfServiceUrl, contact.toContact(), license, licenseUrl, Collections.emptyList());
    }
}
