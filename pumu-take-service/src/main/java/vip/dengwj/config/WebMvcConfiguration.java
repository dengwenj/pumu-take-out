package vip.dengwj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import vip.dengwj.interceptor.JwtTokenAdminInterceptor;
import vip.dengwj.json.JacksonObjectMapper;
import vip.dengwj.utils.HttpClientUtil;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册自定义拦截器
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        // addPathPatterns 需要拦截那些 excludePathPatterns 不需要拦截那些
        registry.addInterceptor(jwtTokenAdminInterceptor)
            // 拦截url匹配上这个的
            .addPathPatterns("/admin/**", "/user/**")
            // 这些 url 是不拦截的
            .excludePathPatterns("/user/user/login", "/user/shop/status", "/admin/login");
    }

    /**
     * 交给IOC容器管理， 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docketAdmin() {
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("朴睦外卖项目接口文档")
            .version("2.0")
            .description("朴睦外卖项目接口文档")
            .build();
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("管理端接口")
            .apiInfo(apiInfo)
            .select()
            // 指定生成接口需要扫描的包
            .apis(RequestHandlerSelectors.basePackage("vip.dengwj.controller.admin"))
            .paths(PathSelectors.any())
            .build();
    }

    @Bean
    public Docket docketUser() {
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("朴睦外卖项目接口文档")
            .version("2.0")
            .description("朴睦外卖项目接口文档")
            .build();
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("用户端接口")
            .apiInfo(apiInfo)
            .select()
            // 指定生成接口需要扫描的包
            .apis(RequestHandlerSelectors.basePackage("vip.dengwj.controller.user"))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * http client
     */
    @Bean
    public HttpClientUtil httpClientUtil() {
        return new HttpClientUtil();
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 扩展 mvc 框架的消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 需要为消息转换器设置一个对象转换器，对象转化器可以将 java 对象序列化为 json 数据
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将我们自己的转换器放入 spring mvc 框架的容器中
        converters.add(0, converter);
    }
}
