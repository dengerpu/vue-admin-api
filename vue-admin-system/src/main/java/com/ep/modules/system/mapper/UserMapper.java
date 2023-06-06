package com.ep.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ep.modules.system.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-10 22:24
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectUserById(@Param("id")Long id);
}
