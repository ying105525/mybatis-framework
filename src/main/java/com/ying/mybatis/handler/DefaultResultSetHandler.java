package com.ying.mybatis.handler;

import com.ying.mybatis.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetHandler implements ResultSetHandler {

	private MappedStatement mappedStatement;

	public DefaultResultSetHandler(MappedStatement mappedStatement) {
		this.mappedStatement = mappedStatement;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> handleResultSet(ResultSet rs) {
		try {
			List<Object> results = new ArrayList<Object>();
			Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
			while (rs.next()) {
				// 遍历一次是一行，也对应一个对象，利用反射new一个对象
				Object result = resultTypeClass.newInstance();

				// 要获取每一列的值，然后封装到结果对象中对应的属性名称上
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					// 获取每一列的值
					Object value = rs.getObject(i + 1);

					// 列的名称
					String columnName = metaData.getColumnName(i + 1);
					// 列名和属性名称要严格一致
					Field field = resultTypeClass.getDeclaredField(columnName);
					field.setAccessible(true);
					// 给映射的对象赋值
					field.set(result, value);
				}
				results.add(result);
			}

			return (List<E>) results;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
