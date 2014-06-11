package org.cauli.junit;


import com.google.common.collect.Lists;
import org.cauli.db.DBCore;
import org.cauli.db.DbManager;
import org.cauli.db.annotation.DB;
import org.cauli.db.annotation.MySQL;
import org.cauli.instrument.ClassPool;
import org.cauli.junit.anno.CauliRule;
import org.cauli.junit.anno.Listener;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import java.util.List;
import java.util.Set;

/**
 * @author 王天庆
 * */
public class JUnitBaseRunner extends Feeder{

    public JUnitBaseRunner(final Class<?> klass)
            throws InitializationError {
        super(klass);
        setScheduler(new ExcuteScheduler(klass));
    }


    public List<TestRule> getRules()  {
        List<TestRule> testRules = Lists.newArrayList();
        Set<Class<?>> classes = ClassPool.getClassPool();
        for(Class<?> clazz :classes){
            if(clazz.isAnnotationPresent(CauliRule.class)||clazz.isAnnotationPresent(Listener.class)){
                try {
                    testRules.add((TestRule) clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return testRules;
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
    }

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> result = getTestClass().getAnnotatedMethodValues(target,
                Rule.class, TestRule.class);

        result.addAll(getTestClass().getAnnotatedFieldValues(target,
                Rule.class, TestRule.class));
        result.addAll(getRules());
        return result;
    }

    public void initDB(){
        Set<Class<?>> classes=ClassPool.getClassPool();
        for(Class<?> clazz:classes){
            if(clazz.isAnnotationPresent(DB.class)||clazz.isAnnotationPresent(MySQL.class)){
                if(clazz.isAnnotationPresent(DB.class)){
                    DB db = clazz.getAnnotation(DB.class);
                    String username = db.username();
                    String password = db.password();
                    String url = db.url();
                    String id = db.id();
                    String driver = db.driver();
                    DBCore dbCore = new DBCore(username,password,url,driver);
                    DbManager.register(id,dbCore);
                }else if(clazz.isAnnotationPresent(MySQL.class)){
                    MySQL db = clazz.getAnnotation(MySQL.class);
                    String username = db.username();
                    String password = db.password();
                    String url = db.url();
                    String alias = db.id();
                    DBCore core = DBCore.mysql(username,password,url);
                    DbManager.register(alias,core);
                }
            }
        }
    }



}
