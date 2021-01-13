package com.imethan.blog.configuration;

import lombok.extern.log4j.Log4j2;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @Name GracefulShutdown
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-13 12:01
 */
@Log4j2
public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private ThreadPoolTaskExecutor blogTaskExecutor;
    private volatile Connector connector;

    GracefulShutdown(){}
    GracefulShutdown(ThreadPoolTaskExecutor blogTaskExecutor){
        this.blogTaskExecutor = blogTaskExecutor;
    }


    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        final int waitTime = 30;
        log.info("application is going to stop. try to stop tomcat gracefully");

        //关闭Connector连接器
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            threadPoolExecutor.shutdown();
            try {
                if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                    log.info("Tomcat did not terminate in the specified time.");
                    threadPoolExecutor.shutdownNow();
                }
            } catch (Exception ex) {
                log.error("awaitTermination failed.", ex);
                threadPoolExecutor.shutdownNow();
            }
        }
        try{
//            log.info("start to stop all task");
//            blogTaskExecutor.shutdown();
        }catch (Exception e){
            log.error("shutdown blogTaskExecutor failed.", e);
        }

    }
}
