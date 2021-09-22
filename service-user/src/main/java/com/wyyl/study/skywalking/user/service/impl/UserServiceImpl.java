package com.wyyl.study.skywalking.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.wyyl.study.skywalking.common.bean.UserDO;
import com.wyyl.study.skywalking.common.rocketmq.RocketTag;
import com.wyyl.study.skywalking.common.rocketmq.RocketTopic;
import com.wyyl.study.skywalking.common.rocketmq.msg.GetUserByCacheMsg;
import com.wyyl.study.skywalking.common.service.UserService;
import com.wyyl.study.skywalking.user.dao.UserDAO;
import com.wyyl.study.skywalking.user.rocket.RocketProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author aoe
 * @date 2021/9/21
 */
@Service(version = "1.0.0")
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserDAO userDAO;

    @Resource
    private RocketProducer rocketProducer;

    @Override
    public UserDO getByCache(int userId) {
//        String traceId = TraceContext.traceId();
//        ActiveSpan.info("see userId=" + userId + " traceId={}" + traceId);
        log.info("userId={}", userId);
        String userStr = "" + redisTemplate.opsForValue().get("" + userId);
        if (!"null".equals(userStr)) {
            return JSON.parseObject(userStr, UserDO.class);
        }

        UserDO user = userDAO.selectById(userId);

        GetUserByCacheMsg msg = new GetUserByCacheMsg();
        msg.setUserId(userId);
        if (user != null) {
            msg.setUserName(user.getName());
        }
        boolean sendMsg = rocketProducer.sendMsg(RocketTopic.TPC_NORMAL, RocketTag.GET_USER_BY_CACHE, msg.serialize());
        log.info("发送统计消息 result={} msg={}", sendMsg, JSON.toJSONString(msg));

        return user;
    }
}
