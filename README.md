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
## Extra configuration introduction
Some configuration marked extra are personal development, not according to swagger original configuration.This introduction mainly explains the extra part, and other part you can configure according to the swagger original hierarchy.
-  swagger.print-init: If true,println each docket initialized information.Default false
![output initilization information](https://img-blog.csdnimg.cn/20181114201529513.png)

- swagger.enabled:Enable swagger automatic configuration.If false,none bean(such as docket,apiResourceController) about swagger will initialize.
- swagger.dockets:Used to configure multiple docket, and its properties is same as docket.If you use intellij idea, you can see the example by Ctrl+Q when your cursor focus on swagger.dockets. And the base-package is required too.Example:
   - swagger:<br>
&nbsp;&nbsp;&nbsp;&nbsp;dockets:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;docket-test: &nbsp;&nbsp;&nbsp;&nbsp;# register a docket naming docket-test into Spring container<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;base-package: org.noslim.controller.test<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;docket-order:&nbsp;&nbsp;# register a docket naming docket-order into Spring container<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;base-package: org.noslim.controller.order
- swagger.docket.path-selectors: Select which paths will be displayed on swagger-ui
	- include-patterns:which paths will be displayed on swagger-ui.Example:[/user/**,/common/**]
	- exclude-patterns:which paths will be hidden on swagger-ui
- swagger.docket.global-parameter:Configuring global parameters.If global-parameters is configured at the same time,global-parameter will be appended to global-parameters. 
- swagger.docket.response-message-language:Global response message language(such as en,cn).
