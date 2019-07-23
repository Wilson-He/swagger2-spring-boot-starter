package io.github.swagger.properties;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newTreeMap;
import static springfox.documentation.builders.BuilderDefaults.defaultIfAbsent;

/**
 * Swagger global return message model,{@link ResponseMessage} properties converter
 *
 * @author Wilson
 */
@ToString
@NoArgsConstructor
@Setter
public class ResponseMessageProperties {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * {@link springfox.documentation.schema.ModelRef} type
     */
    private String modelRef;
    private Map<String, Header> headers = newTreeMap();
    private List<VendorExtension> vendorExtensions = newArrayList();

    public final static List<ResponseMessage> DEFAULT_EN_MESSAGES = new ArrayList<>(8);
    public final static List<ResponseMessage> DEFAULT_CN_MESSAGES = new ArrayList<>(8);

    private ResponseMessageProperties(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = defaultIfAbsent(message, this.message);
    }

    static {
        DEFAULT_EN_MESSAGES.add(new ResponseMessageProperties(401, "Unauthorized").toResponseMessage(null));
        DEFAULT_EN_MESSAGES.add(new ResponseMessageProperties(403, "Forbidden").toResponseMessage(null));
        DEFAULT_EN_MESSAGES.add(new ResponseMessageProperties(404, "Page Not Found").toResponseMessage(null));
        DEFAULT_EN_MESSAGES.add(new ResponseMessageProperties(500, "Server Internal Error").toResponseMessage(null));
        DEFAULT_CN_MESSAGES.add(new ResponseMessageProperties(401, "请先登录").toResponseMessage(null));
        DEFAULT_CN_MESSAGES.add(new ResponseMessageProperties(403, "权限不足").toResponseMessage(null));
        DEFAULT_CN_MESSAGES.add(new ResponseMessageProperties(404, "页面不存在").toResponseMessage(null));
        DEFAULT_CN_MESSAGES.add(new ResponseMessageProperties(500, "系统内部错误").toResponseMessage(null));
    }

    ResponseMessage toResponseMessage(ModelRef modelRef) {
        return new ResponseMessage(this.code, this.message, modelRef, this.headers, this.vendorExtensions);
    }
}
