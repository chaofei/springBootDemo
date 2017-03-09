package com.ccf.boot;

import com.ccf.queue.consumer.Pop;
import com.ccf.util.SpringContext;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by chenchaofei on 2017/3/8.
 */
@Component
public class Listener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        Pop pop = SpringContext.getBean(Pop.class);
        pop.init();
    }

}
