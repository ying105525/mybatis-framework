package com.ying.mybatis.handler;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetHandler {

	<E> List<E> handleResultSet(ResultSet rs);

}
