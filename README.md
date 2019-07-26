# swagger2-spring-boot-starter([版本更新信息](https://github.com/Wilson-He/swagger2-spring-boot-starter/blob/master/%E7%89%88%E6%9C%AC%E6%9B%B4%E6%96%B0%E4%BF%A1%E6%81%AF.md))
[中文版文档](https://github.com/Wilson-He/swagger2-spring-boot-starter/blob/master/README_zh.md)<br>
Swagger Spring Boot Starter.
Support jdk 1.8,base on spring-boot 2.0+ and swagger2-2.9.2

## Installation
Add the following dependence into your project and make sure that your project is using spring-boot 2.0+ version.


    <dependency>
        <groupId>com.github.wilson.swagger</groupId>
        <artifactId>swagger2-spring-boot-starter</artifactId>
        <version>LATEST</version>
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
     - base-package
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
    - resources-provider(configure zuul route docs,need to make enabled 'true')
      - swagger-resources
        - name
        - url
        - swagger-version
      
## Extra configuration introduction
Some configuration marked extra are personal development, not according to swagger original configuration.This introduction mainly explains the extra part, and other part you can configure according to the swagger original hierarchy.
-  swagger.print-init: If true,println each docket initialized information.Default false
![output initilization information](https://img-blog.csdnimg.cn/20181114201529513.png)

- swagger.enabled:Enable swagger automatic configuration.If false,none bean(such as docket,apiResourceController) about swagger will initialize.
- swagger.dockets:Used to configure multiple docket, and its properties is same as docket.If you use intellij idea, you can see the example by Ctrl+Q when your cursor focus on swagger.dockets .Example:
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

## Tips
If you don't know how to configure a properties, you can focus cursor on the property and then Ctrl+Q in intellij idea, maybe it's helpful.<br>
![status code configure example](https://img-blog.csdnimg.cn/20181115112436449.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3oyODEyNjMwOA==,size_16,color_FFFFFF,t_70)
![response message configure example](https://img-blog.csdnimg.cn/20181115112541433.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3oyODEyNjMwOA==,size_16,color_FFFFFF,t_70)

If you use zuul to route swagger or want to export swagger to an html/pdf file, I advise don't configure group-name, it will result in an exception.

## Quick start
#### dependencies
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.1.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>io.github.wilson</groupId>
            <artifactId>swagger2-spring-boot-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>

#### Application.java

	package org.noslim.web;
	
	import io.swagger.annotations.Api;
	import io.swagger.annotations.ApiResponse;
	import io.swagger.annotations.ApiResponses;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RestController;
	
	@SpringBootApplication
	@RestController
	@Api
	@ApiResponses({@ApiResponse(code = 200, message = "success", response = ResponseEntity.class)})
	public class Application {
	    public static void main(String[] args) {
	        SpringApplication.run(Application.class);
	    }
	
	    @GetMapping("/index")
	    public String index() {
	        return "index";
	    }
	
	    @GetMapping("/home")
	    public String home() {
	        return "home";
	    }
	
	    @GetMapping("/home/test")
	    public String homeTest() {
	        return "test";
	    }
	
	    @GetMapping("/test")
	    public String test() {
	        return "test";
	    }
	
	    @GetMapping("/index/test")
	    public String indexTest() {
	        return "test";
	    }
	
	    @GetMapping("/index/test/a")
	    public String indexTestA() {
	        return "test";
	    }
	}
#### application.yml

	swagger:
	  print-init: true #not required
	  enabled: true #required
	  docket:
	    base-package: org.noslim.web   #required
	server:
	  port: 8888   #not required
	  servlet:
	    context-path: /test  #not required



## Complex application.yml demo:

	swagger:
	  print-init: true
	  enabled: true
	  security-configuration:
	      client-id: client-1
	      client-secret: secretA
	      scope-separator: \,
	      use-basic-authentication-with-access-code-grant: true
	  docket:
	    base-package: org.noslim.web
	    group-name: origin
		direct-model-substitutes: [java.sql.Timestamp,java.lang.Long]
	    path-selectors:
	      include-patterns: [/home/*,/**]
	      exclude-patterns: [/index/*]
	    api-info:
	      contact:
	        name: Wilson
	        email: 845023508@qq.com
	        url: http://blog.csdn.net/z28126308/
	    security-contexts:
	      path-selectors: [/**]
	      method-selectors: [get,post,put,delete]
	      security-references:
	        scopes:
	          scopeA: manage scope A
	          scopeB: manage scope B
	        reference: reference-1
	    security-schemas:
	      basic-auth-list: [basic-1,basic-2]
	      api-key-list:
	          - name: query
	            key-name: Authorization
	            pass-as: header
	#      oauth-list:
	#          - name: oa1
	#            grant-types: [admin]
	#            scopes:
	#              scopeA: manage scope A
	    response-message-language: cn
	    response-messages:
	      - code: 501
	        message: test info
	    global-parameters:
	      - name: sss
	        param-type: header
	        description: global header sss
	  dockets:
	    docket-test:
	      base-package: org.noslim.web.test
	      group-name: test-module
	      api-info:
	        contact:
	          name: Wilson
	          email: 845023508@qq.com
	          url: http://blog.csdn.net/z28126308/
	      response-messages:
	        - code: 501
	          message: test
	      global-parameters:
	        - name: token
	          description: page token
	          param-type: header
	        - name: userId
	          description: user identification
	          param-type: query
	    docket-order:
	      api-info:
	        contact:
	          name: Wilson
	          email: 845023508@qq.com
	          url: http://blog.csdn.net/z28126308/
	      base-package: org.noslim.controller
	      group-name: order-module
	      
## Zuul example:
#### application.yml
	server:
	  port: 8999
	spring:
	  application:
	    name: api-docs
	zuul:
	  routes:
	    user-provider:
	      path: /user-provider/**
	    user-consumer:
	      path: /user-consumer/**
	
	eureka:
	  client:
	    service-url:
	      defaultZone: http://eureka1:50001/eureka/,http://eureka2:50002/eureka/
	
	swagger:
	  resources-provider:
	    swagger-resources:
	      - name: 用户消费者模块
	        url: /user-consumer/v2/api-docs
	      - name: 用户提供者模块
	        url: /user-provider/v2/api-docs
	  enabled: true
#### zuul swagger-ui
![zuul swagger-ui](https://img-blog.csdnimg.cn/20181122105526130.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3oyODEyNjMwOA==,size_16,color_FFFFFF,t_70)
