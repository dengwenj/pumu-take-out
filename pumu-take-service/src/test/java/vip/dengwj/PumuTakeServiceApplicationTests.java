package vip.dengwj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class PumuTakeServiceApplicationTests {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void testRedisTemplate() throws Exception {
        System.out.println(redisTemplate);
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        ListOperations<String, Object> stringObjectListOperations = redisTemplate.opsForList();
        SetOperations<String, Object> stringObjectSetOperations = redisTemplate.opsForSet();
        ZSetOperations<String, Object> stringObjectZSetOperations = redisTemplate.opsForZSet();
    }

    /**
     * spring 操作 redis 字符串
     */
    @Test
    public void testStringRedisTemplate() {
        // set get setex setnx
        // 设置
        redisTemplate.opsForValue().set("hello", "world");
        System.out.println(redisTemplate.opsForValue().get("hello"));

        // 3分钟后过期，删除
        redisTemplate.opsForValue().set("code", "123456", 3, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get("code"));

        // 没有该 key 才设置
        redisTemplate.opsForValue().setIfAbsent("pp", "皮皮");
        System.out.println(redisTemplate.opsForValue().get("pp"));
        redisTemplate.opsForValue().setIfAbsent("pp", "啤啤");
    }

    /**
     * spring 操作 redis hash
     */
    @Test
    public void testHash() {
        // hset hget hdel hkeys hvals
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        // 设置
        hashOperations.put("hash", "key1", "value1");
        hashOperations.put("hash", "key2", "value2");

        // 获取
        System.out.println(hashOperations.get("hash", "key1"));

        // 删除
        hashOperations.delete("hash", "key2");

        // 获取这个 key 的 hashKey
        Set<Object> student = hashOperations.keys("hash");
        System.out.println(student);

        // 获取这个 key 的 hashVal
        List<Object> student1 = hashOperations.values("hash");
        System.out.println(student1);
    }
}
