package com.ying.mybatis.handler;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {

	Statement prepare(Connection connection);

	void parameterize(Statement statement, Object param);

	<E> List<E> query(Statement statement);

}
