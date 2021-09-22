package com.wyyl.study.skywalking.user.rocket;

import com.aliyun.openservices.ons.api.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author aoe
 * @date 2021/9/22
 */
@Component
@Slf4j
public class RocketProducer implements InitializingBean, DisposableBean {

    private Producer producer;

    @Value("${aliyun.rocket.namesrv}")
    private String rocketNamesrv;

    @Value("${aliyun.rocket.groupId}")
    private String rocketGroupId;

    @Value("${aliyun.rocket.accessKey}")
    private String accessKey;

    @Value("${aliyun.rocket.accessSecret}")
    private String accessSecret;

    @Override
    public void destroy() throws Exception {
        if (producer != null) {
            producer.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("RocketMQ 生产者初始化");
        Properties properties = new Properties();
        // 测试使用
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, accessSecret);
        properties.put(PropertyKeyConst.NAMESRV_ADDR, rocketNamesrv);
        properties.put(PropertyKeyConst.GROUP_ID, rocketGroupId);
        // 集群模式订阅，共同分担消息处理
//        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        producer = ONSFactory.createProducer(properties);

        log.info("RocketMQ 生产者启动");
        producer.start();
    }

    /**
     * 同步发送实体对象消息
     * 可靠同步发送：同步发送是指消息发送方发出数据后，会在收到接收方发回响应之后才发下一个数据包的通讯方式；
     * 特点：速度快；有结果反馈；数据可靠；
     * 应用场景：应用场景非常广泛，例如重要通知邮件、报名短信通知、营销短信系统等
     * @param topic
     * @param tag
     * @param msg
     * @return
     */
    public boolean sendMsg(String topic, String tag, byte[] msg) {
        return sendMsg(topic, tag, msg, 0L);
    }

    /**
     * 发送延时消息
     * @param topic
     * @param tag
     * @param msg
     * @param startDeliverTime
     * @return
     */
    public boolean sendMsg(String topic, String tag, byte[] msg, long startDeliverTime) {
        Message message = new Message(topic, tag, msg);
        if (startDeliverTime > 0) {
            message.setStartDeliverTime(startDeliverTime);
        }
        SendResult result = producer.send(message);
        if (result != null) {
            return true;
        }
        return false;
    }

    /**
     * 异步发送消息
     * 可靠异步发送：发送方发出数据后，不等接收方发回响应，接着发送下个数据包的通讯方式；
     * 特点：速度快；有结果反馈；数据可靠；
     * 应用场景：异步发送一般用于链路耗时较长,对 rt响应时间较为敏感的业务场景,例如用户视频上传后通知启动转码服务,转码完成后通知推送转码结果等；
     * @param topic
     * @param tag
     * @param msg
     */
    public void sendMsgAsync(String topic, String tag, byte[] msg, SendCallback callback) {
        sendMsgAsync(topic, tag, msg, callback, 0L);
    }

    public void sendMsgAsync(String topic, String tag, byte[] msg, SendCallback callback, long startDeliverTime) {
        Message message = new Message(topic, tag, msg);
        if (startDeliverTime > 0) {
            message.setStartDeliverTime(startDeliverTime);
        }
        producer.sendAsync(message, callback);
    }

    /**
     * 单向发送
     * 单向发送：只负责发送消息，不等待服务器回应且没有回调函数触发，即只发送请求不等待应答；此方式发送消息的过程耗时非常短，一般在微秒级别；
     * 特点：速度最快，耗时非常短，毫秒级别；无结果反馈；数据不可靠，可能会丢失；
     * 应用场景：适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集；
     * @param topic
     * @param tag
     * @param msg
     */
    public void sendMsgOneway(String topic, String tag, byte[] msg) {
        sendMsgOneway(topic, tag, msg, 0L);
    }

    public void sendMsgOneway(String topic, String tag, byte[] msg, long startDeliverTime) {
        Message message = new Message(topic, tag, msg);
        if (startDeliverTime > 0) {
            message.setStartDeliverTime(startDeliverTime);
        }
        producer.sendOneway(message);
    }
}
