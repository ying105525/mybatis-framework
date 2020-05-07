package com.ying.mybatis.executor;

import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.mapping.MappedStatement;
import com.ying.mybatis.sqlsource.SqlSource;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class CachingExecutor implements Executor {

	// 一级缓存
	// TODO private Executor delegate = new SimpleExecutor();
	private Executor delegate;

	public CachingExecutor(Executor executor) {
		this.delegate = executor;
	}

	@Override
	public Object query(Configuration configuration, MappedStatement mappedStatement, Object param, BoundSql boundSql)
			throws Exception {
		// 如果没有二级缓存key：BoundSql+其他
		// 获取SqlSource
		SqlSource sqlSource = mappedStatement.getSqlSource();
		// 执行SqlSource去获取Sql信息
		boundSql = sqlSource.getBoundSql(param);
		// 委托一级缓存去处理
		return delegate.query(configuration, mappedStatement, param, boundSql);
	}

}
