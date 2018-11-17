# swagger2-spring-boot-starter自动化配置框架

## 简介
该框架基于swagger2-2.9.2与SpringBoot-2.0.1版本进行搭建,兼容SpringBoot2.x以上版本,不兼容1.x版本
[详细博客文章](https://blog.csdn.net/z28126308/article/details/84187221)
## 配置结构
为了让使用者更清晰的了解swagger各层次配置，该框架主要根据原swagger配置结构进行属性分层配置，结构树如下(省略了部分)：
- swagger
   - print-init(extra)
   - enabled(extra)
   - security-configuration
     - properties(client-id,client-secret,scope-separator...)
   - dockets(extra)
     - docket-bean-A
       - docket.properties
     - docket-bean-B
       - docket.properties
     - ...
   - docket
     - base-package(required,null将导致NPE)
 	 - path-mapping
 	 - group-name
 	 - host
 	 - protocols
 	 - consumers
 	 - produces
 	 - direct-model-substitutes
 	 - api-info
     - contact
       - properties(name,email,url)
     - properties(version,title.description,license...)
     - security-contexts
       - path-selectors
       - method-selectors
       - security-references
         - reference
         - scopes
     - security-schemas
       - api-key-list
       - basic-auth-list
       - oauth-list
     - path-selectors
       - include-patterns(extra)
       - exclude-patterns(extra)
      - global-parameter(extra)
      - global-parameters
        - -&nbsp;global-parameter[a].properties
        - -&nbsp;global-parameter[b].properties
      - response-message-language(extra)
      - response-messages
## 配置简介
标注了extra的皆为个人开发配置，非根据swagger原有配置转换而来，该简介主要对extra部分进行讲解。
-  swagger.print-init:是否在控制台输出各docket初始化的配置信息
![输出初始化信息](https://img-blog.csdnimg.cn/20181114201529513.png)

- swagger.enabled:是否开启swagger自动化配置(为了保留之前的配置但不开启配置而添加了该配置项)
- swagger.dockets:用于配置多个docket，属性内容与docket相同,若该配置项存在将覆盖docket配置，base-package须填，否则将报NullPointException，example:
   - swagger:<br>
&nbsp;&nbsp;&nbsp;&nbsp;dockets:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;docket-test: &nbsp;&nbsp;&nbsp;&nbsp;#注册一个beanName为docket-test的Docket到Spring容器中<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;base-package: org.noslim.controller.test<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;docket-order:&nbsp;&nbsp;#注册一个beanName为docket-order的Docket到Spring容器中<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;base-package: org.noslim.controller.order
- swagger.docket.path-selectors:swagger-ui上的路径选择器
	- include-patterns:路径显示样式
	- exclude-patterns:路径隐藏样式
- swagger.docket.global-parameter:配置全局参数,若同时配置了global-parameters,global-parameters会将global-parameter也加到全局参数里
- swagger.docket.response-message-language:全局信息返回语言(en,cn)，下图为cn信息
