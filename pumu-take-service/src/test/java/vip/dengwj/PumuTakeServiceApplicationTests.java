package vip.dengwj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
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

    /**
     * spring 操作 redis set 集合
     */
    @Test
    public void testSet() {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        // 向集合中添加数据
        setOperations.add("set1", "val1", "val2");
        setOperations.add("set2", "val2", "val3");

        // 返回集合中所有元素
        Set<Object> set1 = setOperations.members("set1");
        System.out.println(set1);

        // 返回集合成员数据
        Long len = setOperations.size("set1");
        System.out.println(len);

        // 返回给定集合的所有交集
        Set<Object> intersect = setOperations.intersect("set1", "set2");
        System.out.println(intersect);

        // 返回给定集合的所有并集
        Set<Object> union = setOperations.union("set1", "set2");
        System.out.println(union);

        // 删除集合中一个或多个成员
        setOperations.remove("set1", "val1");
    }

    /**
     * spring 操作 redis list 列表
     */
    @Test
    public void testList() {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();

        // 单个插入
        listOperations.leftPush("list1", "val0");
        // 批量插入
        listOperations.leftPushAll("list1", "val1", "val2", "val3");

        // 获取指定范围
        List<Object> range = listOperations.range("list1", 0, 2);
        System.out.println(range);

        // 移除并获取最后一个元素
        Object last = listOperations.rightPop("list1");
        System.out.println(last);

        // 获取长度
        Long len = listOperations.size("list1");
        System.out.println(len);
    }

    /**
     * spring 操作 redis 有序集合
     */
    @Test
    public void testZSet() {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        // 添加数据
        zSetOperations.add("zset2", "aa", 1.1);
        zSetOperations.add("zset2", "bb", 1.4);
        zSetOperations.add("zset2", "cc", 1.7);
        zSetOperations.add("zset2", "dd", 1.2);
        zSetOperations.add("zset2", "ee", 1.3);

        // 获取数据
        Set<Object> zset2 = zSetOperations.range("zset2", 0, -1);
        System.out.println(zset2);

        // 对指定成员的分数增量 1.9 + 1.4
        zSetOperations.incrementScore("zset2", "bb", 1.9);

        // 移除成员，一个或多个
        zSetOperations.remove("zset2", "dd", "ee");
    }

    /**
     * 通用命令操作
     */
    @Test
    public void testCommon() {
        // 返回所有 key
        Set<String> keys = redisTemplate.keys("*");
        System.out.println(keys);
        // 返回匹配到的 key
        Set<String> keys1 = redisTemplate.keys("s*");
        System.out.println(keys1);

        // 检查某个 key 是否存在
        Boolean zset2 = redisTemplate.hasKey("zset2");
        System.out.println(zset2);

        // 返回 key 所存储的值的类型
        DataType zset21 = redisTemplate.type("zset2");
        System.out.println(zset21);

        // 删除 key
        redisTemplate.delete("set2");
    }
}
