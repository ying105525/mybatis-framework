package com.ying.mybatis.handler;

import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.MappedStatement;
import com.ying.mybatis.mapping.ParameterMapping;
import com.ying.mybatis.utils.SimpleTypeRegistry;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class DefaultParameterHandler implements ParameterHandler {

	private MappedStatement mappedStatement;

	private BoundSql boundSql;

	public DefaultParameterHandler(MappedStatement mappedStatement, BoundSql boundSql) {
		this.mappedStatement = mappedStatement;
		this.boundSql = boundSql;
	}

	@Override
	public void handleParameter(Statement statement, Object param) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) statement;
			// 从mappedStatement获取入参的类型
			Class<?> parameterTypeClass = mappedStatement.getParameterTypeClass();
			// 如果入参是8种基本类型和String类型
			if (SimpleTypeRegistry.isSimpleType(parameterTypeClass)) {
				preparedStatement.setObject(1, param);
			} else if (parameterTypeClass == Map.class) {
				// 如果入参是Map类型
				// ....
			} else {
				// 如果入参是POJO类型（比如User类型）
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
				for (int i = 0; i < parameterMappings.size(); i++) {
					ParameterMapping parameterMapping = parameterMappings.get(i);
					// 封装的#{}里面的属性名称
					String name = parameterMapping.getName();
					// 利用反射去入参对象根据属性名称获取指定的属性值
					Field field = parameterTypeClass.getDeclaredField(name);
					field.setAccessible(true);
					Object value = field.get(param);
					// TODO 可以使用ParameterMapping里面的type对Object类型的value进行类型处理
					// 设置statement占位符中的值
					preparedStatement.setObject(i + 1, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
