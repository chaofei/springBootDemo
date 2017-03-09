package com.ccf.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by chenchaofei on 2017/3/8.
 */
@Component
public class QueueService {
    private static String key="job_id_queue";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void push(String val) {
        stringRedisTemplate.opsForList().rightPush(key, val);
    }

    public String pop() {
        return stringRedisTemplate.opsForList().leftPop(key);
    }
}
