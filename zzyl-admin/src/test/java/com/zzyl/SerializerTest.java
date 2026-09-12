package com.zzyl;

import com.zzyl.nursing.domain.NursingProject;
import com.zzyl.nursing.vo.NursingProjectVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

@SpringBootTest
public class SerializerTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Test
    public void testObjectSerializer() {
        // 1. 准备一个对象
        NursingProject np = new NursingProject();
        np.setId(1L);
        np.setName("项目1");
        np.setOrderNo(1);
        np.setUnit("次");
        np.setPrice(new BigDecimal("10.00"));

        // 2. 将对象保存在redis
        redisTemplate.opsForValue().set("nursingProject", np);

        // 3. 从redis中获取对象
        NursingProject np1 = (NursingProject) redisTemplate.opsForValue().get("nursingProject");
        System.out.println(np1);


    }

    @Test
    public void testObjectSerializer2() {
        // 1. 准备一个对象
        NursingProjectVo npvo = new NursingProjectVo();
        npvo.setValue("1");
        npvo.setLabel("项目1");

        // 2. 将对象保存在redis
        redisTemplate.opsForValue().set("nursingProjectVo", npvo);

        // 3. 从redis中获取对象
        NursingProjectVo npvo1 = (NursingProjectVo) redisTemplate.opsForValue().get("nursingProjectVo");
        System.out.println(npvo1);


    }
}
