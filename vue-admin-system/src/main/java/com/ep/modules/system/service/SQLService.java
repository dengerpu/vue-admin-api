package com.ep.modules.system.service;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/***
 * @author dep
 * @version 1.0
 * @date 2023-06-08 22:18
 */
public interface SQLService {

    <T> List<T> selectListBySQL(@NotNull String SQL);
}
