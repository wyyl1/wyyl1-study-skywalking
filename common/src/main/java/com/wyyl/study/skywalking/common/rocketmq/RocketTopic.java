package com.wyyl.study.skywalking.common.rocketmq;

import java.util.List;

/**
 * @author aoe
 * @date 2021/9/22
 */
public class RocketTopic {

    /**
     * 普通消息
     */
    public static final String TPC_NORMAL = "TPC_NORMAL";

    public static String getSubscribeTags(List<String> tags) {
        int size = tags.size();
        StringBuilder sbTags = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sbTags.append(tags.get(i));
            if (i != size - 1) {
                sbTags.append("||");
            }
        }
        return sbTags.toString();
    }
}
