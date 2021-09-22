package com.wyyl.study.skywalking.user.dao;

import com.wyyl.study.skywalking.common.bean.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author aoe
 * @date 2021/9/22
 */
@Mapper
@Repository
public interface UserDAO {

    /**
     * 插入数据
     */
    int insert(UserDO po);

    /**
     * 获得用户信息
     * @param id
     * @return
     */
    UserDO selectById(int id);
}
