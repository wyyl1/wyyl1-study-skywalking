package com.wyyl.study.skywalking.common.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author aoe
 * @date 2021/9/19
 */
@Data
public class UserDO implements Serializable {

    private Integer id;

    private String name;

    private Integer age;
}
