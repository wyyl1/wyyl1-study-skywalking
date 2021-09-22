package com.wyyl.study.skywalking.controller;

import com.wyyl.study.skywalking.common.bean.UserDO;
import com.wyyl.study.skywalking.common.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
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
public class UserController {

    @Reference(version = "1.0.0")
    private UserService userService;

    @GetMapping(value = "/{id}")
    public UserDO getUser(@PathVariable int id) {
        UserDO user = new UserDO();
        user.setId(id);
        user.setName("名字");
        return user;
    }

    @GetMapping(value = "/cache/{id}")
    public UserDO getUserByCache(@PathVariable int id) {
        return userService.getByCache(id);
    }
}
