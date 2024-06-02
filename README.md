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

## Swagger 常用注解
* 通过注解可以控制生成的接口文档，使接口文档拥有更好的可读性，常用注解如下
* @Api：用在类上，例如 Controller，表示对类的说明
* @ApiModel：用在类上，例如 entity、DTO、VO
* @ApiModelProperty：用在属性上，描述属性信息
* @ApiOperation：用在方法上，例如 Controller 的方法，说明方法的用途、作用

## 员工管理
* 当前端提交的数据和实体类中对应的属性差别比较大时，建议使用 DTO 来封装数据

## ThreadLocal
* ThreadLocal 并不是一个 Thread，而是 Thread 的局部变量
* ThreadLocal 为每个线程提供单独一份存储空间，具有线程隔离的效果，只有在线程内才能获取到对应的值，线程外则不能访问
* ThreadLocal 常用方法：
* public void set(T value)：设置当前线程的线程局部变量的值
* public T get()：返回当前线程所对应的线程局部变量的值
* public void remove()：移除当前线程的线程局部变量

## 日期转换
* 方式一：在属性上加入注解，对日期进行格式化，@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
* 方式二：在 WebMvcConfiguration 中扩展 Spring MVC 的消息转换器，统一对日期类型进行格式处理
* 消息转换器的作用：统一对后端返回给前端的数据统一进行转换的处理（比如：日期类型的格式化）

## Redis
* Redis 是一个基于内存的 key-value 结构数据库
* 基于内存存储，读写性能高
* 适合存储热点数据（热点商品、资讯、新闻）

## Redis 数据类型
* Redis 存储的事 key-value 结构的数据，其中 key 是字符串类型，value 有5种常用的数据类型：
* 1、字符串 string：普通字符串，Redis 中最简单的数据类型
* 2、哈希 hash：也叫散列，类似 java 中的 HashMap 结构
* 3、列表 list：按照插入顺序排序，可以有重复元素，类似 java 中的 LinkedLink
* 4、集合 set：无序集合，没有重复元素，类似 java 中的 HashSet
* 5、有序集合 sorted set / zset：集合中每个元素关联一个分数(score)，根据分数升序排序，没有重复元素
