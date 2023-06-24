package com.ep.modules.system.service;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/***
 * @author dep
 * @version 1.0
 * @date 2023-06-08 22:18
 */
public interface SQLService {

    /***
     * 查询数据
     * @param SQL
     * @return
     * @param <T>
     */
    <T> List<T> selectListBySQL(@NotNull String SQL);

    /**
     * 查询总数量
     * @param SQL
     * @return
     */
    Integer selectCountBySQL(@NotNull String SQL);
}
