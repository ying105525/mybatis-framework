package com.ying.mybatis.handler;

import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.mapping.MappedStatement;

import java.sql.*;
import java.util.List;

public class PreparedStatementHandler implements StatementHandler {

	private BoundSql boundSql;

	private ParameterHandler parameterHandler;
	private ResultSetHandler resultSetHandler;

	public PreparedStatementHandler(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql) {
		this.boundSql = boundSql;

		// 初始化一个参数处理器，专门处理预处理的参数
		parameterHandler = configuration.newParameterHandler(mappedStatement, boundSql);
		resultSetHandler = configuration.newResultSetHandler(mappedStatement);

	}

	@Override
	public Statement prepare(Connection connection) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(boundSql.getSql());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	public void parameterize(Statement statement, Object param) {
		parameterHandler.handleParameter(statement, param);
		// handleParameter(preparedStatement, mappedStatement, boundSql, param);
	}

	@Override
	public <E> List<E> query(Statement statement) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) statement;
			ResultSet rs = preparedStatement.executeQuery();

			// 6、处理结果集
			// handleResultSet(rs, mappedStatement)
			return resultSetHandler.handleResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
