package com.ying.mybatis.executor;

import com.ying.mybatis.handler.StatementHandler;
import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.mapping.MappedStatement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class SimpleExecutor extends BaseExecutor {

	@Override
	public Object queryFromDataBase(Configuration configuration, MappedStatement mappedStatement, Object param,
									BoundSql boundSql) {

		Connection connection = null;
		Statement statement = null;

		try {
			// 1、获取Connection
			connection = getConnection(configuration);

			// 2、获取可以JDBC执行的SQL语句
			if (mappedStatement == null) {
				return null;
			}

			// 获取JDBC可以直接执行的SQL语句
			// TODO String sql = boundSql.getSql();

			StatementHandler statementHandler = configuration.newStatementHandler(configuration, mappedStatement,
					boundSql);
			// 去创建Statement
			statement = statementHandler.prepare(connection);
			// 参数设置
			statementHandler.parameterize(statement, param);

			// 执行Statement
			return statementHandler.query(statement);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			/*
			 * if (rs != null) { try { rs.close(); } catch (SQLException e) {
			 * e.printStackTrace(); } } if (preparedStatement != null) { try {
			 * preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
			 * } if (connection != null) { try { connection.close(); } catch (SQLException
			 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }
			 */
		}
		return null;
	}

	private Connection getConnection(Configuration configuration) throws Exception {
		DataSource dataSource = configuration.getDataSource();
		Connection connection = dataSource.getConnection();
		return connection;
	}

}
