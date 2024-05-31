package vip.dengwj.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import vip.dengwj.annotation.AutoFill;
import vip.dengwj.constant.AutoFillConstant;
import vip.dengwj.context.BaseContext;
import vip.dengwj.enun.InsertOrUpdate;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @date 2024/5/31 22:13
 * @author 朴睦
 * @description 自定义切面类，实现公共字段自动填充，拦截操作
 */
@Slf4j
@Component
@Aspect
public class AutoFillAspect {
    // 切入点
    @Pointcut("execution(* vip.dengwj.mapper.*.*(..)) && @annotation(vip.dengwj.annotation.AutoFill)")
    public void pc() {}

    @Before("pc()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException {
        // 方法签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取到这个方法上的注解
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        InsertOrUpdate value = annotation.value();

        // 获取方法的属性
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0) {
            return;
        }

        Object entity = args[0];
        Class<?> aClass = entity.getClass();

        Method createTime = aClass.getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
        Method createUser = aClass.getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
        Method updateTime = aClass.getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
        Method updateUser = aClass.getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
        Long empId = BaseContext.get();
        LocalDateTime now = LocalDateTime.now();

        try {
            if (value == InsertOrUpdate.INSERT) {
                // 第一个参数是实例，第二个参数是传入的参数
                createTime.invoke(entity, now);
                createUser.invoke(entity, empId);
            }
            updateTime.invoke(entity, now);
            updateUser.invoke(entity, empId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
