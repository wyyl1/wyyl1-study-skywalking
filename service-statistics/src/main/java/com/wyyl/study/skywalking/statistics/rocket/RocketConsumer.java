package com.wyyl.study.skywalking.statistics.rocket;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.wyyl.study.skywalking.common.rocketmq.RocketTag;
import com.wyyl.study.skywalking.common.rocketmq.RocketTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Properties;

import static com.wyyl.study.skywalking.common.rocketmq.RocketTopic.getSubscribeTags;

/**
 * @author aoe
 * @date 2021/9/22
 */
@Component
@Slf4j
public class RocketConsumer implements DisposableBean {

    private Consumer consumer;

    @Value("${aliyun.rocket.namesrv}")
    private String rocketNamesrv;

    @Value("${aliyun.rocket.groupId}")
    private String rocketGroupId;

    @Value("${aliyun.rocket.accessKey}")
    private String accessKey;

    @Value("${aliyun.rocket.accessSecret}")
    private String accessSecret;

    @Resource
    private NormalMsgListener normalMsgListener;

    public void start(){
        log.info("RocketMQ 消费者初始化");
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, accessSecret);
        properties.put(PropertyKeyConst.NAMESRV_ADDR, rocketNamesrv);
        properties.put(PropertyKeyConst.GROUP_ID, rocketGroupId);
        // 集群模式订阅，共同分担消息处理
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        consumer = ONSFactory.createConsumer(properties);

        log.info("RocketMQ 消费者启动");
        consumer.subscribe(RocketTopic.TPC_NORMAL, getSubscribeTags(Arrays.asList(
                RocketTag.GET_USER_BY_CACHE
        )), normalMsgListener);

        consumer.start();
    }


    @Override
    public void destroy() throws Exception {
        if (consumer != null) {
            consumer.shutdown();
        }
    }
}
