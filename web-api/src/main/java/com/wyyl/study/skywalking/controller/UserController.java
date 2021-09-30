package com.wyyl.study.skywalking.controller;

import com.wyyl.study.skywalking.common.bean.UserDO;
import com.wyyl.study.skywalking.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aoe
 * @date 2021/9/19
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Reference(version = "1.0.0")
    private UserService userService;

    @GetMapping(value = "/{id}")
    public UserDO getUser(@PathVariable int id) {
        UserDO user = new UserDO();
        user.setId(id);
        String traceId = TraceContext.traceId();
        user.setName("traceId=" + traceId);
        log.info("traceId={}", traceId);
        return user;
    }

    @GetMapping(value = "/cache/{id}")
    public UserDO getUserByCache(@PathVariable int id) {
        return userService.getByCache(id);
    }

}
