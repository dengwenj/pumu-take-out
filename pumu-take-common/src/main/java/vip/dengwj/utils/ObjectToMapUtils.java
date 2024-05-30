package vip.dengwj.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2024/5/30 18:10
 * @author 朴睦
 * @description 对象转成 map
 */
public class ObjectToMapUtils {
    public static Map<String, Object> objectToMap(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 获取属性名
            String name = field.getName();
            // 通过属性名获取值
            Object val = field.get(object);
            map.put(name, val);
        }

        return map;
    }
}
