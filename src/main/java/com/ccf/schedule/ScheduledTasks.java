package com.ccf.schedule;


import com.ccf.queue.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Created by chenchaofei on 2017/3/8.
 */
@Component
@EnableScheduling
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private QueueService queue;


    /**
     * 定时任务
     * 第一位，表示秒，取值0-59
     * 第二位，表示分，取值0-59
     * 第三位，表示小时，取值0-23
     * 第四位，日期天/日，取值1-31
     * 第五位，日期月份，取值1-12
     * 第六位，星期，取值1-7，星期一，星期二...，注：不是第1周，第二周的意思
        另外：1表示星期天，2表示星期一。
     * 第7位，年份，可以留空，取值1970-2099
     *
     * (*)星号：可以理解为每的意思，每秒，每分，每天，每月，每年...
     * (?)问号：问号只能出现在日期和星期这两个位置，表示这个位置的值不确定，每天3点执行，所以第六位星期的位置，我们是不需要关注的，就是不确定的值。同时：日期和星期是两个相互排斥的元素，通过问号来表明不指定值。比如，1月10日，比如是星期1，如果在星期的位置是另指定星期二，就前后冲突矛盾了。
     * (-)减号：表达一个范围，如在小时字段中使用“10-12”，则表示从10到12点，即10,11,12
     * (,)逗号：表达一个列表值，如在星期字段中使用“1,2,4”，则表示星期一，星期二，星期四
     * (/)斜杠：如：x/y，x是开始值，y是步长，比如在第一位（秒） 0/15就是，从0秒开始，每15秒，最后就是0，15，30，45，60
     */
//    @Scheduled(cron="0/10 * * * * ?")
    public void cronJob(){
        Thread current = Thread.currentThread();
        logger.info("定时任务{}:{},name:{},time:{}",1,current.getId(),current.getName(),new Date());
    }

    /**
     * 当任务执行完毕后多长时间再执行
     */
//    @Scheduled(fixedDelay=5*1000)
    public void fixedDelayJob(){
        Thread current = Thread.currentThread();
        logger.info("定时任务{}:{},name:{},time:{}",2,current.getId(),current.getName(),new Date());
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 每多长时间一次，不论任务执行花费了多少时间
     */
//    @Scheduled(fixedRate=2*1000)
    public void fixedRateJob(){
        Thread current = Thread.currentThread();
        logger.info("定时任务{}:threadid:{},name:{},time:{}",3,current.getId(),current.getName(),new Date());
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //    @Scheduled(cron="0/5 * * * * ?")
    public void pushQueue() {
        java.util.Random random=new java.util.Random();
        for(int i=0;i<10;i++) {
            Integer result = random.nextInt(10) + 1;// 返回[0,10)集合中的整数，注意不包括10
            String id = result.toString();
            queue.push(id);
        }
        Thread current = Thread.currentThread();
        logger.info("定时任务:{},threadid:{},name:{},time:{}","pushqueue",current.getId(),current.getName(),new Date());
    }
}
