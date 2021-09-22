package com.wyyl.study.skywalking.common.rocketmq.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author aoe
 * @date 2021/9/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetUserByCacheMsg extends QueueMsg{

    private Integer userId;

    private String userName;
}
