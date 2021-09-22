package com.wyyl.study.skywalking.common.rocketmq.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

/**
 * @author aoe
 * @date 2021/9/22
 */
@Data
public abstract class QueueMsg {
    private long timestamp;

    public QueueMsg() {
        this.timestamp = System.currentTimeMillis();
    }

    public byte[] serialize() {
        return JSON.toJSONBytes(this, SerializerFeature.WriteNonStringKeyAsString);
    }
}
