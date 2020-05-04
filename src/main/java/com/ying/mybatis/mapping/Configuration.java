package com.ying.mybatis.mapping;

import com.ying.mybatis.executor.CachingExecutor;
import com.ying.mybatis.executor.Executor;
import com.ying.mybatis.executor.SimpleExecutor;
import com.ying.mybatis.handler.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应森亮
 * @date 2020/05/02
 * @desc 封装XML配置文件中的信息 比如:mybatis-config.xml和*mapper.xml中存储的数据
 */
public class Configuration {

    private boolean useCache = true;

    // 封装MappedStatement对象集合
    private Map<String, MappedStatement> mappedStatements = new HashMap<String, MappedStatement>();

    // 封装数据源对象
    private DataSource dataSource;

    public MappedStatement getMappedStatementById(String statementId) {
        return this.mappedStatements.get(statementId);
    }

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        this.mappedStatements.put(statementId, mappedStatement);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Executor newExecutor() {
        Executor executor = null;
        // 可以通过配置文件，去指定使用哪种Executor
        // 默认创建的是SimpleExecutor
        executor = new SimpleExecutor();
        // 默认优先使用二级缓存执行器CachingExecutor
        if (useCache) {
            executor = new CachingExecutor(executor);
        }

        return executor;
    }

    public StatementHandler newStatementHandler(Configuration configuration, MappedStatement mappedStatement,
                                                BoundSql boundSql) {
        StatementHandler statementHandler = null;
        switch (mappedStatement.getStatementType()) {
            case "prepared":
                statementHandler = new PreparedStatementHandler(configuration, mappedStatement, boundSql);
                break;
            case "simple":

                // statementHandler = new SimpleStatementHandler(mappedStatement, boundSql);
                break;

            default:
                break;
        }
        return statementHandler;
    }

    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, boundSql);
    }

    public ResultSetHandler newResultSetHandler(MappedStatement mappedStatement) {
        return new DefaultResultSetHandler(mappedStatement);
    }

}
