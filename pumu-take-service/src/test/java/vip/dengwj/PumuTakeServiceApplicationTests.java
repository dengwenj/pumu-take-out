package vip.dengwj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

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
}
