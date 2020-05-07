package com.ying.mybatis.handler;

import java.sql.Statement;

public interface ParameterHandler {

	void handleParameter(Statement statement, Object param);
}
