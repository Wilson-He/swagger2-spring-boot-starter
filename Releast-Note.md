- # 版本
  - 1.1.2
    - 将basePackage设为非必须，只需添加依赖直接启动Spring Boot项目即可生成相应的Api文档
  - 1.1.1
    - 添加Docker Bean是否已存在校验，避免`@RefreshScope`时再次生成bean产生错误
  - 1.1.0
    - 修复security-schemas​属性名字错误,更改为security-schemes
  - 1.0.6
    - 修复依赖冲突
    - 设置security-references.method-selectors默认值为[GET, POST, PUT, DELETE]
  - 1.0.5
    - 修复依赖冲突
  - 1.0.4
    - enabled不设置则默认初始化swagger docket
    - 添加swagger.profiles配置参数<br>
      swagger.profiles若不设置,则默认初始化swagger docket;若配置的swagger.profiles含spring.profiles.active,
      则初始化swagger docket,否则不生成文档