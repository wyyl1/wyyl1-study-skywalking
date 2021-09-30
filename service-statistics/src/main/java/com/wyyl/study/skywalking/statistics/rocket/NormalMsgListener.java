package com.wyyl.study.skywalking.statistics.rocket;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.wyyl.study.skywalking.common.rocketmq.RocketTag;
import com.wyyl.study.skywalking.common.rocketmq.msg.GetUserByCacheMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author aoe
 * @date 2021/9/22
 */
@Component
@Slf4j
public class NormalMsgListener implements MessageListener {

    @Override
    public Action consume(Message message, ConsumeContext context) {
        log.info("收到普通队列消息111 {} {}", message.getTopic(), message.getTag());
        try {
            switch (message.getTag()) {
                case RocketTag.GET_USER_BY_CACHE:
                    // 从缓存查询用户消息
                    GetUserByCacheMsg msg = JSON.parseObject(message.getBody(), GetUserByCacheMsg.class);
                    log.info("收到消息 body={}", JSON.toJSONString(msg));
                    break;
            }
        } catch (Exception e) {
            log.error("消息处理失败", e);
            return Action.ReconsumeLater;
        }
        return Action.CommitMessage;
    }

}
