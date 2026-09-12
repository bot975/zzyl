package com.zzyl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedisTemplate() {
        System.out.println(redisTemplate);
    }

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("name", "张三");
        System.out.println(redisTemplate.opsForValue().get("name"));
        // 设置带有过期时间的key
        redisTemplate.opsForValue().set("token", "123abc", 20, TimeUnit.SECONDS);
        // setnx
        redisTemplate.opsForValue().setIfAbsent("lock", "123abc");
        redisTemplate.opsForValue().setIfAbsent("lock", "456def");

    }

    @Test
    public void testHash() {
        // hset 设置一个hash类型的数据
        redisTemplate.opsForHash().put("user", "name", "张三");
        redisTemplate.opsForHash().put("user", "age", "18");

        // hget 获取一个hash类型的数据
        System.out.println(redisTemplate.opsForHash().get("user", "name"));
        System.out.println(redisTemplate.opsForHash().get("user", "age"));

        // hkeys 获取hash类型中所有的小key
        System.out.println(redisTemplate.opsForHash().keys("user")); // [name, age]

        // hvals 获取hash类型中所有的值
        System.out.println(redisTemplate.opsForHash().values("user")); // [张三, 18]

        // hdel 删除hash类型中指定的小key
        redisTemplate.opsForHash().delete("user", "name");

    }

    @Test
    public void testList() {
        // lpush 添加一个list类型的数据
        redisTemplate.opsForList().leftPush("mylist", "a");
        // lpushall 添加多个list类型的数据
        redisTemplate.opsForList().leftPushAll("mylist", "b", "c", "d");
        // lrange 获取list中的所有数据
        System.out.println(redisTemplate.opsForList().range("mylist", 0, -1)); // [d, c, b, a]
        // lpop 删除并返回list中的第一个元素
        System.out.println(redisTemplate.opsForList().leftPop("mylist")); // d
        System.out.println(redisTemplate.opsForList().range("mylist", 0, -1)); // [c, b, a]
        // rpop 删除并返回list中的最后一个元素
        System.out.println(redisTemplate.opsForList().rightPop("mylist")); // a
        System.out.println(redisTemplate.opsForList().range("mylist", 0, -1)); // [c, b]
        // llen 获取list的长度
        System.out.println(redisTemplate.opsForList().size("mylist")); // 2

    }

    @Test
    public void testSet() {
        // sadd 添加一个set类型的数据
        redisTemplate.opsForSet().add("myset1", "a", "b", "c", "d");
        redisTemplate.opsForSet().add("myset2", "a", "b", "x", "y");
        // smembers 获取set中的所有数据
        System.out.println(redisTemplate.opsForSet().members("myset1")); // [a, b, c, d]
        // scard 获取set的长度
        System.out.println(redisTemplate.opsForSet().size("myset1"));
        // sinter 获取两个set的交集
        System.out.println(redisTemplate.opsForSet().intersect("myset1", "myset2")); // [a, b]
        // sunion 获取两个set的并集
        System.out.println(redisTemplate.opsForSet().union("myset1", "myset2")); // [a, b, c, d, x, y]
    }

    @Test
    public void testZSet() {
        // zadd 添加一个zset类型的数据
        redisTemplate.opsForZSet().add("myzset", "a", 1.0);
        redisTemplate.opsForZSet().add("myzset", "b", 10);
        redisTemplate.opsForZSet().add("myzset", "c", 100);

        // zrange 获取zset中的所有数据
        System.out.println(redisTemplate.opsForZSet().range("myzset", 0, -1)); // [a, b, c]

        // zincrby 增加zset中指定元素的分数
        redisTemplate.opsForZSet().incrementScore("myzset", "a", 10);

        System.out.println(redisTemplate.opsForZSet().range("myzset", 0, -1)); // [a, b, c]

        // zrem 删除zset中指定的元素
        redisTemplate.opsForZSet().remove("myzset", "b");
    }

    @Test
    public void testCommon(){
        // 获取所有key
        System.out.println(redisTemplate.keys("*"));
        // exists 判断key是否存在
        System.out.println(redisTemplate.hasKey("name"));
        System.out.println(redisTemplate.hasKey("names"));
        // type 获取key的类型
        System.out.println(redisTemplate.type("name")); // string
        System.out.println(redisTemplate.type("mylist")); // list

        // del 删除key
        redisTemplate.delete("name");
        redisTemplate.delete(List.of("mylist", "myzset"));
    }

}
