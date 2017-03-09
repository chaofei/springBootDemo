package com.ccf.queue.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by chenchaofei on 2017/3/8.
 */
public class Job implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Job.class);

    private int id;
    private Date dt;

    public Job(int id, Date dt) {
        this.id=id;
        this.dt=dt;
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        logger.info("job:{},threadid:{},name:{},dt:{}",id,current.getId(),current.getName(),dt);
    }
}
