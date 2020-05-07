package com.ying.mybatis.executor;

import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.mapping.MappedStatement;

//只处理一级缓存的逻辑
public abstract class BaseExecutor implements Executor {

	@Override
	public Object query(Configuration configuration, MappedStatement mappedStatement, Object param, BoundSql boundSql)
			throws Exception {
		// 如果一级缓存没有，则直接查询数据库

		// 查询数据库的方式有多种：批量查询、简单查询
		return queryFromDataBase(configuration, mappedStatement, param, boundSql);
	}

	public abstract Object queryFromDataBase(Configuration configuration, MappedStatement mappedStatement, Object param,
			BoundSql boundSql);

}
