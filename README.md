# swagger2-spring-boot-starter
Swagger Spring Boot Starter.
Support jdk 1.8 or 1.8+,base on spring-boot 2.0+ and swagger2-2.9.2
## Installation
Add the following dependence into your project and make sure that your project is using spring-boot 2.0+ version.


    <dependency>
        <groupId>com.github.wilson.swagger</groupId>
        <artifactId>swagger2-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
  
## Configuration tree
In order to give users a clearer understanding of swagger's various levels of configuration, the framework is mainly configured according to the original swagger configuration structure.The structure tree is as follows(omit part)：
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
     - base-package(required,not configure will result NPE)
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
- swagger.docket.response-message-language:全局信息返回语言(仅配置了401,403,404,500,可通过接口@ApiResponse覆盖)，下图为cn信息
