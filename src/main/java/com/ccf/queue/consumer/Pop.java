package com.ccf.queue.consumer;

import com.ccf.queue.QueueService;
import com.ccf.util.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenchaofei on 2017/3/8.
 */
@Component
public class Pop {

    private static final Logger logger = LoggerFactory.getLogger(Pop.class);

    private static QueueService queue = null;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*4);
    static{
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                QueuePop.stop();
                threadPool.shutdown();
            }
        }));
    }

    public void init(){
        Thread current = Thread.currentThread();
        logger.info("queue pop init processors thread:{}--{}", current.getId(),current.getName());
        if(queue == null) {
            queue = SpringContext.getBean(QueueService.class);
        }
        new Thread(new QueuePop(),"queuePop").start();//由于是用redis做的队列，所以只要使用一个线程从队列里拿就ok
    }

    static class QueuePop implements Runnable{

        volatile static boolean stop=false;

        @Override
        public void run() {
            while(!stop){
                //不断循环从队列里取出订单id
                String orderId;
                try {
                    Thread current = Thread.currentThread();
                    orderId = queue.pop();
                    if(orderId!=null){
                        logger.info("pop orderId:{},thread:{}--{}",orderId,current.getId(),current.getName());
                        //将获取的订单编号交给订单统计任务处理线程处理
                        threadPool.submit(new Job(Integer.parseInt(orderId),new Date()));
                    } else {
                        System.out.print(".");
//                        logger.info("pop orderId is null,thread:{}--{}",current.getId(),current.getName());
                        //根据上线后的业务反馈来确定是否改成wait/notify策略来及时处理确认的订单
                        Thread.sleep(1000);
                    }
                } catch (Exception e1) {
                    logger.error("",e1);
                }
            }
        }

        public static void stop(){
            stop=true;
        }

    }

}
