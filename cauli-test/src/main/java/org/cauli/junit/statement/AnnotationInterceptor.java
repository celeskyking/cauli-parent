package org.cauli.junit.statement;


import org.apache.commons.lang3.StringUtils;
import org.cauli.db.DBCore;
import org.cauli.db.DbManager;
import org.cauli.db.annotation.SQL;
import org.cauli.junit.anno.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class AnnotationInterceptor implements Interceptor{

    private Logger logger = LoggerFactory.getLogger(AnnotationInterceptor.class);


    @Override
    public void interceptorBefore(InterceptorStatement statement) {

    }

    @Override
    public void interceptorAfter(InterceptorStatement statement) {

    }

    @Override
    public void interceptorAfterForce(InterceptorStatement statement) {

    }

    @Override
    public void interceptorBeforeRetryTimeConfig(InterceptorStatement statement) {
        initRetryTimes(statement);
        execSQL(statement);
    }


    private void initRetryTimes(InterceptorStatement statement){
        if(statement.getTestMethod().getMethod().isAnnotationPresent(Retry.class)){
            statement.setRetryTimes(statement.getTestMethod().getMethod().getAnnotation(Retry.class).value());
            logger.info("["+statement.getTestMethod().getName()+"]>>>这个case执行失败的话会被重新执行"+statement.getRetryTimes()+"次");
            //System.out.println(this.testMethod.getMethod().getDeclaringClass().getName());
        }else if(statement.getTestMethod().getMethod().getDeclaringClass().isAnnotationPresent(Retry.class)){
            statement.setRetryTimes(statement.getTestMethod().getMethod().getDeclaringClass().getAnnotation(Retry.class).value());
            logger.info("["+statement.getTestMethod().getName()+"]>>>这个case执行失败的话会被重新执行"+statement.getRetryTimes()+"次");
        }else{
            statement.setRetryTimes(0);
        }
    }

    private void execSQL(InterceptorStatement statement){
        if(statement.getTestMethod().getMethod().isAnnotationPresent(SQL.class)){
            SQL sql = statement.getTestMethod().getAnnotation(SQL.class);
            String id = sql.id();
            String value = sql.value();
            if(!StringUtils.startsWith(value.toLowerCase(), "select")){
                if(DbManager.getDBCore(id)!=null){
                    DBCore core = DbManager.getDBCore(id);
                    core.update(value);
                }
            }
        }
    }
}
