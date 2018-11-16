package io.github.swagger.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * PathSelectorsProperties-Swagger页面路径选择器
 *
 * @author Wilson
 * @date 2018/11/13
 */
@Setter
@Getter
@ToString
public class PathSelectorsProperties {
    /**
     * 隐藏路径匹配,example:[/user,/test/user] 隐藏swagger页面上/user及/user/**、/test/user及/test/user/下的所有路径
     */
    private List<String> excludePatterns = new ArrayList<>();
    /**
     * 显示路径匹配,example:[/user,/test/user] 显示swagger页面上/user及/user/**、/test/user及/test/user/下的所有路径
     */
    private List<String> includePatterns = new ArrayList<>();

    public boolean isEmpty() {
        return excludePatterns.isEmpty() && includePatterns.isEmpty();
    }
}
