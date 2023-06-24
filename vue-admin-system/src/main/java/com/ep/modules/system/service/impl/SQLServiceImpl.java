package com.ep.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.ep.modules.system.service.SQLService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***
 * @author dep
 * @version 1.0
 * @date 2023-06-08 22:20
 */
@Service
public class SQLServiceImpl implements SQLService {

    @Override
    public <T> List<T> selectListBySQL(String SQL) {
        List<Map<String, Object>> maps = SqlRunner.db().selectList(SQL);
        return (List<T>) maps;
    }

    @Override
    public Integer selectCountBySQL(String SQL) {
        return SqlRunner.db().selectCount(SQL);
    }
}
