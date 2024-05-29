## pumu-take-common
* 该子模块存放的是一些公共类，可以供其他模块使用

## pumu-take-pojo 
* 该子模块存放的是一些 entity、DTO、VO
* entity：实体，通常和数据库中的表对应
* DTO：数据传输对象，通常用于程序各层之间传递数据
* VO：视图对象，为前端展示数据提供的对象
* POJO：普通 java 对象，只有属性和对应的 getter 和 setter

## pumu-take-service
* 该子模块中存放的是配置文件、配置类、拦截器、controller、service、mapper、启动类等

## nginx 反向代理的好处
* 提高访问速度
* 进行负载均衡
* 保证后端服务安全

## 接口文档
* 使用 Swagger 只需要按照它的规范去定义接口及接口相关的信息，就可以做到生成接口文档，以及在线接口调式页面
* knife4j 是为 java mvc 框架集成 Swagger 生成 API 文档的增强解决方案
* 1、Yapi 是设计阶段使用的工具，管理和维护接口
* 2、Swagger 在开发阶段使用的框架，帮助后端开发人员做后端的接口测试