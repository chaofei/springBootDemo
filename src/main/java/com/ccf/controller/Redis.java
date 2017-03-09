package com.ccf.controller;

import com.ccf.queue.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by chenchaofei on 2017/3/9.
 */

@RestController
@EnableAutoConfiguration
@RequestMapping("/redis")
public class Redis {

    @Autowired
    private QueueService queue;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/set")
    String set() {
        String dt = (new Date()).toString();
        stringRedisTemplate.opsForValue().set("aaa", dt);
        return dt;
    }

    @RequestMapping("/get")
    String get() {
        return stringRedisTemplate.opsForValue().get("aaa").toString();
    }

    @RequestMapping("/push/{val}")
    String push(@PathVariable String val) {
        queue.push(val);
        return val;
    }

    @RequestMapping("/pop")
    String pop() {
        return queue.pop();
    }
}
