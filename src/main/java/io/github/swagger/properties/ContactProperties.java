package io.github.swagger.properties;

import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.Contact;

/**
 * Swagger {@link Contact} properties converter
 *
 * @author Wilson
 * @date 2018/11/10
 */
@Setter
@ToString
public class ContactProperties {
    private String name = "";
    private String url = "";
    private String email = "";

    Contact toContact() {
        return new Contact(name, url, email);
    }

}
