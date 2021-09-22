package com.wyyl.study.skywalking.common.service;

import com.wyyl.study.skywalking.common.bean.UserDO;

/**
 * @author aoe
 * @date 2021/9/21
 */
public interface UserService {

    /**
     * 从缓存获取用户信息
     * @param userId
     * @return
     */
    UserDO getByCache(int userId);
}
