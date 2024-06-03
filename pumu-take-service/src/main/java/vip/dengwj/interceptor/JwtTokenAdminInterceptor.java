package vip.dengwj.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import vip.dengwj.constant.JWTConstant;
import vip.dengwj.context.BaseContext;
import vip.dengwj.properties.JWTProperties;
import vip.dengwj.utils.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTProperties jwtProperties;

    // 校验jwt
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        // /admin/shop/status
        String requestURI = request.getRequestURI();
        String[] split = requestURI.split("/");

        String headerToken = "";
        String secretKey = "";
        String idField = "";
        if (split[1].equals("user")) {
            headerToken = jwtProperties.getUserTokenName();
            secretKey = jwtProperties.getUserSecretKey();
            idField = JWTConstant.USER_ID;
        } else if (split[1].equals("admin")) {
            headerToken = jwtProperties.getAdminTokenName();
            secretKey = jwtProperties.getAdminSecretKey();
            idField = JWTConstant.EMP_ID;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(headerToken);

        //2、校验令牌
        try {
            Map<String, Object> map = JWTUtils.parseJWT(secretKey, token);
            Long id = Long.valueOf(map.get(idField).toString());
            // 放到当前线程的局部变量
            BaseContext.set(id);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }
}
