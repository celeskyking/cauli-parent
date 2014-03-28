package org.cauli.db;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 14-3-12
 */
public class DBCore implements JdbcOperations {

    private JdbcTemplate jdbcTemplate;

    public DBCore(){}

    public DBCore(String username,String password,String url,String driver){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setInitialSize(3);
        dataSource.setMaxIdle(3);
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    public static DBCore mysql(String username,String password,String url){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setInitialSize(3);
        dataSource.setMaxIdle(3);
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        DBCore dbCore =new DBCore();
        dbCore.jdbcTemplate=jdbcTemplate;
        return dbCore;
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T execute(ConnectionCallback<T> action) throws DataAccessException {
        return this.jdbcTemplate.execute(action);
    }

    @Override
    public <T> T execute(StatementCallback<T> action) throws DataAccessException {
        return this.jdbcTemplate.execute(action);
    }

    @Override
    public void execute(String sql) throws DataAccessException {
        this.jdbcTemplate.execute(sql);
    }

    @Override
    public <T> T query(String sql, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.jdbcTemplate.query(sql,rse);
    }

    @Override
    public void query(String sql, RowCallbackHandler rch) throws DataAccessException {
        this.jdbcTemplate.query(sql,rch);
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return this.jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return this.jdbcTemplate.queryForObject(sql,rowMapper);
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
        return this.jdbcTemplate.queryForObject(sql,requiredType);
    }

    @Override
    public Map<String, Object> queryForMap(String sql) throws DataAccessException {
        return this.jdbcTemplate.queryForMap(sql);
    }

    @Override
    public long queryForLong(String sql) throws DataAccessException {
        return this.jdbcTemplate.queryForLong(sql);
    }

    @Override
    public int queryForInt(String sql) throws DataAccessException {
        return this.jdbcTemplate.queryForInt(sql);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
        return this.queryForList(sql,elementType);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
        return this.jdbcTemplate.queryForList(sql);
    }

    @Override
    public SqlRowSet queryForRowSet(String sql) throws DataAccessException {
        return this.jdbcTemplate.queryForRowSet(sql);
    }

    @Override
    public int update(String sql) throws DataAccessException {
        return this.jdbcTemplate.update(sql);
    }

    @Override
    public int[] batchUpdate(String[] sql) throws DataAccessException {
        return this.jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action) throws DataAccessException {
        return this.jdbcTemplate.execute(psc,action);
    }

    @Override
    public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException {
        return this.execute(sql,action);
    }

    @Override
    public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.query(psc,rse);
    }

    @Override
    public <T> T query(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.query(sql,pss,rse);
    }

    @Override
    public <T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.query(sql,args,argTypes,rse);
    }

    @Override
    public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.jdbcTemplate.query(sql,args,rse);
    }

    @Override
    public <T> T query(String sql, ResultSetExtractor<T> rse, Object... args) throws DataAccessException {
        return this.jdbcTemplate.query(sql,rse,args);
    }

    @Override
    public void query(PreparedStatementCreator psc, RowCallbackHandler rch) throws DataAccessException {
        this.jdbcTemplate.query(psc,rch);
    }

    @Override
    public void query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException {
        this.query(sql,pss,rch);
    }

    @Override
    public void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException {
        this.query(sql,args,argTypes,rch);
    }

    @Override
    public void query(String sql, Object[] args, RowCallbackHandler rch) throws DataAccessException {
        this.query(sql,args,rch);
    }

    @Override
    public void query(String sql, RowCallbackHandler rch, Object... args) throws DataAccessException {
        this.jdbcTemplate.query(sql,rch,args);
    }

    @Override
    public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) throws DataAccessException {
        return this.jdbcTemplate.query(psc,rowMapper);
    }

    @Override
    public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException {
        return this.jdbcTemplate.query(sql,pss,rowMapper);
    }

    @Override
    public <T> List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
        return this.query(sql, args, argTypes, rowMapper);
    }

    @Override
    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        return this.query(sql, args, rowMapper);
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        return this.jdbcTemplate.query(sql, rowMapper, args);
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
        return this.queryForObject(sql, args, argTypes, rowMapper);
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        return this.queryForObject(sql,args,rowMapper);
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        return this.jdbcTemplate.queryForObject(sql,rowMapper,args);
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws DataAccessException {
        return this.jdbcTemplate.queryForObject(sql,args,argTypes,requiredType);
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
        return null;
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {
        return this.jdbcTemplate.queryForObject(sql,requiredType,args);
    }

    @Override
    public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.queryForMap(sql, args, argTypes);
    }

    @Override
    public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
        return this.jdbcTemplate.queryForMap(sql, args);
    }

    @Override
    public long queryForLong(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.queryForLong(sql, args, argTypes);
    }

    @Override
    public long queryForLong(String sql, Object... args) throws DataAccessException {
        return this.queryForLong(sql, args);
    }

    @Override
    public int queryForInt(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.jdbcTemplate.queryForInt(sql, args, argTypes);
    }

    @Override
    public int queryForInt(String sql, Object... args) throws DataAccessException {
        return this.jdbcTemplate.queryForInt(sql, args);
    }

    @Override
    public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType) throws DataAccessException {
        return this.jdbcTemplate.queryForList(sql, args, argTypes, elementType);
    }

    @Override
    public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {
        return this.jdbcTemplate.queryForList(sql,args,elementType);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) throws DataAccessException {
        return this.jdbcTemplate.queryForList(sql, elementType, args);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.jdbcTemplate.queryForList(sql, args, argTypes);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {
        return this.jdbcTemplate.queryForList(sql,args);
    }

    @Override
    public SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.jdbcTemplate.queryForRowSet(sql,args,argTypes);
    }

    @Override
    public SqlRowSet queryForRowSet(String sql, Object... args) throws DataAccessException {
        return this.jdbcTemplate.queryForRowSet(sql,args);
    }

    @Override
    public int update(PreparedStatementCreator psc) throws DataAccessException {
        return this.jdbcTemplate.update(psc);
    }

    @Override
    public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder) throws DataAccessException {
        return this.jdbcTemplate.update(psc, generatedKeyHolder);
    }

    @Override
    public int update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        return this.jdbcTemplate.update(sql, pss);
    }

    @Override
    public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.jdbcTemplate.update(sql, args, argTypes);
    }

    @Override
    public int update(String sql, Object... args) throws DataAccessException {
        return this.jdbcTemplate.update(sql, args);
    }

    @Override
    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) throws DataAccessException {
        return this.batchUpdate(sql, pss);
    }

    @Override
    public int[] batchUpdate(String sql, List<Object[]> batchArgs) throws DataAccessException {
        return this.jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public int[] batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes) throws DataAccessException {
        return this.batchUpdate(sql,batchArgs,argTypes);
    }

    @Override
    public <T> int[][] batchUpdate(String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss) throws DataAccessException {
        return this.jdbcTemplate.batchUpdate(sql, batchArgs, batchSize, pss);
    }

    @Override
    public <T> T execute(CallableStatementCreator csc, CallableStatementCallback<T> action) throws DataAccessException {
        return this.jdbcTemplate.execute(csc,action);
    }

    @Override
    public <T> T execute(String callString, CallableStatementCallback<T> action) throws DataAccessException {
        return this.jdbcTemplate.execute(callString,action);
    }

    @Override
    public Map<String, Object> call(CallableStatementCreator csc, List<SqlParameter> declaredParameters) throws DataAccessException {
        return this.jdbcTemplate.call(csc,declaredParameters);
    }

}
