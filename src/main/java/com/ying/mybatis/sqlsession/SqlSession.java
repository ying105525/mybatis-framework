package com.ying.mybatis.sqlsession;

import java.util.List;

/**
 * @author 应森亮
 * @date 2020/05/02
 * @desc SqlSession会话，提供CRUD功能
 */
public interface SqlSession {

    <T> T selectOne(String statementId, Object param);

    <T> List<T> selectList(String statementId, Object param);

    //其他需要的方法可自行在此后填充

}
