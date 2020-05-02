package com.ying.mybatis.sqlsession;

/**
 * @author 应森亮
 * @date 2020/05/02
 * @desc
 */
//工厂方法模式
public interface SqlSessionFactory {

    SqlSession openSqlSession();
}
